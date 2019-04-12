package com.swufe.scoresforapp.HistoryStorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.swufe.scoresforapp.bean.SearchHistoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by ASUS on 2018/7/10.
 */

public class SpHistoryStorage extends BaseHistoryStorage {

    private Context context;
    public static final String SEARCH_HISTORY = "search_history";
    private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private int HISTORY_MAX = 5;
    private static SpHistoryStorage instance;

    private SpHistoryStorage(Context context, int historyMax) {
        this.context = context.getApplicationContext();
        this.HISTORY_MAX = historyMax;
    }

    public static synchronized SpHistoryStorage getInstance(Context context, int historyMax) {
        if (instance == null) {
            synchronized (SpHistoryStorage.class) {
                if (instance == null) {
                    instance = new SpHistoryStorage(context, historyMax);
                }
            }
        }
        return instance;
    }

    //保存的方法，需要先判断是否已经存在，存在的话就先删除然后根据最新的时间保存
    @Override
    public void save(String value) {
        Map<String, String> historys = (Map<String, String>) getAll();
        for (Map.Entry<String, String> entry : historys.entrySet()) {
            if (value.equals(entry.getValue())) {
                remove(entry.getKey());
            }
        }
        put(generateKey(), value);
    }

    @Override
    public void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(SEARCH_HISTORY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    @Override
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(SEARCH_HISTORY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    //为了存储的时候根据当前时间生成的一个Key,用来判断先后顺序
    @Override
    public String generateKey() {
        return mFormat.format(new Date());
    }

    //从SharedPreferences取出所有的值并且按照时间先后进行排序
    @Override
    public ArrayList<SearchHistoryModel> sortHistory() {
        Map<String, ?> allHistory = getAll();
        ArrayList<SearchHistoryModel> mResults = new ArrayList<>();
        Map<String, String> hisAll = (Map<String, String>) getAll();
        //将key排序升序
        Object[] keys = hisAll.keySet().toArray();
        Arrays.sort(keys);
        int keyLeng = keys.length;
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        int hisLeng = keyLeng > HISTORY_MAX ? HISTORY_MAX : keyLeng;
        for (int i = 1; i <= hisLeng; i++) {
            mResults.add(new SearchHistoryModel((String) keys[keyLeng - i], hisAll.get(keys[keyLeng - i])));
        }
        return mResults;
    }

    public boolean contains(String key) {
        SharedPreferences sp = context.getSharedPreferences(SEARCH_HISTORY,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(SEARCH_HISTORY,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public void put(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SEARCH_HISTORY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
