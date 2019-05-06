package com.jdragon.library.asymmetricgridview.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.jdragon.library.R;
import com.jdragon.library.asymmetricgridview.AsymmetricGridViewAdapterContract;
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

    protected final AsymmetricGridView listView;
    protected final Context context;
    protected final List<T> items;

    private Map<Integer, RowInfo<T>> itemsPerRow = new HashMap<>();
    private final ViewPool<IcsLinearLayout> linearLayoutPool;
    private final ViewPool<View> viewPool = new ViewPool<>();

    public AsymmetricGridViewAdapter(final Context context,
                                     final AsymmetricGridView listView,
                                     final List<T> items) {

        super(context, 0, items);

        this.linearLayoutPool = new ViewPool<>(new LinearLayoutPoolObjectFactory(context));
        this.items = items;
        this.context = context;
        // 리스트 뷰의 실제 크기를 구해서 사용하기 때문에 특이하게 listview를 인자로 받고 있음
        this.listView = listView;
    }

    public abstract View getActualView(final int position, final View convertView, final ViewGroup parent);

    protected int getRowHeight(final AsymmetricItem item) {
        return getRowHeight(item.getColumnSpan());
    }

    protected int getRowHeight(int rowSpan) {
        final int rowHeight = listView.getColumnWidth() * rowSpan;

        return rowHeight + ((rowSpan - 1) * listView.getRequestedVerticalSpacing());
    }

    protected int getRowWidth(final AsymmetricItem item) {
        return getRowWidth(item.getColumnSpan());
    }

    protected int getRowWidth(int columnSpan) {
        final int rowWidth = listView.getColumnWidth() * columnSpan;

        return Math.min(rowWidth + ((columnSpan - 1) * listView.getRequestedHorizontalSpacing()), Utils.getScreenWidth(getContext()));
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LinearLayout layout = findOrInitializeLayout(convertView);

        final RowInfo<T> rowInfo = itemsPerRow.get(position);
        final List<T> rowItems = new ArrayList<>();
        rowItems.addAll(rowInfo.getItems());

        // 현재 행에서 열 인덱스
        int columnIndex = 0;

        // 아이템 리스트에서 인덱스
        int currentIndex = 0;

        int spaceLeftInColumn = rowInfo.getRowHeight();

        while (!rowItems.isEmpty() && columnIndex < listView.getNumColumns()) {
            final T currentItem = rowItems.get(currentIndex);

            if (spaceLeftInColumn == 0) {
                // 이 컬럼에 자리가 없으니 다음 컬럼으로
                columnIndex++;
                currentIndex = 0;
                spaceLeftInColumn = rowInfo.getRowHeight();
                continue;
            }

            // 들어갈 자리가 있으면
            if (spaceLeftInColumn >= currentItem.getColumnSpan()) {
                rowItems.remove(currentItem);

                int index = items.indexOf(currentItem);
                final LinearLayout childLayout = findOrInitializeChildLayout(layout, columnIndex);
                // 마치 여기서 getView가 일어난 것처럼..
                final View childConvertView = viewPool.get();
                final View v = getActualView(index, childConvertView, parent);
                v.setTag(currentItem);
                v.setOnClickListener(this);
                v.setOnLongClickListener(this);

                spaceLeftInColumn -= currentItem.getColumnSpan();
                currentIndex = 0;

                v.setLayoutParams(new LinearLayout.LayoutParams(getRowWidth(currentItem),
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                // 세로로 레이아웃이 붙음
                childLayout.addView(v);
            } else {
                break;
            }
        }

        return layout;
    }

    @SuppressWarnings("MagicConstant")
    private IcsLinearLayout findOrInitializeLayout(final View convertView) {
        // 세로 divider
        IcsLinearLayout layout;

        if (convertView == null || !(convertView instanceof IcsLinearLayout)) {
            layout = new IcsLinearLayout(context, null);

            // divider를 보여주는 방식 결정
            layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            // divider drawable 세팅
            layout.setDividerDrawable(context.getResources().getDrawable(R.drawable.item_divider_horizontal));
            // layout_width, layout_height 세팅
            layout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT));
        } else
            layout = (IcsLinearLayout) convertView;

        // children 제거
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
        // 가로 divider
        IcsLinearLayout childLayout = (IcsLinearLayout) parentLayout.getChildAt(childIndex);

        if (childLayout == null) {
            childLayout = linearLayoutPool.get();
            childLayout.setOrientation(LinearLayout.VERTICAL);

            childLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

            childLayout.setDividerDrawable(context.getResources().getDrawable(R.drawable.item_divider_vertical));

            childLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                    AbsListView.LayoutParams.MATCH_PARENT));
            // 가로로 레이아웃이 붙음
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

        calculateItemsPerRow(newItems);
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
        // 꼭 row count 리턴해줘야함.
        // item이 더 많더라도 한 row에 getView 1회이기 때문
        return getRowCount();
    }

    public int getRowCount() {
        return itemsPerRow.size();
    }

    @SuppressWarnings("unchecked")
    public void recalculateItemsPerRow() {
        linearLayoutPool.clear();
        viewPool.clear();
        itemsPerRow.clear();

        final List<T> itemsToAdd = new ArrayList<>();
        itemsToAdd.addAll(items);

        calculateItemsPerRow(itemsToAdd);
    }

    // 아이템들을 RowInfo 형태로 바꿔둔다.
    private void calculateItemsPerRow(final List<T> itemsToAdd) {

        for (int i = 0; i < itemsToAdd.size(); i += 3) {
            // 둘
            List<T> itemsThatFit = new ArrayList<>();
            itemsThatFit.add(itemsToAdd.get(i));
            float spaceLeft;
            if (i + 1 >= itemsToAdd.size()) {
                spaceLeft = 1;
            } else {
                spaceLeft = 0;
                itemsThatFit.add(itemsToAdd.get(i+1));
            }
            itemsPerRow.put(getRowCount(), new RowInfo<>(1, itemsThatFit, spaceLeft));

            // 하나
            if (i + 2 >= itemsToAdd.size()) {
                break;
            }
            itemsThatFit = new ArrayList<>();
            itemsThatFit.add(itemsToAdd.get(i+2));
            itemsPerRow.put(getRowCount(), new RowInfo<>(2, itemsThatFit, 0));
        }

        notifyDataSetChanged();
    }

}
