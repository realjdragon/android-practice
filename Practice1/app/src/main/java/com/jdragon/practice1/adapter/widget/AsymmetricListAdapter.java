package com.jdragon.practice1.adapter.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jdragon.practice1.R;
import com.jdragon.practice1.entity.data.ItemInfo;
import com.jdragon.library.asymmetricgridview.widget.AsymmetricGridView;
import com.jdragon.library.asymmetricgridview.widget.AsymmetricGridViewAdapter;

import java.util.List;

public class AsymmetricListAdapter extends AsymmetricGridViewAdapter<ItemInfo> {
    private class ViewHolder {
        ImageView mImageView;

        TextView mTextView;
    }

    public AsymmetricListAdapter(final Context context, final AsymmetricGridView listView, final List<ItemInfo> items) {
        super(context, listView, items);
    }

    @Override
    @SuppressWarnings("deprecation")
    public View getActualView(final int position, View convertView, final ViewGroup parent) {
        ItemInfo item = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_deal_list_row_odd, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mImageView = convertView.findViewById(R.id.item_image);
            viewHolder.mTextView = convertView.findViewById(R.id.item_name);

            convertView.setTag(R.layout.item_deal_list_row_odd, viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag(R.layout.item_deal_list_row_odd);
        }

        Glide.with(convertView).load(item.getImgUrl()).into(viewHolder.mImageView);
        viewHolder.mTextView.setText(item.getName());

        return convertView;
    }
}
