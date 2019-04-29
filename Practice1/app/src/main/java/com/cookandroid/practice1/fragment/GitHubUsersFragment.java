package com.cookandroid.practice1.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.base.EndlessScrollListener;
import com.cookandroid.practice1.adapter.item.UglyAdapter;
import com.cookandroid.practice1.api.GitHubApiClient;
import com.cookandroid.practice1.entity.api.github.SearchUsersApiResponse;
import com.cookandroid.practice1.entity.base.BaseModel;
import com.cookandroid.practice1.entity.data.UglyResult;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GitHubUsersFragment extends Fragment {
    // 리스트뷰
    ListView itemListView;

    // 스와이프 레이아웃
    SwipeRefreshLayout githubSwipeRefreshLayout;

    // 유저 리스트
    ArrayList<BaseModel> uglyUsers;

    UglyAdapter adapter;

    View rootView;

    UglyResult lastResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_git_hub_users, container, false);

        uglyUsers = new ArrayList<>();

        adapter = new UglyAdapter(getContext());
        adapter.setDataList(uglyUsers);

        itemListView = rootView.findViewById(R.id.item_deal_list);

        // 무한 스크롤
        itemListView.setOnScrollListener(new EndlessScrollListener(2, 2) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                setMobileHome(page);

                return true;
            }
        });

        // 위에 만든 Adapter 객체를 ListView에 설정.
        itemListView.setAdapter(adapter);

        // 스와이프 새로고침 설정
        setUsersSwipeRefreshLayout();

        // API 호출해서 ListView Set
        setMobileHome(1);

        return rootView;
    }

    private void setMobileHome(final int page) {
        new GitHubApiClient().searchUsers(
                "to",
                page,
                new Response.Listener<SearchUsersApiResponse>() {
                    @Override
                    public void onResponse(SearchUsersApiResponse response) {
                        try {
                            processGetUsersResponse(response, page == 1);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            if (getActivity() == null || getActivity().isFinishing() || !isAdded()) {
                                return;
                            }

                            githubSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        githubSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void processGetUsersResponse(SearchUsersApiResponse response, boolean isFirst) {
        ArrayList<SearchUsersApiResponse.User> users = response.getUsers();

        if (users != null && users.size() > 0) {
            if (lastResult == null) {
                lastResult = new UglyResult();
            }

            lastResult.setUsers(users);

            uglyUsers.addAll(lastResult.makeUglyList(isFirst));
            adapter.notifyDataSetChanged();
        }
    }

    private void setUsersSwipeRefreshLayout() {
        githubSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        githubSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                uglyUsers.clear();
                setMobileHome(1);
            }
        });
    }
}
