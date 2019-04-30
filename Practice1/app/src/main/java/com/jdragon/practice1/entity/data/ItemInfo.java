package com.jdragon.practice1.entity.data;

import com.jdragon.library.asymmetricgridview.model.AsymmetricItem;
import com.jdragon.library.base.entity.BaseModel;

// 상품 딜
public class ItemInfo extends BaseModel implements AsymmetricItem {
    private int columnSpan;

    // 상품 이미지 경로
    String imgUrl;

    // 상품명
    String name;

    public ItemInfo(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name= name;
        this.columnSpan = 1;
    }

    public ItemInfo(String imgUrl, String name, final int columnSpan) {
        this.imgUrl = imgUrl;
        this.name= name;
        this.columnSpan = columnSpan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }
}
