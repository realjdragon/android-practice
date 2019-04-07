package com.cookandroid.practice1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.practice1.DownloadImageTask;
import com.cookandroid.practice1.entity.ItemDeal;
import com.cookandroid.practice1.R;

import java.util.ArrayList;

public class ItemDealAdapter extends BaseAdapter {
    ArrayList<ItemDeal> deals;

    LayoutInflater inflater;

    public ItemDealAdapter(LayoutInflater inflater, ArrayList<ItemDeal> deals) {
        this.deals = deals;

        // item_deal_list.xml 파일을 View 객체로 생성(inflating) 해주는 역할의 객체
        // 이 ItemDealAdapter 클래스를 객체로 만들어내는 클래스에서 LayoutInflater 객체 전달해주야 함
        this.inflater = inflater;
    }

    // ItemDealAdapter 객체가 만들어낼 View의 개수를 리턴하는 메소드
    @Override
    public int getCount() {
        return deals.size();
    }

    // ListView의 특정 위치(position)에 해당하는 Data를 리턴하는 메소드
    @Override
    public Object getItem(int position) {
        return deals.get(position);//deals 특정 인덱스 위치 객체 리턴.
    }

    // 특정 위치(position)의 data(ItemDeal)을 지칭하는 아이디를 리턴하는 메소드
    // 특별한 경우가 아니라면 보통은 data의 위치를 아이디로 사용
    @Override
    public long getItemId(int position) {
        return position;
    }

    // ListView에서 한 항목의 View 모양과 값을 설정하는 메소드
    // getCount()메소드의 리턴값만큼 getView를 요구하여  목록에 나열함.
    // 즉, 이 메소드의 리턴값인 View 가 ListView의 한 항목을 의미함.
    // 이 리턴될 View의 모양을 하나의 레이아웃으로 만들어 놓음.
    // 첫번째 파라미터 position : ListView에 놓여질 목록의 위치.
    // 두번째 파라미터 convertview : 리턴될 View로서 List의 한 함목의 View 객체(자세한 건 아래에 소개)
    // 세번째 파라미터 parent : 이 Adapter 객체가 설정된 AdapterView객체(이 예제에서는 ListView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 크게 getView의 안에서는 2가지 작업이 이루어 집니다.
        // 1. ListView의 목록 하나의 모양을 담당하는 View 객체를 만들어 내는 'New View'
        // 2. 만들어진 View에 해당 Data를 연결하는 'Bind View'

        // 1.New View
        // 지문의 한계상 자세히는 설명하기 어려우니 세부사항은 다른 포스트를 참고하시기 바랍니다.
        // 가장 먼저 new View를 하기 위해서 convertView 객체가 null인지 확인합니다.
        // null을 확인하는 이유는 자원 재활용때문인데..
        // 짧게 설명하자면.. ListView에서 보여줄 목록이 만약 100개라면...
        // 데이터의 양이 많아 분명 동시에 보여질 수 있는 목록의 개수를 정해져 있을 겁니다.
        // 그래서 이전 예제에서 보았듯이 ListView는 개수가 많으면 자동으로 Scroll되도록 되어 있지요.
        // 여기서 조금 생각해보시면 간단한데요.. 한번에 만약 5개 밖에 못보여진다면...
        // 굳이 나머지 95개의 View를 미리 만들 필요가 없겠죠.. 어차피 한번에 보여지는게 5~6개 수준이라면..
        // 그 정보의 View만 만들어서 안에 데이터만 바꾸면 메모리를 아낄 수 있다고 생각한 겁니다.
        // 여기 전달되어 체크되고 있는 converView 객체가 재활용할 View가 있으면 null이 아닌값을 가지고
        // 있다고 보시면 되고 converView가 null이면 새로 만들어야 한다고 보시면 됩니다.
        if(convertView == null) {
            // null이라면 재활용할 View가 없으므로 새로운 객체 생성
            convertView = inflater.inflate(R.layout.item_deal_list_row, null);
        }

        // 2. Bind View
        // 재활용을 하든 새로 만들었든 이제 converView는 View객체 상태임.
        // 이 convertView로부터 데이터를 입력할 위젯들 참조하기.
        // 상품이미지를 ImageView, 상품명을 표시하는 TextView

        TextView text_name= (TextView)convertView.findViewById(R.id.item_name);
        ImageView item_image= (ImageView)convertView.findViewById(R.id.item_image);

        // position을 이용해 해당하는 item 찾기
        text_name.setText(deals.get(position).getName());
        // 안드로이드는 왜 현재 실행중인 Thread에서 네트워크 작업을 할 수 없을까?
        new DownloadImageTask(item_image).execute(deals.get(position).getImgUrl());

        //설정이 끝난 convertView객체 리턴
        return convertView;
    }
}
