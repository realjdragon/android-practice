package com.cookandroid.practice1.adapter.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.practice1.adapter.base.BaseListAdapter;
import com.cookandroid.practice1.cell.item.ItemEvenCell;
import com.cookandroid.practice1.cell.item.ItemOddCell;
import com.cookandroid.practice1.entity.data.ItemInfo;

public class ItemListAdapter extends BaseListAdapter<ItemInfo> {
    public static final int ITEM_ODD_ROW = 0;
    public static final int ITEM_EVEN_ROW = 1;

    private int oldPos;
    private boolean scrollDown;

    public ItemListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initItemViewTypes() {
        addItemViewType(ITEM_ODD_ROW, ItemOddCell.class);
        addItemViewType(ITEM_EVEN_ROW, ItemEvenCell.class);
    }

    @Override
    public int getItemViewType(int position) {
        // even, odd가 무한 반복되어야 하기 때문에..
        return position % 2;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        View v = super.getView(position, view, group);

        return v;
    }
}