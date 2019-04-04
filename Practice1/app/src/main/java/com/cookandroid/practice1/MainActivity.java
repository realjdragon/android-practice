package com.cookandroid.practice1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // main activity를 View로 설정한다.
        setContentView(R.layout.activity_main);
        setTitle("홈메인");

        // 왜 final이어야 하는가?
        final ArrayList<ItemDeal> deals = new ArrayList<ItemDeal>();

        // TODO: GMApi 호출

        String imageUrl = "http://image.gmarket.co.kr/service_image/2019/04/03/20190403085832843577_0_0.jpg";

        deals.add(new ItemDeal(imageUrl, "상품1"));
        deals.add(new ItemDeal(imageUrl, "상품2"));
        deals.add(new ItemDeal(imageUrl, "상품3"));
        deals.add(new ItemDeal(imageUrl, "상품4"));
        deals.add(new ItemDeal(imageUrl, "상품5"));
        deals.add(new ItemDeal(imageUrl, "상품6"));
        deals.add(new ItemDeal(imageUrl, "상품7"));
        deals.add(new ItemDeal(imageUrl, "상품8"));
        deals.add(new ItemDeal(imageUrl, "상품9"));
        deals.add(new ItemDeal(imageUrl, "상품10"));
        deals.add(new ItemDeal(imageUrl, "상품11"));
        deals.add(new ItemDeal(imageUrl, "상품12"));
        deals.add(new ItemDeal(imageUrl, "상품13"));
        deals.add(new ItemDeal(imageUrl, "상품14"));
        deals.add(new ItemDeal(imageUrl, "상품15"));

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
}
