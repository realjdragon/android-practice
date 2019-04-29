package com.jdragon.practice1.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jdragon.practice1.R;
import com.jdragon.library.base.adapter.EndlessScrollListener;
import com.jdragon.practice1.adapter.item.UglyAdapter;
import com.jdragon.practice1.api.GitHubApiClient;
import com.jdragon.practice1.entity.api.github.SearchUsersApiResponse;
import com.jdragon.library.base.entity.BaseModel;
import com.jdragon.practice1.entity.data.UglyResult;
import com.jdragon.library.base.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * ListView로 구현된 GitHub 회원 리스트
 */
public class GitHubUsersFragment extends BaseFragment {
    // 리스트뷰
    ListView listView;

    // 유저 리스트
    ArrayList<BaseModel> uglyUsers;

    UglyAdapter adapter;

    UglyResult lastResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_git_hub_users;
    }

    @Override
    public void initUI() {
        listView = rootView.findViewById(R.id.item_deal_list);
    }

    @Override
    public void initData() {
        uglyUsers = new ArrayList<>();

        adapter = new UglyAdapter(getContext());
        adapter.setDataList(uglyUsers);

        // 위에 만든 Adapter 객체를 ListView에 설정.
        listView.setAdapter(adapter);

        // API 호출해서 ListView Set
        setMobileHomeData(1);
    }

    @Override
    public void addListeners() {
        // 스와이프 새로고침
        setSwipeRefreshLayout(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                uglyUsers.clear();
                setMobileHomeData(1);
            }
        }, R.id.swipe_refresh_layout);

        // 무한 스크롤
        listView.setOnScrollListener(new EndlessScrollListener(2, 2) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                setMobileHomeData(page);

                return true;
            }
        });
    }

    private void setMobileHomeData(final int page) {
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

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
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
}
