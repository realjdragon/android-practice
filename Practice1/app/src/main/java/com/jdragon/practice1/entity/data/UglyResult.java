package com.jdragon.practice1.entity.data;

import com.jdragon.practice1.entity.api.github.SearchUsersApiResponse;
import com.jdragon.library.base.entity.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class UglyResult extends BaseModel {
    private ArrayList<SearchUsersApiResponse.User> users;

    public ArrayList<SearchUsersApiResponse.User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<SearchUsersApiResponse.User> users) {
        this.users = users;
    }

    public class OddResult extends BaseModel {
        private String imgUrl;
        private String name;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class EvenResult extends BaseModel {
        private String imgUrl;
        private String name;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class DoubleResult extends BaseModel {
        private String imgUrl1;
        private String name1;
        private String imgUrl2;
        private String name2;

        public String getImgUrl1() {
            return imgUrl1;
        }

        public void setImgUrl1(String imgUrl1) {
            this.imgUrl1 = imgUrl1;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getImgUrl2() {
            return imgUrl2;
        }

        public void setImgUrl2(String imgUrl2) {
            this.imgUrl2 = imgUrl2;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }
    }

    public List<BaseModel> makeUglyList(boolean isFirst) {
        List<BaseModel> results = new ArrayList<>();

        if (isFirst) {
            for (int i = 0; i < users.size(); i += 2) {
                DoubleResult doubleResult = new DoubleResult();
                doubleResult.imgUrl1 = users.get(i).getAvatarUrl();
                doubleResult.name1 = users.get(i).getUrl();
                doubleResult.imgUrl2 = users.get(i+1).getAvatarUrl();
                doubleResult.name2 = users.get(i+1).getUrl();

                results.add(doubleResult);
            }
        }
        else {
            for (int i = 0; i < users.size(); i++) {
                if (i % 2 == 0) {
                    OddResult oddResult = new OddResult();
                    oddResult.imgUrl = users.get(i).getAvatarUrl();
                    oddResult.name = users.get(i).getUrl();
                    results.add(oddResult);
                } else {
                    EvenResult evenResult = new EvenResult();
                    evenResult.imgUrl = users.get(i).getAvatarUrl();
                    evenResult.name = users.get(i).getUrl();
                    results.add(evenResult);
                }
            }
        }

        return results;
    }
}
