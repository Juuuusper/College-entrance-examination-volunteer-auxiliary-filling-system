package com.swufe.scoresforapp.FourMain;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.swufe.scoresforapp.DrawerLayoutActivity;
import com.swufe.scoresforapp.MainActivity;
import com.swufe.scoresforapp.R;
import com.swufe.scoresforapp.adapter.RecyclerAdapter;
import com.swufe.scoresforapp.bean.SchoolBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import q.rorbin.badgeview.QBadgeView;

public class SchoolFragment extends Fragment {

    public static final String CONTENT = "content";
    private View mView;
    private RecyclerView mRecyclerView;
    private ImageView ivCart; //VSimageView
    private List<SchoolBean> mSchoolBeans = new ArrayList<>();

    private RelativeLayout rl;
    private PathMeasure mPathMeasure;

    private RecyclerAdapter mAdapter;

    QBadgeView mQBadgeView;//小圆点

    private boolean isShowFloatImage = true;
    private float startY;
    private int moveDistance;
    private Timer timer;
    /**用户手指按下后抬起的实际*/
    private long upTime;

    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];
    private int i = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_school_fragment, container, false);

        //注册接受DrawLayoutActivity的Touch回调对象
        //重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
        DrawerLayoutActivity.MyTouchListener myTouchListener = new DrawerLayoutActivity.MyTouchListener() {
            @Override
            public void onTouchEvent(MotionEvent event) {
                //处理手势事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (System.currentTimeMillis() - upTime < 1500) {
                            //本次按下距离上次的抬起小于1.5s时，取消Timer
                            timer.cancel();
                        }
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(startY - event.getY()) > 10) {
                            if (isShowFloatImage){
                                hideFloatImage(moveDistance);
                            }
                        }
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isShowFloatImage){
                            //抬起手指1.5s后再显示悬浮按钮
//                new Handler(new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(Message msg) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                showFloatImage(moveDistance);
//                            }
//                        });
//                        return false;
//                    }
//                }).sendEmptyMessageDelayed(0, 1500);

                            //开始1.5s倒计时
                            upTime = System.currentTimeMillis();
                            timer = new Timer();
                            timer.schedule(new FloatTask(), 1500);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        //将myTouchListener注册到分发列表
        ((DrawerLayoutActivity)this.getActivity()).registerMyTouchListener(myTouchListener);
        /*Button btn = view.findViewById(R.id.frag2_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "第二个Fragment", Toast.LENGTH_SHORT).show();
            }
        });*/
        initData();
        initView();


        return mView;
    }

    private void initData() {
        for (int i = 0; i < 60; i++) {
            mSchoolBeans.add(new SchoolBean("学校" + i));
        }
    }

    private void initView() {
        mRecyclerView =  mView.findViewById(R.id.recycler);
        mAdapter = new RecyclerAdapter(getContext(), mSchoolBeans);
        mQBadgeView = new QBadgeView(getContext());
        ivCart = (ImageView) mView.findViewById(R.id.iv_cart);
        rl = (RelativeLayout) mView.findViewById(R.id.school_rl);
        mQBadgeView.bindTarget(ivCart).setBadgeGravity(Gravity.END | Gravity.TOP);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "收藏", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //控件绘制完成之后再获取其宽高
        ivCart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("Tag", "控件宽度" + ivCart.getWidth());
                moveDistance = getDisplayMetrics(getContext())[0] - ivCart.getRight() + ivCart.getWidth() / 2;
                //监听结束之后移除监听事件
                ivCart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }



    class FloatTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showFloatImage(moveDistance);
                }
            });
        }
    }


    /**
     * 显示悬浮图标
     *
     * @param distance
     */
    private void showFloatImage(int distance) {
        Log.e("Tag", "显示动画执行");
        isShowFloatImage = true;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                distance,//起始x坐标
                0,//结束x坐标
                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(0.5f, 1f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        ivCart.startAnimation(set);
    }

    /**
     * 隐藏悬浮图标
     *
     * @param distance
     */
    private void hideFloatImage(int distance) {
        Log.e("Tag", "隐藏动画执行");
        isShowFloatImage = false;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                0,//起始x坐标,10表示与初始位置相距10
                distance,//结束x坐标
                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(1f, 0.5f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        ivCart.startAnimation(set);

    }

    //得到手机屏幕宽高
    private int[] getDisplayMetrics(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int widthPixels = mDisplayMetrics.widthPixels;
        int heightPixels = mDisplayMetrics.heightPixels;
        int[] array = {widthPixels, heightPixels};
        return array;
    }

    /**★★★★★点击加入对比按钮，调用该方法
     * 把商品添加到购物车的动画效果★★★★★
     * @param iv
     */
    private void addCart( ImageView iv) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(getActivity());
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        rl.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        ivCart.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + ivCart.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
                i++;
                if( i <= 4)
                    mQBadgeView.setBadgeNumber(i);
                // 把移动的图片imageview从父布局里移除
                rl.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



}
