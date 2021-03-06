package com.jdragon.practice1.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jdragon.library.asymmetricgridview.widget.AsymmetricGridView;
import com.jdragon.library.base.adapter.EndlessScrollListener;
import com.jdragon.library.base.fragment.BaseFragment;
import com.jdragon.practice1.R;
import com.jdragon.practice1.adapter.item.AsymmetricListAdapter;
import com.jdragon.practice1.api.GitHubApiClient;
import com.jdragon.practice1.entity.api.github.SearchUsersApiResponse;
import com.jdragon.practice1.entity.data.ItemInfo;

import java.util.ArrayList;

/**
 * AsymmetricGridView로 구현된 GitHub 회원 리스트
 */
public class AsymmetricFragment extends BaseFragment {
    private AsymmetricGridView listView;

    private AsymmetricListAdapter adapter;

    private ArrayList<ItemInfo> lastResult;

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_asymmetric;
    }

    @Override
    public void initUI() {
        listView = findView(R.id.listView);
    }

    @Override
    public void initData() {
        adapter = new AsymmetricListAdapter(getActivity(), listView, new ArrayList<ItemInfo>());
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
                adapter.setItems(new ArrayList<ItemInfo>());
                setMobileHomeData(1);
            }
        }, R.id.swipe_refresh_layout);

        // 아이템 클릭
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

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
                            processGetUsersResponse(response);
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

    private void processGetUsersResponse(SearchUsersApiResponse response) {
        ArrayList<SearchUsersApiResponse.User> usersResponse = response.getUsers();

        if (usersResponse != null && usersResponse.size() > 0) {
            lastResult = new ArrayList<>();
            int colSpan;
            for (int i = 0; i < usersResponse.size(); i++) {
                colSpan = getColSpanByPosition(i);
                final ItemInfo item = new ItemInfo(usersResponse.get(i).getAvatarUrl(), usersResponse.get(i).getUrl(), colSpan);
                lastResult.add(item);
            }

            adapter.appendItems(lastResult);
        }
    }

    private int getColSpanByPosition(int position) {
        // 둘하나 둘하나
        return position % 3 == 2? 2 : 1;
    }
}
