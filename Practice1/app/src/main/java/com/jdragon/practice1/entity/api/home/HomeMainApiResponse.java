package com.jdragon.practice1.entity.api.home;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeMainApiResponse extends ApiResponse {
    @SerializedName("Data")
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("HomeMainGroupList")
        private ArrayList<HomeMainGroup> homeMainGroupList;

        public ArrayList<HomeMainGroup> getHomeMainGroupList() {
            return homeMainGroupList;
        }
    }

    public class HomeMainGroup {
        @SerializedName("Type")
        private int type;
        @SerializedName("ItemList")
        private ArrayList<Item> itemList;

        public int getType() {
            return type;
        }

        public ArrayList<Item> getItemList() {
            return itemList;
        }
    }

    public class Item {
        @SerializedName("ItemTitle")
        private String itemTitle;
        @SerializedName("ImageUrl")
        private String imageUrl;

        public String getItemTitle() {
            return itemTitle;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
