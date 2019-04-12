package com.swufe.scoresforapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ASUS on 2018/7/9.
 */

public class MyViewPager extends ViewPager {

    private boolean scrollable = false;

    public MyViewPager(@NonNull Context context) {
        super(context);;
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);;
    }

    public void setScrollable(boolean scrollable){
        this.scrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable && super.onTouchEvent(ev);
    }
}
