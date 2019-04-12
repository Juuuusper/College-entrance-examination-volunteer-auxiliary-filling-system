package com.swufe.scoresforapp.HistoryStorage;

import com.swufe.scoresforapp.bean.SearchHistoryModel;

import java.util.ArrayList;

/**
 * 历史信息存储基类
 * Created by ASUS on 2018/7/10.
 */

public abstract class BaseHistoryStorage {

    /**
     * 保存历史记录时调用
     *
     * @param value
     */
    public abstract void save(String value);

    /**
     * 移除单条历史记录
     *
     * @param key
     */
    public abstract void remove(String key);

    /**
     * 清空历史记录
     */
    public abstract void clear();

    /**
     * 生成key
     *
     * @return
     */
    public abstract String generateKey();

    /**
     * 返回排序好的历史记录
     *
     * @return
     */
    public abstract ArrayList<SearchHistoryModel> sortHistory();
}
