package com.swufe.scoresforapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import java.util.Date;

/**
 * Created by ASUS on 2018/7/10.
 */

@Entity(indexes = {
        @Index(value = "text, date DESC", unique = true)
})
public class SearchRecord {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String text;
    private java.util.Date date;

    public SearchRecord(Long id, @NotNull String text, java.util.Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public SearchRecord() {
    }

    public SearchRecord(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
