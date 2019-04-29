package com.jdragon.practice1.fragment.base;

import android.app.Dialog;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jdragon.practice1.R;

public abstract class BaseFragment extends Fragment implements BaseInterface {

    private Context context;

    protected Dialog dialog;

    protected SwipeRefreshLayout swipeRefreshLayout;

    protected ViewGroup container;

    protected View rootView;

    protected int curPage;

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
        curPage = 1;
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

    public void replaceChildFragment(@IdRes int resId, Fragment fragment) {
        replaceFragment(getChildFragmentManager(),resId,fragment,false);
    }

    public void replaceFragment(@IdRes int resId, Fragment fragment){
        replaceFragment(resId,fragment,false);
    }

    public void replaceFragment(@IdRes int resId, Fragment fragment, boolean isBackStack) {
        replaceFragment(getFragmentManager(),resId,fragment,isBackStack);
    }

    public void replaceFragment(FragmentManager fragmentManager, @IdRes int resId, Fragment fragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(resId, fragment);
        if(isBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    protected void setSwipeRefreshLayout(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    protected void setSwipeRefreshLayout(SwipeRefreshLayout.OnRefreshListener listener, int id) {
        swipeRefreshLayout = rootView.findViewById(id);
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @LayoutRes
    public abstract int inflaterRootView();
}