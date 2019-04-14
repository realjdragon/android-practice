package com.cookandroid.practice1.entity.data;

// 상품 딜
public class ItemInfo {
    // 상품 이미지 경로
    String imgUrl;

    // 상품명
    String name;

    public ItemInfo(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name= name;
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
}
