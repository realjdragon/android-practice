package com.jdragon.practice1.entity.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.jdragon.practice1.entity.base.BaseModel;
import com.jdragon.asymmetricgridview.library.model.AsymmetricItem;

// 상품 딜
public class ItemInfo extends BaseModel implements AsymmetricItem {
    private int columnSpan;
    private int rowSpan;
    private int position;

    // 상품 이미지 경로
    String imgUrl;

    // 상품명
    String name;

    public ItemInfo(String imgUrl, String name, final int columnSpan, final int rowSpan, int position) {
        this.imgUrl = imgUrl;
        this.name= name;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
        position = in.readInt();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
        dest.writeInt(position);
    }

    public static final Parcelable.Creator<DemoItem> CREATOR = new Parcelable.Creator<DemoItem>() {

        @Override
        public DemoItem createFromParcel(final Parcel in) {
            return new DemoItem(in);
        }

        @Override
        public DemoItem[] newArray(final int size) {
            return new DemoItem[size];
        }
    };
}
