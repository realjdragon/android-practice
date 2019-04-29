package com.cookandroid.practice1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.base.EndlessScrollListener;
import com.cookandroid.practice1.adapter.widget.ListAdapter;
import com.cookandroid.practice1.api.GitHubApiClient;
import com.cookandroid.practice1.entity.api.github.SearchUsersApiResponse;
import com.cookandroid.practice1.entity.data.ItemInfo;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AsymmetricFragment extends Fragment {
    View rootView;

    // 스와이프 레이아웃
    SwipeRefreshLayout githubSwipeRefreshLayout;

    private AsymmetricGridView listView;

    private ListAdapter adapter;

    private int currentOffset = 0;

    private ArrayList<ItemInfo> users;

    private ArrayList<ItemInfo> lastResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_asymmetric, container, false);

        listView = rootView.findViewById(R.id.listView);

        // 무한 스크롤
        listView.setOnScrollListener(new EndlessScrollListener(2, 2) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                setMobileHome(page);

                return true;
            }
        });

        users = new ArrayList<>();
        adapter = new ListAdapter(getActivity(), listView, users);

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
        ArrayList<SearchUsersApiResponse.User> usersResponse = response.getUsers();

        if (usersResponse != null && usersResponse.size() > 0) {
            lastResult = new ArrayList<>();
            for (int i = 0; i < usersResponse.size(); i++) {
                int colSpan = 1;
                // 둘하나 둘하나
                if (i % 3 == 2) {
                    colSpan = 2;
                }
                int rowSpan = colSpan;
                final ItemInfo item = new ItemInfo(usersResponse.get(i).getAvatarUrl(), usersResponse.get(i).getUrl(), colSpan, rowSpan, currentOffset + i);
                lastResult.add(item);
            }

            if (isFirst) {
                listView.setRequestedColumnCount(2);
                listView.setAdapter(adapter);
            }

            adapter.appendItems(lastResult);

        }
    }

    private void setUsersSwipeRefreshLayout() {
        githubSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        githubSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                users.clear();
                currentOffset = 0;
                setMobileHome(1);
            }
        });
    }
}
