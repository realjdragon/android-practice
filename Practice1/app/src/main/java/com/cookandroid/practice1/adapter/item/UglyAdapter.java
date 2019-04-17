package com.cookandroid.practice1.adapter.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.practice1.adapter.base.BaseListAdapter;
import com.cookandroid.practice1.cell.item.ItemEvenCell;
import com.cookandroid.practice1.cell.item.ItemOddCell;
import com.cookandroid.practice1.cell.item.ItemUglyDoubleCell;
import com.cookandroid.practice1.cell.item.ItemUglySingleCell;
import com.cookandroid.practice1.entity.base.BaseModel;
import com.cookandroid.practice1.entity.data.UglyResult;

public class UglyAdapter extends BaseListAdapter<BaseModel> {
    public static final int ITEM_DOUBLE = 0;
    public static final int ITEM_SINGLE = 1;

    public UglyAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initItemViewTypes() {
        addItemViewType(ITEM_DOUBLE, ItemUglyDoubleCell.class);
        addItemViewType(ITEM_SINGLE, ItemUglySingleCell.class);
    }

    @Override
    public int getItemViewType(int position) {
        BaseModel item = getItem(position);
        if ( item instanceof UglyResult.DoubleResult) {
            return ITEM_DOUBLE;
        } else if ( item instanceof UglyResult.SingleResult ) {
            return ITEM_SINGLE;
        }

        return -1;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        View v = super.getView(position, view, group);

        return v;
    }
}