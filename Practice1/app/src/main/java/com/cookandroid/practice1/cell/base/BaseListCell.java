package com.cookandroid.practice1.cell.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cookandroid.practice1.adapter.base.BaseListAdapter;

import java.lang.ref.WeakReference;

public abstract class BaseListCell<T> extends FrameLayout {
    public T data;
    public int position;
    protected WeakReference<BaseListAdapter<T>> parentListAdapter;

    public BaseListCell(Context context) {
        super(context);
    }

    public BaseListCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(BaseListAdapter<T> adapter) {
        this.parentListAdapter = new WeakReference<BaseListAdapter<T>>(adapter);
    }

    public BaseListAdapter getAdapter() {
        if ( parentListAdapter != null && parentListAdapter.get() != null ) {
            return parentListAdapter.get();
        }

        return null;
    }

//    protected void addOnListCellClickItem(View v) {
//        if ( getAdapter().getOnListCellClickDelegate() != null ) {
//            v.setOnClickListener(onListCellClickDelegateListener);
//        } else {
//            throw new IllegalStateException("ListAdapter 에서 OnListCellClickDelegate 를 설정한 후에 사용할 수 있습니다.");
//        }
//    }

    /**
     * 지정한 View 를 생성합니다.
     * @param context
     */
    public void initializeView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View cellView = onCreateView(context, inflater);
        if ( cellView != null ) {
            this.addView(cellView);
        }
    }

//    public View createView(Context context, T data) {
//        initializeView(context);
//        setData(data);
//
//        return this;
//    }

    /**
     * View 를 생성하고, Holder 패턴을 인스턴스 변수로 지정하여 구현한다.
     * @param context
     * @return
     */
    public abstract View onCreateView(Context context, LayoutInflater inflater);

    public void setData(T data, int position) {
        setPosition(position);
        setData(data);
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

//    private View.OnClickListener onListCellClickDelegateListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if ( getAdapter().getOnListCellClickDelegate() != null ) {
//                getAdapter().getOnListCellClickDelegate().onClick(v, BaseListCell.this);
//            }
//        }
//    };

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}