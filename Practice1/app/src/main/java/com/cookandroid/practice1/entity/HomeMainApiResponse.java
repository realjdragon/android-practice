package com.cookandroid.practice1.entity;

import java.util.ArrayList;

public class HomeMainApiResponse extends ApiResponse {
    public Data Data;

    public class Data {
        public ArrayList<HomeMainGroup> HomeMainGroupList;
    }

    public class HomeMainGroup {
        public int Type;

        public ArrayList<Item> ItemList;
    }

    public class Item {
        public String ItemTitle;

        public String ImageUrl;
    }
}
