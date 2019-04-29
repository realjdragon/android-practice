package com.jdragon.library.asymmetricgridview.widget;

import android.view.View;

public interface PoolObjectFactory<T extends View> {
    public T createObject();
}
