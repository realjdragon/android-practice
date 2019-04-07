package com.cookandroid.practice1.entity;

// 상품 딜
public class ItemDeal {
    // 상품 이미지 경로
    String imgUrl;

    // 상품명
    String name;

    public ItemDeal(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name= name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
