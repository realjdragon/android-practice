package com.cookandroid.practice1.api;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.ItemDealAdapter;
import com.cookandroid.practice1.entity.ItemDeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeMainApiClient {
    public void setMainActivityList(final Activity activity)
    {
        final ArrayList<ItemDeal> deals = new ArrayList<ItemDeal>();

        String url = "";

        // Volley는 onResponse, onErrorResponse 리스너를 정의하는걸 강제하고 있음.
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                null,
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

                                    for (int i=0; i < itemList.length(); i++) {
                                        deals.add(new ItemDeal(itemList.getJSONObject(i).getString("ImageUrl")
                                                , itemList.getJSONObject(i).getString("ItemTitle")));
                                    }
                                }
                            }

                            // AdapterView는 ViewGroup에서 파생되는 클래스임.
                            // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
                            // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
                            ListView listView = (ListView)activity.findViewById(R.id.itemDealList);

                            // ItemDeal 객체의 정보들(이미지, 상품명)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
                            // 이 예제에서는 ItemDealAdapter.java 파일로 클래스를 설계하였음.
                            // getLayoutInflater : xml 레이아웃 파일을 객체로 만들어 줌
                            ItemDealAdapter adapter = new ItemDealAdapter(activity.getLayoutInflater(), deals);

                            // 위에 만든 Adapter 객체를 ListView에 설정.
                            listView.setAdapter(adapter);

                            // 아이템을 클릭했을 때 토스트
                            // 이런 식으로 한 개의 View 객체에 접근할 수 있으므로 사용자와 상호작용 가능
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(activity.getApplicationContext(), deals.get(i).getName(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "");
                return params;
            }
        };

        ApiClient.getInstance().addRequestQueue(request);
    }
}
