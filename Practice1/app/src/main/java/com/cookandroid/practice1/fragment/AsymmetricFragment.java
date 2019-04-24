package com.cookandroid.practice1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.practice1.R;
import com.cookandroid.practice1.adapter.widget.ListAdapter;
import com.cookandroid.practice1.entity.data.DemoItem;
import com.cookandroid.practice1.entity.data.ItemInfo;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class AsymmetricFragment extends Fragment {
    View rootView;

    private AsymmetricGridView listView;

    private ListAdapter adapter;

    private int currentOffset = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_asymmetric, container, false);

        listView = rootView.findViewById(R.id.listView);

        adapter = new ListAdapter(getActivity(), listView, new ArrayList<ItemInfo>());
        adapter.appendItems(getMoreItems(50));

        listView.setRequestedColumnCount(2);
        listView.setAdapter(adapter);
        listView.setDebugging(true);

        return rootView;
    }

    private List<ItemInfo> getMoreItems(int qty) {
        final List<ItemInfo> items = new ArrayList<>();

        for (int i = 0; i < qty; i++) {
            int colSpan = 1;
            if (i % 3 == 2) {
                colSpan = 2;
            }
            int rowSpan = colSpan;
            final ItemInfo item = new ItemInfo("https://avatars0.githubusercontent.com/u/29120817?s=460&v=4", "https://github.com/realjdragon", colSpan, rowSpan, currentOffset + i);
            items.add(item);
        }

        currentOffset += qty;

        return items;
    }
}
