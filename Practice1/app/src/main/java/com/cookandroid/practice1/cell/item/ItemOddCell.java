package com.cookandroid.practice1.cell.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.cell.base.BaseListCell;
import com.cookandroid.practice1.entity.ItemDeal;

public class ItemOddCell extends BaseListCell<ItemDeal> {
    View view;

    ViewHolder viewHolder;

    private class ViewHolder {
        ImageView mImageView;

        TextView mTextView;
    }

    public ItemOddCell(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater) {
        view = inflater.inflate(R.layout.item_deal_list_row_odd, null);

        // findViewById는 한 번만..
        viewHolder = new ViewHolder();
        viewHolder.mImageView = (ImageView)view.findViewById(R.id.item_image);
        viewHolder.mTextView = (TextView)view.findViewById(R.id.item_name);

        return view;
    }

    @Override
    public void setData(ItemDeal data) {
        super.setData(data);

        Glide.with(view).load(data.getImgUrl()).into(viewHolder.mImageView);

        viewHolder.mTextView.setText(data.getName());
    }
}