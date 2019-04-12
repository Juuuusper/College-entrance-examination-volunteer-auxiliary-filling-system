package com.swufe.scoresforapp.bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2018/7/10.
 */

public class SearchHistoryModel implements Serializable {

    private String time;
    private String content;

    public SearchHistoryModel() {
    }

    public SearchHistoryModel(String time, String content) {

        this.content = content;
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
