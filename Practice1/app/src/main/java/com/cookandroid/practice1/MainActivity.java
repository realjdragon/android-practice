package com.cookandroid.practice1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // main activity를 View로 설정한다.
        setContentView(R.layout.activity_main);
        setTitle("홈메인");

        // 왜 final이어야 하는가?
        final ArrayList<ItemDeal> deals = new ArrayList<ItemDeal>();

        // API 호출
        RequestQueue rq = Volley.newRequestQueue(this);

        String apiUrl = "";

        final JSONArray itemList = new JSONArray();
        JsonObjectRequest jr = new JsonObjectRequest(
                apiUrl,
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
                                    break;
                                }
                            }

                            for (int i=0; i < itemList.length(); i++) {
                                if (i == 15){
                                    break;
                                }

                                deals.add(new ItemDeal(itemList.getJSONObject(i).getString("ImageUrl")
                                        , itemList.getJSONObject(i).getString("ItemTitle")));
                            }

                            ListView listView = (ListView) findViewById(R.id.listView1);

                            // AdapterView의 일종인 ListView에 적용할 Adapter 객체 생성
                            // MemberData 객체의 정보들(이름, 국적, 이미지)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
                            // 이 예제에서는 ItemDealAdapter.java 파일로 클래스를 설계하였음.
                            // 첫번재 파라미터로 xml 레이아웃 파일을 객체로 만들어 주는 LayoutInflater 객체 얻어와서 전달..
                            // inflater는 xml 문서를 가져와서 객체로 만들어주는 역할을 함.
                            // 두번째 파라미터는 우리가 나열한 Data 배열..
                            ItemDealAdapter adapter = new ItemDealAdapter(getLayoutInflater(), deals);

                            // 위에 만든 Adapter 객체를 AdapterView의 일종인 ListView에 설정.
                            listView.setAdapter(adapter);

                            // 아이템을 클릭했을 때 토스트
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(getApplicationContext(), deals.get(i).name,
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

        rq.add(jr);

    }
}
