package com.jdragon.practice1.adapter.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jdragon.practice1.adapter.base.BaseListAdapter;
import com.jdragon.practice1.cell.item.ItemUglyDoubleCell;
import com.jdragon.practice1.cell.item.ItemUglyEvenCell;
import com.jdragon.practice1.cell.item.ItemUglyOddCell;
import com.jdragon.practice1.entity.base.BaseModel;
import com.jdragon.practice1.entity.data.UglyResult;

public class UglyAdapter extends BaseListAdapter<BaseModel> {
    public static final int UGLY_DOUBLE = 0;
    public static final int UGLY_ODD_CELL = 1;
    public static final int UGLY_EVEN_CELL = 2;


    public UglyAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initItemViewTypes() {
        addItemViewType(UGLY_DOUBLE, ItemUglyDoubleCell.class);
        addItemViewType(UGLY_ODD_CELL, ItemUglyOddCell.class);
        addItemViewType(UGLY_EVEN_CELL, ItemUglyEvenCell.class);
    }

    @Override
    public int getItemViewType(int position) {
        BaseModel item = getItem(position);
        if ( item instanceof UglyResult.DoubleResult) {
            return UGLY_DOUBLE;
        } else if ( item instanceof UglyResult.OddResult ) {
            return UGLY_ODD_CELL;
        } else if ( item instanceof UglyResult.EvenResult ) {
            return UGLY_EVEN_CELL;
        }

        return -1;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        View v = super.getView(position, view, group);

        return v;
    }
}