package com.swufe.scoresforapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.gifloadinglibrary.GifLoadingView;

import java.util.ArrayList;
import java.util.List;

import ezy.ui.layout.LoadingLayout;
import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

public class ScrollingCompareActivity extends AppCompatActivity {


    private LoadingLayout mLoadingLayout;
    private GifLoadingView mGifLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_compare);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的对比");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mLoadingLayout = LoadingLayout.wrap(this);
        //mLoadingLayout.setLoading(R.layout.loading_view);
        //mLoadingLayout.showLoading();

        mGifLoadingView = new GifLoadingView();
        mGifLoadingView.setImageResource(R.drawable.loading);
        mGifLoadingView.setBackGroundColor(R.color.loading_color);
        mGifLoadingView.show(getFragmentManager(), "");






        // 注意：
        // 如果你决定自己实现自己的Column，而不是使用默认的1，2，3。。。
        // 请注意更新contentList时手动更新columnList


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


}
