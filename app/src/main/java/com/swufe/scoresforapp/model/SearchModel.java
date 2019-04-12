package com.swufe.scoresforapp.model;

/**
 * Created by ASUS on 2018/7/10.
 */

public interface SearchModel {

    void save(String value);

    void search(String value,OnSearchListener onSearchListener);

    void remove(String key);

    void clear();

    void sortHistory(OnSearchListener onSearchListener);
}
