package com.swufe.scoresforapp.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;

import com.swufe.scoresforapp.R;

public class LoginThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_three);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

    }
}
