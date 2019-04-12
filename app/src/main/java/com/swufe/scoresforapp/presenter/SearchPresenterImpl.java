package com.swufe.scoresforapp.presenter;

import android.content.Context;

import com.swufe.scoresforapp.SearchView;
import com.swufe.scoresforapp.bean.SearchHistoryModel;
import com.swufe.scoresforapp.model.OnSearchListener;
import com.swufe.scoresforapp.model.SearchModel;
import com.swufe.scoresforapp.model.SearchModelImpl;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/7/10.
 */

public class SearchPresenterImpl implements SearchPresenter, OnSearchListener {
    private static final int historyMax = 5;
    private SearchView searchView;
    private SearchModel searchModel;

    public SearchPresenterImpl(SearchView searchView, Context context) {
        this.searchView = searchView;
        this.searchModel = new SearchModelImpl(context, historyMax);
    }

    //移除历史记录
    @Override
    public void remove(String key) {
        searchModel.remove(key);
        searchModel.sortHistory(this);
    }

    @Override
    public void clear() {
        searchModel.clear();
        searchModel.sortHistory(this);
    }

    //获取所有的历史记录
    @Override
    public void sortHistory() {
        searchModel.sortHistory(this);
    }

    @Override
    public void search(String value) {
        searchModel.save(value);
        searchModel.search(value, this);
    }

    @Override
    public void onSortSuccess(ArrayList<SearchHistoryModel> results) {
        searchView.showHistories(results);
    }

    @Override
    public void searchSuccess(String value) {
        searchView.searchSuccess(value);
    }
}
