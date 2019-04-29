package com.jdragon.library.base.fragment;

import android.content.Context;
import android.content.Intent;
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

    protected int currentOffset;

    protected boolean isStop;

    public ViewGroup getContainer(){
        return container;
    }

    public View getActivityRootView(){
        return getActivity().findViewById(android.R.id.content);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        this.container = container;
        rootView = inflater.inflate(inflaterRootView(), container, false);
        currentOffset = 0;
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
        isStop = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
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

    // 아래부터는 지금은 쓸모 없지만 넣어봄

    protected void setOnClickListener(@IdRes int id,View.OnClickListener onClicklistener){
        findView(id).setOnClickListener(onClicklistener);
    }

    protected Intent getIntent(){
        return getActivity().getIntent();
    }

    protected Intent getIntent(Class<?> cls){
        return new Intent(context,cls);
    }

    protected Intent getIntent(Class<?> cls,int flags){
        Intent intent = getIntent(cls);
        intent.setFlags(flags);
        return intent;
    }

    protected void startActivity(Class<?> cls){
        startActivity(getIntent(cls));
    }

    protected void startActivity(Class<?> cls,int flags){
        startActivity(getIntent(cls,flags));
    }

    protected void startActivityFinish(Class<?> cls){
        startActivity(cls);
        finish();
    }

    protected void startActivityFinish(Class<?> cls,int flags){
        startActivity(cls,flags);
        finish();
    }

    protected void finish(){
        getActivity().finish();
    }

    protected void setResult(int resultCode){
        setResult(resultCode,getIntent());
    }

    protected void setResult(int resultCode,Intent intent){
        getActivity().setResult(resultCode,intent);
    }

    protected void setIntent(Intent newIntent){
        getActivity().setIntent(newIntent);
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