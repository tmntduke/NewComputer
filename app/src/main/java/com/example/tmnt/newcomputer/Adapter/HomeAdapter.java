package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.tmnt.newcomputer.Model.NewsInfo;
import com.example.tmnt.newcomputer.Widget.DragBubbleView;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.UIModel.UIQuestionData;
import com.example.tmnt.newcomputer.ViewHolder.HomeListViewHolder;

import java.util.List;

/**
 * Home界面适配器
 * Created by tmnt on 2016/6/6.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UIQuestionData> mDatas;
    private List<NewsInfo.NewslistBean> mList;
    private Context mContext;
    private CardView mCardView;
    private DragBubbleView dbv;
    private ConvenientBanner mConvenientBanner;

    public static final int IS_HEADER = 2;
    public static final int IS_NORMAL = 1;
    private int t;
    private boolean isLoad;

    private static final String TAG = "HomeAdapter";

    public HomeAdapter(List<UIQuestionData> mDatas, List<NewsInfo.NewslistBean> l, Context context) {
        this.mDatas = mDatas;
        mContext = context;
        mList = l;

    }

    //item单机事件
    public interface OnItemCardClickListener {
        void longClickEvent(View view, int position);

        void itemClickEvent(View view, int position);

    }

    private OnItemCardClickListener mOnItemCardClickListener;

    //设置单击事件
    public void setOnItemCardClickListener(OnItemCardClickListener mOnItemCardClickListener) {
        this.mOnItemCardClickListener = mOnItemCardClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //当type为header
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hoem_header_fragment, parent, false);
            mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
            return HomeListViewHolder.getInstance(view, viewType);
        } else if (viewType == IS_NORMAL) {
            //不为header时
            View view = LayoutInflater.from(mContext).inflate(R.layout.homt_list_fragment, parent, false);
            mCardView = (CardView) view.findViewById(R.id.home_Item);
            dbv = (DragBubbleView) view.findViewById(R.id.dbv);
            //Log.i(TAG, "onCreateViewHolder: list");
            return HomeListViewHolder.getInstance(view, viewType);
        } else {
            return null;
        }


    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //Log.i(TAG, "onViewAttachedToWindow: start");
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        mConvenientBanner.startTurning(4000);

    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HomeListViewHolder homeListViewHolder = (HomeListViewHolder) holder;

        //显示header
        if (position == 0 && homeListViewHolder.type == IS_HEADER) {
            homeListViewHolder.showConvenientBanner(mList);

        } else {

            //item添加数据
            homeListViewHolder.getItemDate(mDatas.get(position - 1));

            homeListViewHolder.showDrag(position, isLoad);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemCardClickListener != null) {
                        mOnItemCardClickListener.itemClickEvent(v, position);
                    }
                    dbv.setVisibility(View.GONE);
                }
            });


            mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemCardClickListener != null) {
                        mOnItemCardClickListener.longClickEvent(v, position);

                    }
                    return true;
                }
            });


        }
    }


    //显示拖拽红点
    public void showDragWhenLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else {
            return IS_NORMAL;
        }
    }
}



