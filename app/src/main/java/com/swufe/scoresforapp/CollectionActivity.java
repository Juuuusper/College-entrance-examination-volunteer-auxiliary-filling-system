package com.swufe.scoresforapp;

import android.app.ActionBar;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity {

    @BindView(R.id.collect_layout)
    DrawerLayout mCollectLayout;
    @BindView(R.id.toolbar3)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;


    private TabLayout mTabLayout = null;
    private MyViewPager mViewPager;
    private Fragment[] mFragments = new Fragment[3];

    private String[] mTabTitles = new String[3];
    private int pos;

    //private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collection);

        ButterKnife.bind(this);

        mToolbar.setTitle("我的收藏");
        //设置Toolbar
        setToolbar();


        //recyclerView = findViewById(R.id.recycler);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.tab_viewPager);
        mViewPager.setScrollable(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        initView();

        initSearchListener();


    }

    private void initSearchListener(){

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.collect_layout), "Query:" +query, Snackbar.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        mSearchView.setVoiceSearch(false);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(matches != null && matches.size() > 0){
                String searchWrd = matches.get(0);
                if(!TextUtils.isEmpty(searchWrd)){
                    mSearchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(mSearchView.isSearchOpen()){
            mSearchView.closeSearch();
        }else{
            super.onBackPressed();
        }
    }

    private void initView(){

        mTabTitles[0] = getResources().getString(R.string.school);
        mTabTitles[1] = getResources().getString(R.string.major);
        mTabTitles[2] = getResources().getString(R.string.compare);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragments[0] = TabFragment.newInstance();
        mFragments[1] = TabFragment.newInstance();
        mFragments[2] = TabFragment.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            pos = position;
            return mFragments[position];
        }

        /*@Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment)super.instantiateItem(container, position);
            this.
            return ;
        }*/

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        // 显示Home的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* private void setDrawerToggle() {
        mToggle=new ActionBarDrawerToggle(this,mCollectLayout,mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mCollectLayout.addDrawerListener(mToggle);
        //同步DrawerLayout的状态
        mToggle.syncState();
    }
*/
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_collect, menu);

       MenuItem search = menu.findItem(R.id.item_search);
       mSearchView.setMenuItem(search);

       return true;
   }
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item1 = menu.findItem(R.id.item_search);
        MenuItem item2 = menu.findItem(R.id.item_manage);
        switch (mViewPager.getCurrentItem()) {
            case 0:
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
        }
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                Toast.makeText(CollectionActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
           /* case R.id.item_manage:
                Toast.makeText(CollectionActivity.this, "管理", Toast.LENGTH_SHORT).show();
                break;*/
            /*case android.R.id.home:
                this.finish();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

}
