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
import com.cookandroid.practice1.adapter.EndlessScrollListener;
import com.cookandroid.practice1.adapter.ItemDealAdapter;
import com.cookandroid.practice1.api.GitHubApiClient;
import com.cookandroid.practice1.entity.ItemDeal;
import com.cookandroid.practice1.entity.github.SearchUsersApiResponse;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class GitHubUsersFragment extends Fragment {
    // 리스트뷰
    ListView itemListView;

    // 스와이프 레이아웃
    SwipeRefreshLayout githubSwipeRefreshLayout;

    // 상품 리스트
    ArrayList<ItemDeal> deals;

    // ItemDeal 객체의 정보들(이미지, 상품명)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
    // 이 예제에서는 ItemDealAdapter.java 파일로 클래스를 설계하였음.
    // getLayoutInflater : xml 레이아웃 파일을 객체로 만들어 줌
    ItemDealAdapter adapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_git_hub_users, container, false);

        deals = new ArrayList<ItemDeal>();

        adapter = new ItemDealAdapter(inflater, deals);

        // AdapterView는 ViewGroup에서 파생되는 클래스임.
        // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
        // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
        itemListView = (ListView)rootView.findViewById(R.id.item_deal_list);

        // Attach the listener to the AdapterView onCreate
        itemListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                setMobileHome(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        // 위에 만든 Adapter 객체를 ListView에 설정.
        itemListView.setAdapter(adapter);

        // 스와이프 새로고침 설정
        setMobileHomeSwipeRefreshLayout();

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
                            processMobileHomeResponse(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            githubSwipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(getActivity()
                                    , String.valueOf(deals.size()) + "개 회원 load"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", error.getMessage());

                        githubSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void processMobileHomeResponse(SearchUsersApiResponse response) {
        ArrayList<SearchUsersApiResponse.User> users = response.getUsers();

        if (users != null && users.size() > 0) {
            for (SearchUsersApiResponse.User user : users) {
                deals.add(new ItemDeal(user.getAvatarUrl(), user.getUrl()));
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void setMobileHomeSwipeRefreshLayout() {
        githubSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.home_main_swipe_layout);
        githubSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setMobileHome(1);
            }
        });
    }
}
