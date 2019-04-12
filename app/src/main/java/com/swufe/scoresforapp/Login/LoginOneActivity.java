package com.swufe.scoresforapp.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swufe.scoresforapp.DrawerLayoutActivity;
import com.swufe.scoresforapp.R;

public class LoginOneActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_one);
        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
    }

    private void setListener() {
        //登陆成功
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();;
                explode.setDuration(200);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);


                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginOneActivity.this);
                Intent i2 = new Intent(LoginOneActivity.this,DrawerLayoutActivity.class);
                startActivity(i2, oc2.toBundle());
            }
        });
        //注册
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginOneActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginOneActivity.this, LoginTwoActivity.class), options.toBundle());

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }
}
