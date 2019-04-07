package com.cookandroid.practice1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.practice1.entity.ItemDeal;
import com.cookandroid.practice1.R;

import java.util.ArrayList;

// 딜카드 커스텀 어댑터
// BaseAdatper를 상속받음
// getCount, getItem, getItemId 이 세가지는 BaseAdatper에도 구현이 안 되어 있으니 직접 구현해야함.
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
        return deals.size() > 15 ? 15 : deals.size();
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

    // getCount()메소드의 리턴값만큼 getView를 목록에 나열함.
    // 이 리턴될 View의 모양을 하나의 레이아웃으로 만들어 놓음. layout -> item_deal_list_row
    // position : getItemId의 결과
    // convertview : ListView의 한 항목을 의미함. 아이템 한 개!
    // parent : 이 Adapter 객체가 설정된 AdapterView객체(여기서는 ListView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 크게 getView의 안에서는 2가지 작업이 이루어짐.
        // 1. ListView의 목록 하나의 모양을 담당하는 View 객체를 만들어 내는 'New View'
        // 2. 만들어진 View에 해당 Data를 연결하는 'Bind View'

        // 1.New View
        // convertView가 null이 아니라면 재활용할 수 있다고함.
        // 근데 그건 RecyclerView의 특징인줄 알았다만...
        if(convertView == null) {
            // null이라면 재활용할 View가 없으므로 새로운 객체 생성
            convertView = inflater.inflate(R.layout.item_deal_list_row, null);
        }

        // 2. Bind View
        // 재활용을 하든 새로 만들었든 이제 converView는 View객체 상태임.
        // 이 convertView로부터 데이터를 입력할 위젯들 참조하기.
        TextView itemNameView = (TextView)convertView.findViewById(R.id.item_name);
        ImageView itemImageView = (ImageView)convertView.findViewById(R.id.item_image);

        // position을 이용해 해당하는 item 찾기
        itemNameView.setText(deals.get(position).getName());

        // drawable 리소스에 있는 이미지 말고 url을 로드를 위해 glide 사용
        Glide.with(parent).load(deals.get(position).getImgUrl()).into(itemImageView);

        //설정이 끝난 convertView객체 리턴
        return convertView;
    }
}
