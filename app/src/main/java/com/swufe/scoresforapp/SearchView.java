package com.swufe.scoresforapp;

import com.swufe.scoresforapp.bean.SearchHistoryModel;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/7/10.
 */

public interface SearchView {

    void showHistories(ArrayList<SearchHistoryModel> results);

    void searchSuccess(String value);
}
