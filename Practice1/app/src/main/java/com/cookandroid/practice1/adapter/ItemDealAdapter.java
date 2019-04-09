package com.cookandroid.practice1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.entity.ItemDeal;

import java.util.ArrayList;

// 딜카드 커스텀 어댑터
// BaseAdatper를 상속받음
// getCount, getItem, getItemId 이 세가지는 BaseAdatper에도 구현이 안 되어 있으니 직접 구현해야함.
public class ItemDealAdapter extends BaseAdapter {
    int cntNew = 0;
    int cntOld = 0;

    private class ViewHolder {
        View mConvertView;

        ImageView mImageView;

        TextView mTextView;

        public ViewHolder(int type) {
            switch (type) {
                case 0:
                    mConvertView = inflater.inflate(R.layout.item_deal_list_row_odd, null);
                    break;
                case 1:
                    mConvertView = inflater.inflate(R.layout.item_deal_list_row_even, null);
                    break;
            }
        }
    }

    ViewHolder viewHolder;

    ArrayList<ItemDeal> deals;

    LayoutInflater inflater;

    public ItemDealAdapter(LayoutInflater inflater, ArrayList<ItemDeal> deals) {
        this.deals = deals;

        // item_deal_list.xml 파일을 View 객체로 생성(inflating) 해주는 역할의 객체
        // 이 ItemDealAdapter 클래스를 객체로 만들어내는 클래스에서 LayoutInflater 객체 전달해주야 함
        this.inflater = inflater;
    }

    // View 형태의 가짓수 (홀수, 짝수 두 가지)
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // 홀수, 짝수 구분
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    // ItemDealAdapter 객체가 만들어낼 View의 개수를 리턴하는 메소드
    @Override
    public int getCount() {
        return deals.size();
    }

    // ListView의 특정 위치(position)에 해당하는 Data를 리턴하는 메소드
    @Override
    public Object getItem(int position) {
        return deals.get(position);
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
        if(convertView == null) {
            // 처음에만 생성
            viewHolder = new ViewHolder(getItemViewType(position));

            convertView = viewHolder.mConvertView;

            viewHolder.mImageView = (ImageView)convertView.findViewById(R.id.item_image);
            viewHolder.mTextView = (TextView)convertView.findViewById(R.id.item_name);

            convertView.setTag(viewHolder);

            ++cntNew;
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

            ++cntOld;
        }

        viewHolder.mTextView.setText(String.valueOf(cntNew) + " "
                + String.valueOf(cntOld) + " "
                + deals.get(position).getName());

        Glide.with(parent).load(deals.get(position).getImgUrl()).into(viewHolder.mImageView);

        return convertView;
    }
}
