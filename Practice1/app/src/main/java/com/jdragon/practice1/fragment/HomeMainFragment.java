package com.jdragon.practice1.fragment;


import android.widget.GridView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jdragon.library.base.fragment.BaseFragment;
import com.jdragon.practice1.R;
import com.jdragon.practice1.adapter.item.ItemAdapter;
import com.jdragon.practice1.api.HomeMainApiClient;
import com.jdragon.practice1.entity.api.home.HomeMainApiResponse;
import com.jdragon.practice1.entity.data.ItemInfo;

import java.util.ArrayList;

/**
 * GridView로 구현된 상품 리스트
 */
public class HomeMainFragment extends BaseFragment {
    // 상품 딜 그리드뷰
    GridView gridView;

    // 상품 목록
    ArrayList<ItemInfo> items;

    ItemAdapter adapter;

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_home_main;
    }

    @Override
    public void initUI() {
        gridView = findView(R.id.item_deal_grid);
    }

    @Override
    public void initData() {
        items = new ArrayList<>();

        adapter = new ItemAdapter(getContext());
        adapter.setDataList(items);

        gridView.setAdapter(adapter);

        // API 호출해서 ListView Set
        setMobileHomeData();
    }

    @Override
    public void addListeners() {
        setSwipeRefreshLayout(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setMobileHomeData();
            }
        }, R.id.swipe_refresh_layout);
    }

    private void setMobileHomeData() {
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
                            if (isFinishing()) {
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

    private void processMobileHomeResponse(HomeMainApiResponse response) {
        items.clear();

        ArrayList<HomeMainApiResponse.HomeMainGroup> homeMainGroups
                = response.getData().getHomeMainGroupList();
        if (homeMainGroups != null && homeMainGroups.size() > 0) {
            for (HomeMainApiResponse.HomeMainGroup homeMainGroup : homeMainGroups) {
                if (homeMainGroup.getType() == 3) {
                    for (HomeMainApiResponse.Item item: homeMainGroup.getItemList()) {
                        items.add(new ItemInfo(item.getImageUrl(), item.getItemTitle()));
                    }
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
