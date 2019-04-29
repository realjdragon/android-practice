package com.jdragon.practice1.adapter.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jdragon.practice1.adapter.base.BaseListAdapter;
import com.jdragon.practice1.cell.item.ItemEvenCell;
import com.jdragon.practice1.cell.item.ItemOddCell;
import com.jdragon.practice1.entity.data.ItemInfo;

/**
 * 홀수, 짝수 cell 번갈아가며 다른 view 반환
 */
public class ItemAdapter extends BaseListAdapter<ItemInfo> {
    public static final int ITEM_ODD_ROW = 0;
    public static final int ITEM_EVEN_ROW = 1;

    public ItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initItemViewTypes() {
        addItemViewType(ITEM_ODD_ROW, ItemOddCell.class);
        addItemViewType(ITEM_EVEN_ROW, ItemEvenCell.class);
    }

    @Override
    public int getItemViewType(int position) {
        // 홀수, 짝수가 무한 반복되어야 하기 때문에..
        return position % 2;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        View v = super.getView(position, view, group);

        return v;
    }
}