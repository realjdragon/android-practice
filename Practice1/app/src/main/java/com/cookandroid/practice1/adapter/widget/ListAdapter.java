package com.cookandroid.practice1.adapter.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.practice1.R;
import com.cookandroid.practice1.entity.data.DemoItem;
import com.cookandroid.practice1.entity.data.ItemInfo;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.List;

public class ListAdapter extends AsymmetricGridViewAdapter<ItemInfo> {
    ImageView mImageView;

    TextView mTextView;

    public ListAdapter(final Context context, final AsymmetricGridView listView, final List<ItemInfo> items) {
        super(context, listView, items);
    }

    @Override
    @SuppressWarnings("deprecation")
    public View getActualView(final int position, final View convertView, final ViewGroup parent) {
        ItemInfo item = getItem(position);

        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item_deal_list_row_odd, parent, false);
            v.setId(item.getPosition());

            // findViewById는 한 번만..
            mImageView = v.findViewById(R.id.item_image);
            mTextView = v.findViewById(R.id.item_name);
        } else {
            v = convertView;
        }

        Glide.with(v).load(item.getImgUrl()).into(mImageView);
        mTextView.setText(String.valueOf(item.getPosition()) + " " + String.valueOf(v.getId()));

        return v;
    }
}
