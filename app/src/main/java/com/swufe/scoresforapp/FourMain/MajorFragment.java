package com.swufe.scoresforapp.FourMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qlh.dropdownmenu.DropDownMenu;
import com.qlh.dropdownmenu.view.MultiMenusView;
import com.qlh.dropdownmenu.view.SingleMenuView;
import com.swufe.scoresforapp.MainActivity;
import com.swufe.scoresforapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MajorFragment extends Fragment {

    private FragmentActivity instance;
    private String[] headers;//菜单头部选项
    private List<View> popupViews = new ArrayList<>();//菜单列表视图
    private DropDownMenu mDropDownMenu;
    private MultiMenusView multiMenusView;//多级菜单
    private SingleMenuView singleMenuView;//单级菜单
    //内容视图
    private View mView, contentView;

    public static final String CONTENT = "MajorFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_major_fragment, container, false);


        init();

        /*Button btn = mView.findViewById(R.id.frag3_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "第三个Fragment", Toast.LENGTH_SHORT).show();
            }
        });*/
        return mView;
    }

    private void init() {
        instance = this.getActivity();
        initView();
        initMenus();
        initListener();

    }

    private void initView() {

        mDropDownMenu = (DropDownMenu) mView.findViewById(R.id.dropDownMenu);
    }

    @SuppressLint("InflateParams")
    private void initMenus() {

        popupViews.clear();
        headers = new String[]{"专业类别", "开设学校"};
        //初始化多级菜单
        final String[] levelOneMenu = {"医学类", "理学类", "文学类", "工学类"};
        final String[][] levelTwoMenu = {
                {"基础医学", "预防医学", "营养学", "临床医学", "麻醉学", "医学影像", "放射医学", "康复治疗学", "精神医学", "口腔医学", "口腔修复", "中医学"},
                {"数学与应用数学", "信息与计算科学", "数学基础科学", "物理学"},
                {"汉语言文学", "英语", "德语", "法语"},
                {"地质工程", "矿物资源学", "电子信息工程", "水利水电工程"}
        };
        multiMenusView = new MultiMenusView(instance,levelOneMenu,levelTwoMenu);
        popupViews.add(multiMenusView);
        //初始化单级菜单
        String[] addressArrays = {"不限","北京大学", "上海交通大学", "复旦大学", "厦门大学", "武汉大学", "龙城大学"};
        singleMenuView = new SingleMenuView(instance,addressArrays);
        popupViews.add(singleMenuView);
        //初始化内容视图
        contentView = LayoutInflater.from(instance).inflate(R.layout.content_view,null);
        //装载
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,contentView);


    }

    private void initListener() {

        //下拉菜单
        multiMenusView.setOnSelectListener(new MultiMenusView.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                mDropDownMenu.setTabText(showText);
                mDropDownMenu.closeMenu();
            }
        });
        singleMenuView.setOnSelectListener(new SingleMenuView.OnSelectListener() {
            @Override
            public void getValue(int position, String showText) {
                mDropDownMenu.setTabText(showText);
                mDropDownMenu.closeMenu();
            }
        });
    }

}
