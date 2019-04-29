package com.jdragon.practice1.entity.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.jdragon.library.base.entity.BaseModel;
import com.jdragon.library.asymmetricgridview.model.AsymmetricItem;

// 상품 딜
public class ItemInfo extends BaseModel implements AsymmetricItem {
    private int columnSpan;
    private int rowSpan;

    // 상품 이미지 경로
    String imgUrl;

    // 상품명
    String name;

    public ItemInfo(String imgUrl, String name, final int columnSpan, final int rowSpan) {
        this.imgUrl = imgUrl;
        this.name= name;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
    }

    public ItemInfo(final Parcel in) {
        readFromParcel(in);
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

    // 비대칭 아이템이라 아래 두 개의 메서드를 구현한거임.
    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(final Parcel in) {
        columnSpan = in.readInt();
        rowSpan = in.readInt();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
    }

    public static final Parcelable.Creator<ItemInfo> CREATOR = new Parcelable.Creator<ItemInfo>() {

        @Override
        public ItemInfo createFromParcel(final Parcel in) {
            return new ItemInfo(in);
        }

        @Override
        public ItemInfo[] newArray(final int size) {
            return new ItemInfo[size];
        }
    };
}
