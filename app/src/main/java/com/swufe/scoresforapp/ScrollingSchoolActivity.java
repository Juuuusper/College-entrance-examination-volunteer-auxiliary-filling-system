package com.swufe.scoresforapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class ScrollingSchoolActivity extends AppCompatActivity{

    private TextView school_tel;  //招生电话
    private TextView school_place;  //院校所在地
    private TextView school_type;  //院校类别
    private TextView school_main_num;   //重点学科数
    private TextView school_part;  //所属部门
    private TextView school_shuoshi;  //硕士点数
    private TextView school_boshi;  //博士点数
    private TextView school_is_985;  //是否985
    private TextView school_is_211;  //是否211


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_school);

        initData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("北京大学");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void initData(){
        school_tel = findViewById(R.id.school_tel);
        school_place = findViewById(R.id.school_place);
        school_type = findViewById(R.id.school_type);
        school_main_num = findViewById(R.id.school_main_num);
        school_part = findViewById(R.id.school_part);
        school_shuoshi = findViewById(R.id.school_shuoshi);
        school_boshi = findViewById(R.id.school_boshi);
        school_is_985 = findViewById(R.id.school_is_985);
        school_is_211 = findViewById(R.id.school_is_211);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
