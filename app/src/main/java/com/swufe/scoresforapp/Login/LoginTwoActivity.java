package com.swufe.scoresforapp.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.swufe.scoresforapp.DrawerLayoutActivity;
import com.swufe.scoresforapp.R;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class LoginTwoActivity extends AppCompatActivity{

    private FloatingActionButton fab;
    private CardView cvAdd;
    private EditText userName;
    private EditText password;
    private EditText repeatPassword;

    private NiceSpinner proSpinner;
    private NiceSpinner typeSpinner;
    private NiceSpinner yearSpinner;

    private EditText score;

    private Button btn_register;

    //private OptionsPickerView pvOptions;//选择器
    private ArrayList<String> pro = new ArrayList<>(Arrays.asList("山东省", "安徽省", "江西省", "上海市"));
    private ArrayList<String> type = new ArrayList<>(Arrays.asList("文科", "理科"));
    private ArrayList<String> year = new ArrayList<>(Arrays.asList("2018", "2019"));
    //private ArrayList<String> province = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_two);

        ShowEnterAnimation();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        //proSpinner.setOnItemSelectedListener(this);
        proSpinner.attachDataSource(pro);
        /*proSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        typeSpinner.attachDataSource(type);
        /*typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        yearSpinner.attachDataSource(year);
        /*yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        setListener();
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        repeatPassword = findViewById(R.id.et_repeatpassword);
        score = findViewById(R.id.et_score);
        proSpinner = (NiceSpinner) findViewById(R.id.et_proSpinner);
        typeSpinner = (NiceSpinner) findViewById(R.id.et_typeSpinner);
        yearSpinner = (NiceSpinner) findViewById(R.id.et_yearSpinner);
        btn_register = findViewById(R.id.bt_go);

    }

    private void setListener(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();;
                explode.setDuration(200);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);


                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginTwoActivity.this);
                Intent i2 = new Intent(LoginTwoActivity.this,DrawerLayoutActivity.class);
                startActivity(i2, oc2.toBundle());
            }
        });
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });

    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(200);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0,  cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(200);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                LoginTwoActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String content = parent.getItemAtPosition(position).toString();

        Resources res = getResources();
        pro = res.getStringArray(R.array.province);
        *//*for(String p : pro){
            province.add(p);
        }*//*

        *//*pvOptions = new OptionsPickerBuilder(LoginTwoActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回分别是三个级别选中位置
                String tx = province.get(options1);
                province.set(tx);
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")//取消按钮文字
                .setTitleText("省份选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                //.setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(province);
        pvOptions.show();*//*
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
