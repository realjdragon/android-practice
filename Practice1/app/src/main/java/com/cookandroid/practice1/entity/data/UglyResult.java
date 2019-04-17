package com.cookandroid.practice1.entity.data;

import com.cookandroid.practice1.entity.api.github.SearchUsersApiResponse;
import com.cookandroid.practice1.entity.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class UglyResult extends BaseModel {
    public class SingleResult extends BaseModel {
        public String imgUrl;
        public String name;
    }

    public class DoubleResult extends BaseModel {
        public String imgUrl1;
        public String name1;
        public String imgUrl2;
        public String name2;
    }

    public List<BaseModel> makeUglyList(ArrayList<SearchUsersApiResponse.User> users, boolean isFirst) {
        List<BaseModel> results = new ArrayList<BaseModel>();

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
                SingleResult singleResult = new SingleResult();
                singleResult.imgUrl = users.get(i).getAvatarUrl();
                singleResult.name = users.get(i).getUrl();

                results.add(singleResult);
            }
        }

        return results;
    }
}
