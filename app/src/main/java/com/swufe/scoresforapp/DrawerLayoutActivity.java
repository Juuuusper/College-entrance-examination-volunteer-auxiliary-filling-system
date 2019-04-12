package com.swufe.scoresforapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.swufe.scoresforapp.FourMain.MainFragment;
import com.swufe.scoresforapp.FourMain.MajorFragment;
import com.swufe.scoresforapp.FourMain.MoreFragment;
import com.swufe.scoresforapp.FourMain.SchoolFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerLayoutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar1)
    Toolbar mToolbar;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    //保存MyTouchListener接口列表
    private ArrayList<MyTouchListener> mMyTouchListeners = new ArrayList<MyTouchListener>();

    private ActionBarDrawerToggle mToggle;
    //private int index;

    //private List<TabFragment> mFragmentList = new ArrayList<TabFragment>();
    private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    private MainFragment mMainFragment;
    private SchoolFragment mSchoolFragment;
    private MajorFragment mMajorFragment;
    private MoreFragment mMoreFragment;


    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        ButterKnife.bind(this);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mToolbar.setTitle("首页");
        //设置Toolbar
        setToolbar();

        //设置DrawerToggle 开关
        setDrawerToggle();

        /*mNavigationView.setItemIconTintList(null);*/

        //设置监听器
        setDrawerLayoutListener();

        initView();
        initListener();


    }

    private void initView() {

        //首页
        mMainFragment = new MainFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(MainFragment.CONTENT, getResources().getString(R.string.home));
        mMainFragment.setArguments(bundle1);
        mFragmentList.add(mMainFragment);

        //学校
        mSchoolFragment = new SchoolFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(SchoolFragment.CONTENT, getResources().getString(R.string.school));
        mSchoolFragment.setArguments(bundle2);
        mFragmentList.add(mSchoolFragment);

        //专业
        mMajorFragment = new MajorFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString(MajorFragment.CONTENT, getResources().getString(R.string.major));
        mMajorFragment.setArguments(bundle3);
        mFragmentList.add(mMajorFragment);

        //更多
        mMoreFragment = new MoreFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString(MoreFragment.CONTENT, getResources().getString(R.string.more));
        mMoreFragment.setArguments(bundle4);
        mFragmentList.add(mMoreFragment);

        /*mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                reflushView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        mVpContent.setAdapter(new FragAdapter(getSupportFragmentManager()));
    }


    private void initListener() {

        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                /*mToolbar.setTitle(bottomBarItem.getAccessibilityClassName());
                setToolbar();
                setDrawerToggle();*/
                Log.i("DrawerLayoutActivity", "position: " + currentPosition);
                if (currentPosition == 0) {
                    //如果是第一个，即首页
                    if (previousPosition == currentPosition) {
                        //如果是在原来位置上点击,更换首页图标并播放旋转动画
                        bottomBarItem.setIconSelectedResourceId(R.drawable.ic_loading);//更换成加载图标
                        bottomBarItem.setStatus(true);

                        //播放旋转动画
                        if (mRotateAnimation == null) {
                            mRotateAnimation = new RotateAnimation(0, 360,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                    0.5f);
                            mRotateAnimation.setDuration(800);
                            mRotateAnimation.setRepeatCount(-1);
                        }
                        ImageView bottomImageView = bottomBarItem.getImageView();
                        bottomImageView.setAnimation(mRotateAnimation);
                        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                        //模拟数据刷新完毕
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean tabNotChanged = mBottomBarLayout.getCurrentItem() == currentPosition; //是否还停留在当前页签
                                bottomBarItem.setIconSelectedResourceId(R.drawable.ic_home_selected);//更换成首页原来选中图标
                                bottomBarItem.setStatus(tabNotChanged);//刷新图标
                                cancelTabLoading(bottomBarItem);
                            }
                        }, 3000);
                        return;
                    }
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.drawable.ic_home_selected);//更换为原来的图标

                cancelTabLoading(bottomItem);//停止旋转动画


            }


        });


        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字
    }

    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    class FragAdapter extends FragmentPagerAdapter {


        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


    private void setDrawerLayoutListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_favorite:
                        Toast.makeText(DrawerLayoutActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                        mToolbar.setTitle(R.string.favorite);
                        setToolbar();
                        //setDrawerToggle();
                        Intent intent = new Intent(DrawerLayoutActivity.this, CollectionActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_about:
                        Toast.makeText(DrawerLayoutActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                        mToolbar.setTitle(R.string.about_us);
                        setToolbar();
                        //setDrawerToggle();
                        break;
                    case R.id.menu_exit:
                        Toast.makeText(DrawerLayoutActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                        mToolbar.setTitle(R.string.exit);
                        setToolbar();
                        //setDrawerToggle();
                        break;
                    case R.id.menu_home:
                        Toast.makeText(DrawerLayoutActivity.this, "首页", Toast.LENGTH_SHORT).show();
                        mToolbar.setTitle(R.string.home);
                        setToolbar();
                        //setDrawerToggle();
                        break;
                    default:
                        break;
                }
                //关闭侧边栏
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setDrawerToggle() {
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        //同步DrawerLayout的状态
        mToggle.syncState();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        // 显示Home的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
/*

    private void reflushView(){
        mVpContent.setCurrentItem(index);
        mNavigationView.getMenu().getItem(index).setChecked(true);
        invalidateOptionsMenu();
        switch (index) {
            case 0:
                mToolbar.setTitle(R.string.home);
                setToolbar();
                setDrawerToggle();
                break;
            case 1:
                mToolbar.setTitle(R.string.school);
                setToolbar();
                setDrawerToggle();
                break;
            case 2:
                mToolbar.setTitle(R.string.major);
                setToolbar();
                setDrawerToggle();
                break;
            case 3:
                mToolbar.setTitle(R.string.more);
                setToolbar();
                setDrawerToggle();
                break;
        }
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item1 = menu.findItem(R.id.menu_item1);
        MenuItem item2 = menu.findItem(R.id.menu_item2);
        MenuItem item3 = menu.findItem(R.id.menu_item3);
        MenuItem item4 = menu.findItem(R.id.menu_item4);
        switch (mVpContent.getCurrentItem()) {
            case 0:
                mToolbar.setTitle(R.string.home);
                setToolbar();
                setDrawerToggle();
                //mNavigationView.getMenu().getItem(0).setChecked(true);
                item1.setVisible(true);
                item2.setVisible(false);
                item3.setVisible(false);
                item4.setVisible(false);
                break;
            case 1:
                mToolbar.setTitle(R.string.school);
                setToolbar();
                setDrawerToggle();
                //mNavigationView.getMenu().getItem(0).setCheckable(false);
                item1.setVisible(false);
                item2.setVisible(true);
                item3.setVisible(false);
                item4.setVisible(false);
                break;
            case 2:
                mToolbar.setTitle(R.string.major);
                setToolbar();
                setDrawerToggle();
                //mNavigationView.getMenu().getItem(0).setChecked(false);
                item1.setVisible(false);
                item2.setVisible(false);
                item3.setVisible(true);
                item4.setVisible(false);
                break;
            case 3:
                mToolbar.setTitle(R.string.more);
                setToolbar();
                setDrawerToggle();
                //mNavigationView.getMenu().getItem(0).setChecked(false);
                item1.setVisible(false);
                item2.setVisible(false);
                item3.setVisible(false);
                item4.setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Toast.makeText(DrawerLayoutActivity.this, "item1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item2:
                Toast.makeText(DrawerLayoutActivity.this, "item2", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(DrawerLayoutActivity.this, SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_item3:
                Toast.makeText(DrawerLayoutActivity.this, "item3", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(DrawerLayoutActivity.this, SearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_item4:
                Toast.makeText(DrawerLayoutActivity.this, "item4", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface MyTouchListener{
        public void onTouchEvent(MotionEvent event);
    }

   //提供Fragment通过getActivity()来注册触摸事件
    public void registerMyTouchListener(MyTouchListener listener){
        mMyTouchListeners.add(listener);
    }

    //提供Fragment通过getActivity()来取消注册触摸事件
    public void unRegisterMyTouchListener(MyTouchListener listener){
        mMyTouchListeners.remove(listener);
    }

    //分发触摸事件给所有注册了MyTouchListener的接口


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for(MyTouchListener listener: mMyTouchListeners){
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
