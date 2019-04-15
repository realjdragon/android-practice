package com.cookandroid.practice1.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.item.ItemAdapter;
import com.cookandroid.practice1.api.HomeMainApiClient;
import com.cookandroid.practice1.entity.data.ItemInfo;
import com.cookandroid.practice1.entity.api.home.HomeMainApiResponse;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeMainFragment extends Fragment {
    // 그리드뷰
    GridView itemGridView;

    // 스와이프 레이아웃
    SwipeRefreshLayout mobileHomeSwipeRefreshLayout;

    // 상품 리스트
    ArrayList<ItemInfo> deals;

    ItemAdapter adapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_home_main, container, false);

        deals = new ArrayList<ItemInfo>();

        adapter = new ItemAdapter(getContext());
        adapter.setDataList(deals);

        // AdapterView는 ViewGroup에서 파생되는 클래스임.
        // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
        // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
        itemGridView = (GridView) rootView.findViewById(R.id.item_deal_grid);

        // 위에 만든 Adapter 객체를 ListView에 설정.
        itemGridView.setAdapter(adapter);

        // 스와이프 새로고침 설정
        setMobileHomeSwipeRefreshLayout();

        // API 호출해서 ListView Set
        setMobileHome();

        return rootView;
    }

    private void setMobileHome() {
        // 안드로이드는 안정성의 이유로 Main Thread (UI Thread)에서만 UI를 변경할 수 있도록 제한됨
        // 또, Main Thread에서 네트워크작업도 제한됨..5초 이상의 지연이 있을 경우 App 종료..
        // 따라서 별도의 Thread를 구성하고 네트워크 작업을 해야하는데 매번 이러긴 귀찮으니
        // Volley라는 패키지를 사용해서 손 쉽게 API를 호출하고 response 후에 UI 작업까지 할 수 있음.
        new HomeMainApiClient().getMobileHome(
                new Response.Listener<HomeMainApiResponse>() {
                    @Override
                    public void onResponse(HomeMainApiResponse response) {
                        try {
                            processMobileHomeResponse(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            if (getActivity() == null || getActivity().isFinishing() || !isAdded()) {
                                return;
                            }

                            mobileHomeSwipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(getActivity()
                                    , String.valueOf(deals.size()) + "개 상품 load"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mobileHomeSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void processMobileHomeResponse(HomeMainApiResponse response) {
        deals.clear();

        ArrayList<HomeMainApiResponse.HomeMainGroup> homeMainGroups
                = response.getData().getHomeMainGroupList();
        if (homeMainGroups != null && homeMainGroups.size() > 0) {
            for (HomeMainApiResponse.HomeMainGroup homeMainGroup : homeMainGroups) {
                if (homeMainGroup.getType() == 3) {
                    for (HomeMainApiResponse.Item item: homeMainGroup.getItemList()) {
                        deals.add(new ItemInfo(item.getImageUrl(), item.getItemTitle()));
                    }
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void setMobileHomeSwipeRefreshLayout() {
        mobileHomeSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.home_main_swipe_layout);
        mobileHomeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setMobileHome();
            }
        });
    }
}
