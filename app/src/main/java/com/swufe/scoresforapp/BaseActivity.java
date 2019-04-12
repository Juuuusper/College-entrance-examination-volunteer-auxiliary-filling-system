package com.swufe.scoresforapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected FrameLayout mContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mToolbar = findViewById(R.id.mdToolbar);
        mContainerLayout = findViewById(R.id.mdFrame);
        boolean isToolbar = initToolbar();
        if (isToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
        }

        initContent(getLayoutId());

    }

    private void initContent(int layoutId) {
        if (layoutId != 0) {
            View contentView = LayoutInflater.from(this).inflate(layoutId, mContainerLayout, false);
            mContainerLayout.addView(contentView);
            ButterKnife.bind(this);
            initViews();
        }
    }

    /**
     * 初始化布局
     */
    protected abstract void initViews();

    /**
     * 获取布局控件
     *
     * @return
     */
    protected abstract int getLayoutId();

    private void onNavigationClick() {
        finish();
    }

    /**
     * 初始化Toolbar
     *
     * @return
     */
    protected abstract boolean initToolbar();

}
