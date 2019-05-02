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
    protected int numColumns = DEFAULT_COLUMN_COUNT;
    protected final Rect padding;
    protected int defaultPadding;
    protected int requestedHorizontalSpacing;
    protected int requestedVerticalSpacing;
    protected int requestedColumnWidth;
    protected int requestedColumnCount;
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
        // 여기서 컬럼 수 결정
        determineColumns();
    }

    public int determineColumns() {
        int numColumns;
        final int availableSpace = getAvailableSpace();

        if (requestedColumnWidth > 0) {
            numColumns = (availableSpace + requestedHorizontalSpacing) /
                    (requestedColumnWidth + requestedHorizontalSpacing);
            // 대부분 컬럼 수를 입력해서 사용할 것
        } else if (requestedColumnCount > 0) {
            numColumns = requestedColumnCount;
        } else {
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

    /**
     * @return 한 컬럼의 가로 길이.
     */
    public int getColumnWidth() {
        return (getAvailableSpace() - ((numColumns - 1) * requestedHorizontalSpacing)) / numColumns;
    }

    /**
     * @return listview의 폭에서 양쪽 padding을 뺀 값. 컬럼과 divider가 들어갈 수 있는 공간
     */
    public int getAvailableSpace() {
        return getMeasuredWidth() - padding.left - padding.right;
    }
}
