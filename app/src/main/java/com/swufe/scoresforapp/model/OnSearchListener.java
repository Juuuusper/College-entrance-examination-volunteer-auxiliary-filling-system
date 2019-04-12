package com.swufe.scoresforapp.model;

import com.swufe.scoresforapp.bean.SearchHistoryModel;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/7/10.
 */

public interface OnSearchListener {

    void onSortSuccess(ArrayList<SearchHistoryModel> results);

    void searchSuccess(String value);
}
