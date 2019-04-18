package com.cookandroid.practice1.cell.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.cell.base.BaseListCell;
import com.cookandroid.practice1.entity.data.UglyResult;

public class ItemUglyDoubleCell extends BaseListCell<UglyResult.DoubleResult> {
    View view;

    ImageView mImageView1;

    TextView mTextView1;

    ImageView mImageView2;

    TextView mTextView2;

    public ItemUglyDoubleCell(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater) {
        view = inflater.inflate(R.layout.item_deal_ugly_row, this, false);

        // findViewById는 한 번만..
        mImageView1 = view.findViewById(R.id.item_image1);
        mTextView1 = view.findViewById(R.id.item_name1);
        mImageView2 = view.findViewById(R.id.item_image2);
        mTextView2 = view.findViewById(R.id.item_name2);

        return view;
    }

    @Override
    public void setData(UglyResult.DoubleResult data) {
        super.setData(data);

        Glide.with(view).load(data.getImgUrl1()).into(mImageView1);
        mTextView1.setText(data.getName1());
        Glide.with(view).load(data.getImgUrl2()).into(mImageView2);
        mTextView2.setText(data.getName2());
    }
}