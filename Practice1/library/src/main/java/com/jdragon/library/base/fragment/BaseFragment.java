package com.jdragon.library.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class BaseFragment extends Fragment implements BaseInterface {

    private Context context;

    protected SwipeRefreshLayout swipeRefreshLayout;

    protected ViewGroup container;

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        this.container = container;
        rootView = inflater.inflate(inflaterRootView(), container, false);
        initUI();
        initData();
        addListeners();

        if(rootView!=null)
            return rootView;

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected View inflate(@LayoutRes int id){
        return inflate(id,null);
    }

    protected View inflate(@LayoutRes int id, @Nullable ViewGroup root){
        return LayoutInflater.from(context).inflate(id,root);
    }

    protected <T extends View> T findView(int resId){
        return (T)rootView.findViewById(resId);
    }

    protected boolean isFinishing(){
        return getActivity() == null || getActivity().isFinishing() || !isAdded();
    }

    protected void setSwipeRefreshLayout(SwipeRefreshLayout.OnRefreshListener listener, @IdRes int id) {
        swipeRefreshLayout = rootView.findViewById(id);
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @LayoutRes
    public abstract int inflaterRootView();
}