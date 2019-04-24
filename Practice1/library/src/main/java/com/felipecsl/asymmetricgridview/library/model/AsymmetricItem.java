package com.felipecsl.asymmetricgridview.library.model;

import android.os.Parcelable;

// 비대칭 아이템들은 columnSpan, rowSpan을 무조건 가져야 하나봄
public interface AsymmetricItem extends Parcelable {
    public int getColumnSpan();
    public int getRowSpan();
}
