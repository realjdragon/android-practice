package com.cookandroid.practice1.activity;

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
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.ItemDealAdapter;
import com.cookandroid.practice1.entity.ItemDeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// AppCompatActivity는 안드로이드 하위 버전을 지원하는 액티비티
public class MainActivity extends AppCompatActivity {

    // Activity 클래스를 상속받아 액티비티 생명 주기에 따른 이벤트 리스터를 Override
    // 액티비티가 처음 만들어 졌을 때 호출됨
    // 화면에 보이는 뷰들의 일반적인 상태를 설정하는 부분
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // savedInstanceState가 뭘까?
        super.onCreate(savedInstanceState);
        // main activity를 View로 설정한다.
        setContentView(R.layout.activity_main);
        setTitle("홈메인");

        // API 호출해서 ListView Set
        getMobileHomeMain();
    }

    private void getMobileHomeMain() {
        final ArrayList<ItemDeal> deals = new ArrayList<ItemDeal>();

        // 안드로이드는 안정성의 이유로 Main Thread (UI Thread)에서만 UI를 변경할 수 있도록 제한됨
        // 또한 Main Thread에서 네트워크작업도 제한됨..5초 이상의 지연이 있을 경우 App 종료
        // 따라서 별도의 Thread를 구성하고 네트워크 작업을 해야하는데 매번 이러긴 귀찮으니
        // Volley라는 패키지를 사용해서 손 쉽게 API 호출을 할 수 있음.

        // RequestQueue를 생성하고 Queue에 Request 객체를 집어 넣어주면 알아서 쓰레드 핸들링을 해줌
        // 객체 하나만 생성하고 싱글톤으로 많이들 사용함
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String apiUrl = "";

        // Response 형식이 너무 복잡해서 이번에는 따로 entity를 만들지는 않았음..
        final JSONArray itemList = new JSONArray();

        // Volley는 onResponse, onErrorResponse 리스너를 정의하는걸 강제하고 있음.
        // 앱은 강제종료가 되는 상황이 최악이니 에러에 대한 핸들링을 강제하는 건 좋다고 생각함.
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
                                deals.add(new ItemDeal(itemList.getJSONObject(i).getString("ImageUrl")
                                        , itemList.getJSONObject(i).getString("ItemTitle")));
                            }

                            // AdapterView는 ViewGroup에서 파생되는 클래스임.
                            // 다수의 항목을 열거할 때 사용하는 뷰들을 총칭하여 AdapterView라고 함!
                            // AdapterView라고 부르는 이유는 UI에 표시할 항목을 adapter라는 객체에서 공급받기 때문
                            // 그러면 adapter라는 것도 만들어야겄네? 아래에서 만들어보자
                            ListView listView = (ListView)findViewById(R.id.listView1);

                            // ItemDeal 객체의 정보들(이미지, 상품명)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
                            // 이 예제에서는 ItemDealAdapter.java 파일로 클래스를 설계하였음.
                            // getLayoutInflater : xml 레이아웃 파일을 객체로 만들어 줌
                            ItemDealAdapter adapter = new ItemDealAdapter(getLayoutInflater(), deals);

                            // 위에 만든 Adapter 객체를 ListView에 설정.
                            listView.setAdapter(adapter);

                            // 아이템을 클릭했을 때 토스트
                            // 이런 식으로 한 개의 View 객체에 접근할 수 있으므로 사용자와 상호작용 가능
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(getApplicationContext(), deals.get(i).getName(),
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

        requestQueue.add(jr);
    }
}
