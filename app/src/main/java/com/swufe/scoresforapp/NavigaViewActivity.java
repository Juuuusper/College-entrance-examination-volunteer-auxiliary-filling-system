package com.swufe.scoresforapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;

public class NavigaViewActivity extends BaseActivity {

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_naviga_view;
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.home);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black);
        return true;
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_favorite:
                    Toast.makeText(NavigaViewActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_about:
                    Toast.makeText(NavigaViewActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_exit:
                    Toast.makeText(NavigaViewActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_home:
                    Toast.makeText(NavigaViewActivity.this, "首页", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            //关闭侧边栏
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

}
