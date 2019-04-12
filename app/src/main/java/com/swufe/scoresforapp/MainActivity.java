package com.swufe.scoresforapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.swufe.scoresforapp.Login.LoginOneActivity;

import ezy.ui.layout.LoadingLayout;

public class MainActivity extends AppCompatActivity {

    private LoadingLayout mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingLayout = LoadingLayout.wrap(this);
        mLoadingLayout.showContent();


    }

    public void doEffectA(View v){
        startActivity(new Intent(this, LoginOneActivity.class));
    }


}
