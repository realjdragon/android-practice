package com.jdragon.library.asymmetricgridview.widget;

import com.jdragon.library.asymmetricgridview.model.AsymmetricItem;

import java.util.List;

class RowInfo<T extends AsymmetricItem> {

    private final List<T> items;
    private final int rowHeight;
    private final float spaceLeft;

    public RowInfo(final int rowHeight,
                   final List<T> items,
                   final float spaceLeft) {

        this.rowHeight = rowHeight;
        this.items = items;
        this.spaceLeft = spaceLeft;
    }

    public List<T> getItems() {
        return items;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public float getSpaceLeft() {
        return spaceLeft;
    }
}
