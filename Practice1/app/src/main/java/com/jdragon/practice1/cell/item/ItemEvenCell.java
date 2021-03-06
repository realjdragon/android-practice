package com.jdragon.practice1.cell.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jdragon.practice1.R;
import com.jdragon.library.base.cell.BaseListCell;
import com.jdragon.practice1.entity.data.ItemInfo;

// 아이템 리스트뷰 짝수줄 cell
public class ItemEvenCell extends BaseListCell<ItemInfo> {
    View view;

    ImageView mImageView;

    TextView mTextView;

    public ItemEvenCell(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater) {
        view = inflater.inflate(R.layout.item_deal_list_row_even, this, false);

        // findViewById는 한 번만..
        mImageView = view.findViewById(R.id.item_image);
        mTextView = view.findViewById(R.id.item_name);

        return view;
    }

    @Override
    public void setData(ItemInfo data) {
        super.setData(data);

        Glide.with(view).load(data.getImgUrl()).into(mImageView);

        mTextView.setText(data.getName());
    }
}