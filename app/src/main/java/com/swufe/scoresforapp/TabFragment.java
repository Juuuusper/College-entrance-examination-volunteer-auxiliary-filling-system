package com.swufe.scoresforapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.swufe.scoresforapp.adapter.RecyclerAdapter;
import com.swufe.scoresforapp.bean.SchoolBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/7/7.
 */

public class TabFragment extends Fragment {

    private RecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<SchoolBean> mSchoolBeans;

    private RecyclerView recyclerView;
    private View rootView;


    public static Fragment newInstance(){

        TabFragment fragment = new TabFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        initDatas();
        mAdapter = new RecyclerAdapter(this.getContext(), mSchoolBeans);
        mAdapter.setOnDelListener(new RecyclerAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                if (pos >= 0 && pos < mSchoolBeans.size()) {
                    Toast.makeText(getActivity(), "删除:" + pos, Toast.LENGTH_SHORT).show();
                    mSchoolBeans.remove(pos);
                    mAdapter.notifyItemRemoved(pos);//推荐用这个
                    //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                    //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                    //mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTop(int pos) {
                if (pos > 0 && pos < mSchoolBeans.size()) {
                    SchoolBean swipeBean = mSchoolBeans.get(pos);
                    mSchoolBeans.remove(swipeBean);
                    mAdapter.notifyItemInserted(0);
                    mSchoolBeans.add(0, swipeBean);
                    mAdapter.notifyItemRemoved(pos + 1);
                    if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                        recyclerView.scrollToPosition(0);
                    }
                    //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                }
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int tag, View view, int position) {
                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ScrollingSchoolActivity.class);
                intent.setClass(getContext(), ScrollingSchoolActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(int tag, View view, int position) {
                Toast.makeText(getContext(), "长按", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //流式
        //recyclerView.setLayoutManager(mLayoutManager = new GridLayoutManager(this.getContext(), 2));

        // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        //recyclerView.setAdapter(new RecyclerAdapter(this.getContext(), mSchoolBeans));
        return rootView;
    }

    private void initDatas() {
        mSchoolBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mSchoolBeans.add(new SchoolBean("" + i));
        }
    }


}
