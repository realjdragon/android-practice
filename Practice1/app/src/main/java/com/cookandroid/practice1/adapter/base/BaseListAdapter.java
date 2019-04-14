package com.cookandroid.practice1.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cookandroid.practice1.cell.base.BaseListCell;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    private ArrayList<T> mDataList = null;
    private ArrayList<Class> mItemObjectList = null;
    private HashMap<Integer, Class> mItemViewMap = null;
    private Context mContext = null;
//    private OnListCellClickDelegate mOnListCellClickDelegate = null;

//    public static interface OnListCellClickDelegate {
//        public void onClick(View v, BaseListCell parent);
//    }

    public BaseListAdapter(Context context) {
        this.mContext = context;
        mItemObjectList = new ArrayList<Class>();
        mItemViewMap = new HashMap<Integer, Class>();

        initItemViewTypes();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    /**
     * 이 메소드는 OnClickListener 이벤트를 Fragment 나 Activity 에 편하게 전달하기 위해 사용합니다.
     * 작업 절차는 다음과 같습니다.
     *
     * 1. Fragment 나 Activity 에서 OnListCellClickDelegate 를 구현한후에 등록한다.
     * 2. ListCell 에서 addOnListCellClickItem(View v) 메소드를 이용해 이벤트를 받고 싶은 뷰를 등록.
     *
     * @param onClickDelegate
     */
//    public void setOnListCellClickDelegate(OnListCellClickDelegate onClickDelegate) {
//        this.mOnListCellClickDelegate = onClickDelegate;
//    }
//
//    public OnListCellClickDelegate getOnListCellClickDelegate() {
//        return this.mOnListCellClickDelegate;
//    }

    /**
     * 아이템 설정
     *
     * @param dataList
     */
    public void setDataList(ArrayList<T> dataList) {
        this.mDataList = dataList;
        this.notifyDataSetChanged();
    }

    public void addItemViewType(Class itemClass, Class viewClass) {
        if (itemClass == null || viewClass == null) {
            throw new NullPointerException("ItemClass and ViewClass must be not null.");
        }

        mItemObjectList.add(itemClass);
        mItemViewMap.put(mItemViewMap.size(), viewClass);
    }

    public void addItemViewType(int type, Class viewClass) {
        if (viewClass == null) {
            throw new NullPointerException("ViewClass must be not null.");
        }

        mItemViewMap.put(type, viewClass);
    }


    protected abstract void initItemViewTypes();

    @Override
    public int getItemViewType(int position) {
        if (mItemObjectList != null && mItemObjectList.size() != 0) {
            for (int index=0; index < mItemObjectList.size(); index++ ) {
                Class<T> clazz = mItemObjectList.get(index);
                T item = getItem(position);

                if (clazz == item.getClass()) {
                    return index;
                }
            }
        }

        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return mItemViewMap == null ? super.getViewTypeCount() : mItemViewMap.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        BaseListCell<T> cellView = (BaseListCell<T>) view;

        T data = getItem(position);

        if (cellView == null) {
            int itemType = getItemViewType(position);

            try {
                Class<?> viewClazz = mItemViewMap.get(itemType);
                cellView = (BaseListCell<T>) viewClazz.getDeclaredConstructor(Context.class).newInstance(group.getContext());
                cellView.setAdapter(this);
                cellView.initializeView(group.getContext());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (cellView != null) {
            try {
                cellView.setData(data, position);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new NullPointerException("you must add addItemViewType() or override getView()");
        }

        return cellView;
    }
}