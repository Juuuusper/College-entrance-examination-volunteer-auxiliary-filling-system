package com.swufe.scoresforapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.swufe.scoresforapp.R;
import com.swufe.scoresforapp.bean.SchoolBean;

import java.util.List;

/**
 * Created by ASUS on 2018/7/8.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AuthorViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<SchoolBean> mSchools;
    private onItemClickListener mOnItemClickListener;

    public RecyclerAdapter(Context context, List<SchoolBean> mSchools) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mSchools = mSchools;
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new AuthorViewHolder(mInflater.inflate(R.layout.item_school, parent, false));
    }

    @Override
    public void onBindViewHolder(final AuthorViewHolder holder, int position) {
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuLayout)holder.itemView).setIos(false);//.setLeftSwipe(position % 2 == 0 ? true : false);

        holder.mNickNameView.setText(mSchools.get(position).name + (position % 2 == 0 ? "北京大学" : "清华大学"));


        //删除
        holder.mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                    //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                    //((CstSwipeDelMenu) holder.itemView).quickClose();
                    mOnSwipeListener.onDel(holder.getAdapterPosition());
                }
            }
        });



        //置顶：
        holder.mbtnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=mOnSwipeListener){
                    mOnSwipeListener.onTop(holder.getAdapterPosition());
                }

            }
        });

        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        (holder.mContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(-55, holder.itemView, pos);
                Log.d("TAG", "onClick() called with: v = [" + v + "]");
            }
        });
        //验证长按
        holder.mContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getLayoutPosition();
                Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                mOnItemClickListener.onItemLongClick(-55, holder.itemView, pos);
                Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                return false;
            }
        });
    }

    /**
     * tag：区分点击的是什么
     * position:位置
     */
    public static interface onItemClickListener{
        void onItemClick(int tag, View view, int position);
        void onItemLongClick(int tag, View view, int position);
    }

    /**
     * 声明给外界的方法
     */
    public void setOnItemClickListener(onItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return null != mSchools ? mSchools.size() : 0;
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

        void onTop(int pos);
    }

    private onSwipeListener mOnSwipeListener;

    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }


    class AuthorViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mContent;
        TextView mNickNameView;
        Button mbtnTop;
        Button mbtnDelete;

        public AuthorViewHolder(View itemView) {
            super(itemView);

            mContent = (LinearLayout) itemView.findViewById(R.id.school_content);
            mNickNameView = (TextView) itemView.findViewById(R.id.tv_schoolname);
            mbtnTop = (Button) itemView.findViewById(R.id.btnTop);
            mbtnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
