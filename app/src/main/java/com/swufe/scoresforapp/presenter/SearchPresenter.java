package com.swufe.scoresforapp.presenter;

/**
 * Created by ASUS on 2018/7/10.
 */

public interface SearchPresenter {

    void remove(String key);

    void clear();

    void sortHistory();

    void search(String value);
}
