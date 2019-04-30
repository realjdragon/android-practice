package com.jdragon.library.asymmetricgridview.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.jdragon.library.R;
import com.jdragon.library.asymmetricgridview.AsymmetricGridViewAdapterContract;
import com.jdragon.library.asymmetricgridview.AsyncTaskCompat;
import com.jdragon.library.asymmetricgridview.Utils;
import com.jdragon.library.asymmetricgridview.model.AsymmetricItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AsymmetricGridViewAdapter<T extends AsymmetricItem>
        extends ArrayAdapter<T>
        implements View.OnClickListener,
        View.OnLongClickListener,
        AsymmetricGridViewAdapterContract {

    private static final String TAG = "AsymmetricGridViewAdapter";
    protected final AsymmetricGridView listView;
    protected final Context context;
    protected final List<T> items;

    private Map<Integer, RowInfo<T>> itemsPerRow = new HashMap<>();
    private final ViewPool<IcsLinearLayout> linearLayoutPool;
    private final ViewPool<View> viewPool = new ViewPool<>();
    private ProcessRowsTask asyncTask;

    public AsymmetricGridViewAdapter(final Context context,
                                     final AsymmetricGridView listView,
                                     final List<T> items) {

        super(context, 0, items);

        // 뭐니 이게
        this.linearLayoutPool = new ViewPool<>(new LinearLayoutPoolObjectFactory(context));
        // 이게 진짜 리스트
        this.items = items;
        this.context = context;
        // 아시메트릭 그리드뷰
        this.listView = listView;
    }

    // 상속받아서 이거만 구현해서 사용하나본데
    // 이 메서드가 호출될때는 이미 랜덤으로 그리드가 정해진 상태임...
    public abstract View getActualView(final int position, final View convertView, final ViewGroup parent);

    protected int getRowHeight(final AsymmetricItem item) {
        return getRowHeight(item.getColumnSpan());
    }

    protected int getRowHeight(int rowSpan) {
        final int rowHeight = listView.getColumnWidth() * rowSpan;
        // when the item spans multiple rows, we need to account for the vertical padding
        // and add that to the total final height
        return rowHeight + ((rowSpan - 1) * listView.getRequestedVerticalSpacing());
    }

    protected int getRowWidth(final AsymmetricItem item) {
        return getRowWidth(item.getColumnSpan());
    }

    protected int getRowWidth(int columnSpan) {
        final int rowWidth = listView.getColumnWidth() * columnSpan;
        // when the item spans multiple columns, we need to account for the horizontal padding
        // and add that to the total final width
        return Math.min(rowWidth + ((columnSpan - 1) * listView.getRequestedHorizontalSpacing()), Utils.getScreenWidth(getContext()));
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LinearLayout layout = findOrInitializeLayout(convertView);

        final RowInfo<T> rowInfo = itemsPerRow.get(position);
        final List<T> rowItems = new ArrayList<>();
        rowItems.addAll(rowInfo.getItems());

        // Index to control the current position
        // of the current column in this row
        int columnIndex = 0;

        // Index to control the current position
        // in the array of all the items available for this row
        int currentIndex = 0;

        int spaceLeftInColumn = rowInfo.getRowHeight();

        while (!rowItems.isEmpty() && columnIndex < listView.getNumColumns()) {
            final T currentItem = rowItems.get(currentIndex);

            if (spaceLeftInColumn == 0) {
                // No more space in this column. Move to next one
                columnIndex++;
                currentIndex = 0;
                spaceLeftInColumn = rowInfo.getRowHeight();
                continue;
            }

            // Is there enough space in this column to accommodate currentItem?
            if (spaceLeftInColumn >= currentItem.getColumnSpan()) {
                rowItems.remove(currentItem);

                int index = items.indexOf(currentItem);
                final LinearLayout childLayout = findOrInitializeChildLayout(layout, columnIndex);
                final View childConvertView = viewPool.get();
                final View v = getActualView(index, childConvertView, parent);
                v.setTag(currentItem);
                v.setOnClickListener(this);
                v.setOnLongClickListener(this);

                spaceLeftInColumn -= currentItem.getColumnSpan();
                currentIndex = 0;

                v.setLayoutParams(new LinearLayout.LayoutParams(getRowWidth(currentItem),
                        getRowHeight(currentItem)));

                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                v.setLayoutParams(layoutParams);

                childLayout.addView(v);
            } else if (currentIndex < rowItems.size() - 1) {
                // Try again with next item
                currentIndex++;
            } else {
                break;
            }
        }

        return layout;
    }

    @SuppressWarnings("MagicConstant")
    private IcsLinearLayout findOrInitializeLayout(final View convertView) {
        IcsLinearLayout layout;

        if (convertView == null || !(convertView instanceof IcsLinearLayout)) {
            layout = new IcsLinearLayout(context, null);
            if (listView.isDebugging())
                layout.setBackgroundColor(Color.parseColor("#83F27B"));

            layout.setShowDividers(IcsLinearLayout.SHOW_DIVIDER_MIDDLE);
            layout.setDividerDrawable(context.getResources().getDrawable(R.drawable.item_divider_horizontal));

            layout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT));
        } else
            layout = (IcsLinearLayout) convertView;

        // Clear all layout children before starting
        for (int j = 0; j < layout.getChildCount(); j++) {
            IcsLinearLayout tempChild = (IcsLinearLayout) layout.getChildAt(j);
            linearLayoutPool.put(tempChild);
            for (int k = 0; k < tempChild.getChildCount(); k++)
                viewPool.put(tempChild.getChildAt(k));
            tempChild.removeAllViews();
        }
        layout.removeAllViews();

        return layout;
    }

    @SuppressWarnings("MagicConstant")
    private IcsLinearLayout findOrInitializeChildLayout(final LinearLayout parentLayout, final int childIndex) {
        IcsLinearLayout childLayout = (IcsLinearLayout) parentLayout.getChildAt(childIndex);

        if (childLayout == null) {
            childLayout = linearLayoutPool.get();
            childLayout.setOrientation(LinearLayout.VERTICAL);

            if (listView.isDebugging())
                childLayout.setBackgroundColor(Color.parseColor("#837BF2"));

            childLayout.setShowDividers(IcsLinearLayout.SHOW_DIVIDER_MIDDLE);
            childLayout.setDividerDrawable(context.getResources().getDrawable(R.drawable.item_divider_vertical));

            childLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                    AbsListView.LayoutParams.MATCH_PARENT));
            parentLayout.addView(childLayout);
        }

        return childLayout;
    }

    public void setItems(List<T> newItems) {
        linearLayoutPool.clear();
        viewPool.clear();
        items.clear();
        items.addAll(newItems);
        recalculateItemsPerRow();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public void appendItems(List<T> newItems) {
        items.addAll(newItems);

        RowInfo<T> rowInfo = null;
        final int lastRow = getRowCount() - 1;
        if (lastRow >= 0)
            rowInfo = itemsPerRow.get(lastRow);

        // 추가로 더 붙일 때 마지막 row를 구한 다음 남은 공간이 있는지 본다..
        if (rowInfo != null) {
            final float spaceLeftInLastRow = rowInfo.getSpaceLeft();

            // Try to add new items into the last row, if there is any space left
            if (spaceLeftInLastRow > 0) {

                for (final T i : rowInfo.getItems())
                    newItems.add(0, i);

                final RowInfo<T> stuffThatFit = calculateItemsForRow(newItems);
                final List<T> itemsThatFit = stuffThatFit.getItems();

                if (!itemsThatFit.isEmpty()) {
                    for (T anItemsThatFit : itemsThatFit) newItems.remove(anItemsThatFit);

                    itemsPerRow.put(lastRow, stuffThatFit);
                    notifyDataSetChanged();
                }
            }
        }

        asyncTask = new ProcessRowsTask();
        asyncTask.executeSerially(newItems);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onClick(final View v) {
        final T item = (T) v.getTag();
        listView.fireOnItemClick(items.indexOf(item), v);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onLongClick(View v) {
        final T item = (T) v.getTag();
        return listView.fireOnItemLongClick(items.indexOf(item), v);
    }

    @Override
    public int getCount() {
        // Returns the row count for ListView display purposes
        return getRowCount();
    }

    public int getRowCount() {
        return itemsPerRow.size();
    }

    @SuppressWarnings("unchecked")
    public void recalculateItemsPerRow() {
        if (asyncTask != null)
            asyncTask.cancel(true);

        linearLayoutPool.clear();
        viewPool.clear();
        itemsPerRow.clear();

        final List<T> itemsToAdd = new ArrayList<>();
        itemsToAdd.addAll(items);

        asyncTask = new ProcessRowsTask();
        asyncTask.executeSerially(itemsToAdd);
    }

    private RowInfo<T> calculateItemsForRow(final List<T> items) {
        return calculateItemsForRow(items, listView.getNumColumns());
    }

    private RowInfo<T> calculateItemsForRow(final List<T> items, final float initialSpaceLeft) {
        final List<T> itemsThatFit = new ArrayList<>();
        int currentItem = 0;
        int rowHeight = 1;
        float areaLeft = initialSpaceLeft;

        // 돌면서 공간을 찾아 한 줄을 완성한다.
        while (areaLeft > 0 && currentItem < items.size()) {
            final T item = items.get(currentItem++);
            float itemArea = item.getColumnSpan() * item.getColumnSpan();

            if (rowHeight < item.getColumnSpan()) {
                // 남은 공간에 들어갈 수 없으면 다음 줄에서 시작한다.
                itemsThatFit.clear();
                rowHeight = item.getColumnSpan();
                currentItem = 0;
                areaLeft = initialSpaceLeft * item.getColumnSpan();
            } else if (areaLeft >= itemArea) {
                // 공간이 있으면 넣는다.
                areaLeft -= itemArea;
                itemsThatFit.add(item);
            } else if (!listView.isAllowReordering()) {
                break;
            }
        }

        return new RowInfo<>(rowHeight, itemsThatFit, areaLeft);
    }

    class ProcessRowsTask extends AsyncTaskCompat<List<T>, Void, List<RowInfo<T>>> {

        @Override
        @SafeVarargs
        protected final List<RowInfo<T>> doInBackground(final List<T>... params) {
            return calculateItemsPerRow(params[0]);
        }

        @Override
        protected void onPostExecute(List<RowInfo<T>> rows) {
            for (RowInfo<T> row : rows)
                itemsPerRow.put(getRowCount(), row);

            notifyDataSetChanged();
        }

        private List<RowInfo<T>> calculateItemsPerRow(final List<T> itemsToAdd) {
            List<RowInfo<T>> rows = new ArrayList<>();

            while (!itemsToAdd.isEmpty()) {
                final RowInfo<T> stuffThatFit = calculateItemsForRow(itemsToAdd);

                final List<T> itemsThatFit = stuffThatFit.getItems();
                if (itemsThatFit.isEmpty()) {
                    // we can't fit a single item inside a row.
                    // bail out.
                    break;
                }

                for (T anItemsThatFit : itemsThatFit)
                    // RowInfo로 완성된 item들은 추가대상 리스트 itemsToAdd에서 제거한다.
                    itemsToAdd.remove(anItemsThatFit);

                rows.add(stuffThatFit);
            }

            return rows;
        }
    }

}
