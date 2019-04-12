package com.swufe.scoresforapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

public class CompareActivity extends AppCompatActivity {

    private PanelListLayout pl_root;
    private ListView lv_content;

    private AbstractPanelListAdapter mAdapter;

    private List<List<String>> contentList = new ArrayList<>(); //内容

    private List<Integer> itemWidthList = new ArrayList<>(); //宽度

    private List<String> rowDataList = new ArrayList<>();  //行
    private List<String> columnDataList = new ArrayList<>();  //行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的对比");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initView();

        initRowDataList();
        initColumnDataList();
        initContentDataList();
        initItemWidthList();

        initAdapter();
        pl_root.setAdapter(mAdapter);

        // 注意：
        // 如果你决定自己实现自己的Column，而不是使用默认的1，2，3。。。
        // 请注意更新contentList时手动更新columnList
    }

    private void initAdapter(){
        mAdapter = new AbstractPanelListAdapter(this, pl_root, lv_content) {
            @Override
            protected BaseAdapter getContentAdapter() {
                return null;
            }
        };
        mAdapter.setInitPosition(10); //设置content的初始position
        mAdapter.setTitle("对比");// 表头标题
        mAdapter.setTitleBackgroundResource(R.color.white); //设置表头标题背景
        mAdapter.setTitleWidth(100);
        mAdapter.setTitleHeight(30);
        mAdapter.setRowColor("#FFFFFF");
        mAdapter.setColumnColor("#FFFFFF");
        mAdapter.setSwipeRefreshEnabled(false);  //是否开启下拉刷新
        mAdapter.setRowDataList(rowDataList);// 横向表头内容
        mAdapter.setColumnDataList(columnDataList);  //纵向表头内容

        mAdapter.setOnRefreshListener(new CompareActivity.CustomRefreshListener());// 设置监听
        mAdapter.setContentDataList(contentList);// 内容
        mAdapter.setItemWidthList(itemWidthList);// 每一个小格子的宽度
        mAdapter.setItemHeight(40);// 小格子高度
    }

    private void initView() {

        pl_root = findViewById(R.id.pl_root);
        lv_content = findViewById(R.id.lv_content);

        //设置listView为多选模式，长按自动触发
        lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_content.setMultiChoiceModeListener(new CompareActivity.MultiChoiceModeCallback());

        //listView的点击监听
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CompareActivity.this, "你选中的position为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            // you can do sth here, for example: make a toast:
            Toast.makeText(CompareActivity.this, "custom SwipeRefresh listener", Toast.LENGTH_SHORT).show();
            // don`t forget to call this
            mAdapter.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    /**
     * 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private void initRowDataList() {
        rowDataList.add("学校1");
        rowDataList.add("学校2");
        rowDataList.add("学校3");
        rowDataList.add("学校4");
    }

    /**
     * 生成一份纵向表头的内容
     *
     * @return List<String>
     */
    private void initColumnDataList() {
        columnDataList.add("学校名称");
        columnDataList.add("所在地");
        columnDataList.add("学校类别");
        columnDataList.add("重点学科数");
        columnDataList.add("所属部门");
        columnDataList.add("硕士点数");
        columnDataList.add("博士点数");
        columnDataList.add("是否985");
        columnDataList.add("是否211");
    }


    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i <= 9; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "行第一个");
            data.add("第" + i + "行第二个");
            data.add("第" + i + "行第三个");
            data.add("第" + i + "行第四个");
            contentList.add(data);
        }
    }

    /**
     * 初始化 content 部分每一个 item 的每个数据的宽度
     */
    private void initItemWidthList() {
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
    }
    /**
     * 更新content数据源
     */
    private void changeContentDataList() {
        contentList.clear();
        for (int i = 1; i < 9; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "第一个");
            data.add("第" + i + "第二个");
            data.add("第" + i + "第三个");
            data.add("第" + i + "第四个");
            contentList.add(data);
        }
    }

    /**
     * 删除一个数据
     */
    private void removeData() {
        contentList.remove(10);
    }

    /**
     * 多选模式的监听器
     */
    private class MultiChoiceModeCallback implements ListView.MultiChoiceModeListener {

        private View actionBarView;
        private TextView tv_selectedCount;

        /**
         * 进入ActionMode时调用
         * 可设置一些菜单
         *
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // menu
            getMenuInflater().inflate(R.menu.menu_multichoice, menu);
            // actionBar
            if (actionBarView == null) {
                actionBarView = LayoutInflater.from(CompareActivity.this).inflate(R.layout.actionbar_listviewmultichoice, null);
                tv_selectedCount = (TextView) actionBarView.findViewById(R.id.id_tv_selectedCount);
            }
            mode.setCustomView(actionBarView);
            return true;
        }

        /**
         * 和onCreateActionMode差不多的时机调用，不写逻辑
         *
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * 当ActionMode的菜单项被点击时
         *
         * @param mode
         * @param item
         * @return
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.id_menu_selectAll:
                    for (int i = 0; i < lv_content.getAdapter().getCount(); i++) {
                        lv_content.setItemChecked(i, true);
                    }
                    tv_selectedCount.setText(String.valueOf(lv_content.getAdapter().getCount()));
                    break;
                case R.id.id_menu_draw:
                    //draw
                    SparseBooleanArray booleanArray = lv_content.getCheckedItemPositions();
                    Log.d("ybz", booleanArray.toString());

                    List<Integer> checkedItemPositionList = new ArrayList<>();
                    for (int i = 0; i < contentList.size(); i++) {
                        if (lv_content.isItemChecked(i)) {
                            checkedItemPositionList.add(i);
                            Log.d("ybz", "被选中的item： " + i);
                        }
                    }

                    StringBuilder checkedItemString = new StringBuilder();
                    for (int i = 0; i < checkedItemPositionList.size(); i++) {
                        checkedItemString.append(checkedItemPositionList.get(i) + ",");
                    }

                    Toast.makeText(CompareActivity.this, "你选中的position有：" + checkedItemString, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }

        /**
         * 退出ActionMode时调用
         *
         * @param mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            lv_content.clearChoices();
        }

        /**
         * 当item的选中状态发生改变时调用
         *
         * @param mode
         * @param position
         * @param id
         * @param checked
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int selectedCount = lv_content.getCheckedItemCount();
            tv_selectedCount.setText(String.valueOf(selectedCount));
            ((ArrayAdapter) lv_content.getAdapter()).notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
