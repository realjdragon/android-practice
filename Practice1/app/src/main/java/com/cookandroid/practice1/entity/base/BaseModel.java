package com.cookandroid.practice1.entity.base;

import java.util.ArrayList;
import java.util.List;

public class BaseModel {
    public static final int VIEW_TYPE_NONE = -1;
    private List wrapItems;

    private int viewType = VIEW_TYPE_NONE;

    private Object tag;

    public BaseModel() {
    }

    public static BaseModel wrap(List items) {
        BaseModel newModel = new BaseModel();
        newModel.wrapItems = items;

        return newModel;
    }

    public static BaseModel wrap(int viewType, List items) {
        BaseModel newModel = new BaseModel();
        newModel.viewType = viewType;
        newModel.wrapItems = items;

        return newModel;
    }

    public List getWrapItems() {
        return wrapItems;
    }

    public int getViewTypeId() {
        return viewType;
    }

    public void setViewTypeId(int viewType) {
        this.viewType = viewType;
    }

    public ArrayList<? extends BaseModel> getChildren() {
        return null;
    }

    public final void setTag(Object tag) {
        this.tag = tag;
    }

    public final Object getTag() {
        return tag;
    }

}
