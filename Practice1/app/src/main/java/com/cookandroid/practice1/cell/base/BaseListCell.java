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

    public void initializeView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View cellView = onCreateView(context, inflater);
        if ( cellView != null ) {
            this.addView(cellView);
        }
    }

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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}