package com.cookandroid.practice1.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.ItemDealAdapter;
import com.cookandroid.practice1.api.MyApiClient;
import com.cookandroid.practice1.entity.ItemDeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

// Activity는 화면을 구성하는 가장 기본적인 컴포넌트
// 모든 Activity는 Activity 클래스를 상속받음
// AppCompatActivity는 안드로이드 하위 버전을 지원하는 액티비티
public class MainActivity extends AppCompatActivity {
    ListView itemListView;

    SwipeRefreshLayout mobileHomeSwipeRefreshLayout;

    ArrayList<ItemDeal> deals;

    // ItemDeal 객체의 정보들(이미지, 상품명)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
    // 이 예제에서는 ItemDealAdapter.java 파일로 클래스를 설계하였음.
    // getLayoutInflater : xml 레이아웃 파일을 객체로 만들어 줌
    ItemDealAdapter adapter;

    // Activity 클래스를 상속받아 액티비티 생명 주기에 따른 이벤트 리스너를 Override
    // 액티비티가 처음 만들어 졌을 때 호출됨
    // 화면에 보이는 뷰들의 일반적인 상태를 설정하는 부분
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Bundle은 상태를 보존하는데 사용한다고 한다.
        // 가령 앱 동작중에 카메라를 이용하고 돌아오는 경우
        // 카메라를 이용하는 사이에 앱이 죽어버리는 경우를 대비해 onSaveInstanceState(Bundle)를 작성
        super.onCreate(savedInstanceState);

        // main activity를 View로 설정한다.
        setContentView(R.layout.activity_main);

        // 제목 설정
        setTitle(R.string.main_title);

        deals = new ArrayList<ItemDeal>();

        adapter = new ItemDealAdapter(getLayoutInflater(), deals);

        // AdapterView는 ViewGroup에서 파생되는 클래스임.
        // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
        // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
        itemListView = (ListView)findViewById(R.id.item_deal_list);

        // 위에 만든 Adapter 객체를 ListView에 설정.
        itemListView.setAdapter(adapter);

        // 스와이프 새로고침 설정
        setMobileHomeSwipeRefreshLayout();

        // API 호출해서 ListView Set
        setMobileHome();

    }

    private void setMobileHome() {
        // 안드로이드는 안정성의 이유로 Main Thread (UI Thread)에서만 UI를 변경할 수 있도록 제한됨
        // 또, Main Thread에서 네트워크작업도 제한됨..5초 이상의 지연이 있을 경우 App 종료..
        // 따라서 별도의 Thread를 구성하고 네트워크 작업을 해야하는데 매번 이러긴 귀찮으니
        // Volley라는 패키지를 사용해서 손 쉽게 API를 호출하고 response 후에 UI 작업까지 할 수 있음.
        new MyApiClient().getMobileHome(
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("Data");
                            JSONArray homeMainGroupList = data.getJSONArray("HomeMainGroupList");
                            JSONArray itemList = new JSONArray();
                            for (int i=0; i < homeMainGroupList.length(); i++) {
                                if (homeMainGroupList.getJSONObject(i).getInt("Type") == 3){
                                    itemList = homeMainGroupList.getJSONObject(i).getJSONArray("ItemList");

                                    for (int j=0; j < itemList.length(); j++) {
                                        deals.add(new ItemDeal(itemList.getJSONObject(j).getString("ImageUrl")
                                                , itemList.getJSONObject(j).getString("ItemTitle")));
                                    }
                                }
                            }

                            adapter.notifyDataSetChanged();

                            mobileHomeSwipeRefreshLayout.setRefreshing(false);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", error.getMessage());
                    }
                }
        );
    }

    private void setMobileHomeSwipeRefreshLayout() {
        mobileHomeSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.home_main_swipe_layout);
        mobileHomeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setMobileHome();
            }
        });
    }
}
