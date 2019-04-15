package com.cookandroid.practice1.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.base.EndlessScrollListener;
import com.cookandroid.practice1.adapter.item.ItemListAdapter;
import com.cookandroid.practice1.api.GitHubApiClient;
import com.cookandroid.practice1.entity.data.ItemInfo;
import com.cookandroid.practice1.entity.api.github.SearchUsersApiResponse;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GitHubUsersFragment extends Fragment {
    // 리스트뷰
    ListView itemListView;

    // 스와이프 레이아웃
    SwipeRefreshLayout githubSwipeRefreshLayout;

    // 유저 리스트
    ArrayList<ItemInfo> users;

    ItemListAdapter adapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_git_hub_users, container, false);

        users = new ArrayList<ItemInfo>();

        adapter = new ItemListAdapter(getContext());
        adapter.setDataList(users);

        // AdapterView는 ViewGroup에서 파생되는 클래스임.
        // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
        // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
        itemListView = (ListView)rootView.findViewById(R.id.item_deal_list);

        // 무한 스크롤
        itemListView.setOnScrollListener(new EndlessScrollListener(10, 2) {
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

    private void setMobileHome(int page) {
        // 안드로이드는 안정성의 이유로 Main Thread (UI Thread)에서만 UI를 변경할 수 있도록 제한됨
        // 또, Main Thread에서 네트워크작업도 제한됨..5초 이상의 지연이 있을 경우 App 종료..
        // 따라서 별도의 Thread를 구성하고 네트워크 작업을 해야하는데 매번 이러긴 귀찮으니
        // Volley라는 패키지를 사용해서 손 쉽게 API를 호출하고 response 후에 UI 작업까지 할 수 있음.
        new GitHubApiClient().searchUsers(
                "to",
                page,
                new Response.Listener<SearchUsersApiResponse>() {
                    @Override
                    public void onResponse(SearchUsersApiResponse response) {
                        try {
                            processGetUsersResponse(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            if (getActivity() == null || getActivity().isFinishing() || !isAdded()) {
                                return;
                            }

                            githubSwipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(getActivity()
                                    , String.valueOf(users.size()) + "개 회원 load"
                                    , Toast.LENGTH_SHORT).show();
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

    private void processGetUsersResponse(SearchUsersApiResponse response) {
        ArrayList<SearchUsersApiResponse.User> users = response.getUsers();

        if (users != null && users.size() > 0) {
            for (SearchUsersApiResponse.User user : users) {
                this.users.add(new ItemInfo(user.getAvatarUrl(), user.getUrl()));
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void setUsersSwipeRefreshLayout() {
        githubSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.users_swipe_layout);
        githubSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                users.clear();
                setMobileHome(1);
            }
        });
    }
}
