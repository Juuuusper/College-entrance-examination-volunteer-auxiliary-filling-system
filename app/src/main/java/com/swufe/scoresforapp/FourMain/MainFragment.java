package com.swufe.scoresforapp.FourMain;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.swufe.scoresforapp.DrawerLayoutActivity;
import com.swufe.scoresforapp.GlideImageLoader;
import com.swufe.scoresforapp.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainFragment extends Fragment implements OnBannerListener {

    private Banner banner;
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;

    public static final String CONTENT = "content";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        banner = (Banner) view.findViewById(R.id.banner);
        initview();
        Button btn = view.findViewById(R.id.frag1_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "第一个Fragment", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    private void initview(){


        list_path = new ArrayList<>();
        list_title = new ArrayList<>();

        list_path.add(R.drawable.welcomimg7);
        list_path.add(R.drawable.welcomimg8);
        list_path.add(R.drawable.welcomimg9);
        list_path.add(R.drawable.welcomimg6);

        list_title.add("第一张图片");
        list_title.add("第二张图片");
        list_title.add("第三张图片");
        list_title.add("第四张图片");

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new GlideImageLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点了第"+position+"张轮播图", Toast.LENGTH_SHORT).show();
        Log.i("tag", "你点了第"+position+"张轮播图");
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
