package com.cookandroid.practice1.entity.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

public class DemoItem implements AsymmetricItem {
    private int columnSpan;
    private int rowSpan;
    private int position;

    public DemoItem() {
        this(1, 1, 0);
    }

    public DemoItem(final int columnSpan, final int rowSpan, int position) {
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.position = position;
    }

    public DemoItem(final Parcel in) {
        readFromParcel(in);
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

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("%s: %sx%s", position, rowSpan, columnSpan);
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

    /* Parcelable interface implementation */
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
