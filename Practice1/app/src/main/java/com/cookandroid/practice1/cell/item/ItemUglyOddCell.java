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

public class ItemUglyOddCell extends BaseListCell<UglyResult.OddResult> {
    View view;

    ImageView mImageView;

    TextView mTextView;

    public ItemUglyOddCell(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater) {
        view = inflater.inflate(R.layout.item_deal_list_row_odd, this, false);

        // findViewById는 한 번만..
        mImageView = view.findViewById(R.id.item_image);
        mTextView = view.findViewById(R.id.item_name);

        return view;
    }

    @Override
    public void setData(UglyResult.OddResult data) {
        super.setData(data);

        Glide.with(view).load(data.getImgUrl()).into(mImageView);
        mTextView.setText(data.getName());
    }
}