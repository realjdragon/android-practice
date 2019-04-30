package com.jdragon.library.asymmetricgridview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jdragon.library.asymmetricgridview.AsymmetricGridViewAdapterContract;
import com.jdragon.library.asymmetricgridview.Utils;

public class AsymmetricGridView extends ListView {

    private static final int DEFAULT_COLUMN_COUNT = 2;
    private static final String TAG = "MultiColumnListView";
    protected int numColumns = DEFAULT_COLUMN_COUNT;
    protected final Rect padding;
    protected int defaultPadding;
    protected int requestedHorizontalSpacing;
    protected int requestedVerticalSpacing;
    protected int requestedColumnWidth;
    protected int requestedColumnCount;
    protected boolean allowReordering;
    protected boolean debugging = false;
    protected AsymmetricGridViewAdapterContract gridAdapter;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    public AsymmetricGridView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        defaultPadding = Utils.dpToPx(context, 5);
        requestedHorizontalSpacing = defaultPadding;
        requestedVerticalSpacing = defaultPadding;
        padding = new Rect(defaultPadding, defaultPadding, defaultPadding, defaultPadding);

        final ViewTreeObserver vto = getViewTreeObserver();
        if (vto != null)
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    getViewTreeObserver().removeOnPreDrawListener(this);
                    determineColumns();
                    if (gridAdapter != null)
                        gridAdapter.notifyDataSetChanged();
                    return false;
                }
            });
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    protected void fireOnItemClick(final int position, final View v) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(this, v, position, v.getId());
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    protected boolean fireOnItemLongClick(final int position, final View v) {
        return onItemLongClickListener != null && onItemLongClickListener.onItemLongClick(this, v, position, v.getId());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setAdapter(final ListAdapter adapter) {
        if (!(adapter instanceof AsymmetricGridViewAdapterContract))
            throw new UnsupportedOperationException("Adapter must implement AsymmetricGridViewAdapterContract");

        gridAdapter = (AsymmetricGridViewAdapterContract) adapter;
        super.setAdapter(adapter);
        gridAdapter.recalculateItemsPerRow();
    }

    public void setRequestedColumnWidth(final int width) {
        requestedColumnWidth = width;
    }

    public void setRequestedColumnCount(int requestedColumnCount) {
        this.requestedColumnCount = requestedColumnCount;
    }

    public int getRequestedHorizontalSpacing() {
        return requestedHorizontalSpacing;
    }

    public int getRequestedVerticalSpacing() {
        return requestedVerticalSpacing;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        determineColumns();
    }

    public int determineColumns() {
        int numColumns;
        final int availableSpace = getAvailableSpace();

        if (requestedColumnWidth > 0) {
            numColumns = (availableSpace + requestedHorizontalSpacing) /
                    (requestedColumnWidth + requestedHorizontalSpacing);
        } else if (requestedColumnCount > 0) {
            numColumns = requestedColumnCount;
        } else {
            // Default to 2 columns
            numColumns = DEFAULT_COLUMN_COUNT;
        }

        if (numColumns <= 0)
            numColumns = 1;

        this.numColumns = numColumns;

        return numColumns;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getColumnWidth() {
        return (getAvailableSpace() - ((numColumns - 1) * requestedHorizontalSpacing)) / numColumns;
    }

    public int getAvailableSpace() {
        return getMeasuredWidth() - padding.left - padding.right;
    }

    public boolean isAllowReordering() {
        return allowReordering;
    }

    public void setAllowReordering(final boolean allowReordering) {
        this.allowReordering = allowReordering;
        if (gridAdapter != null) {
            gridAdapter.recalculateItemsPerRow();
            gridAdapter.notifyDataSetChanged();
        }
    }
}
