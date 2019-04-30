package com.jdragon.library.asymmetricgridview;

import android.widget.ListAdapter;

public interface AsymmetricGridViewAdapterContract extends ListAdapter {
    public void recalculateItemsPerRow();

    public void notifyDataSetChanged();
}
