package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.UIModel.UIQuestionData;
import com.example.tmnt.newcomputer.ViewHolder.HomeListViewHolder;

import java.util.List;

/**
 * Home适配器
 * Created by tmnt on 2016/6/6.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UIQuestionData> mDatas;
    private List<Integer> mList;
    private Context mContext;
    private CardView mCardView;

    public static final int IS_HEADER = 2;
    public static final int IS_NORMAL = 1;
    private int t;

    private static final String TAG = "HomeAdapter";

    public HomeAdapter(List<UIQuestionData> mDatas, List<Integer> l, Context context) {
        this.mDatas = mDatas;
        mContext = context;
        mList = l;

    }

    public interface OnItemCardClickListener {
        void longClickEvent(View view, int position);

        void itemClickEvent(View view, int position);

    }

    private OnItemCardClickListener mOnItemCardClickListener;

    public void setOnItemCardClickListener(OnItemCardClickListener mOnItemCardClickListener) {
        this.mOnItemCardClickListener = mOnItemCardClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hoem_header_fragment, parent, false);
            return HomeListViewHolder.getInstance(view, viewType);
        } else if (viewType == IS_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.homt_list_fragment, parent, false);
            mCardView = (CardView) view.findViewById(R.id.home_Item);
            //Log.i(TAG, "onCreateViewHolder: list");
            return HomeListViewHolder.getInstance(view, viewType);
        } else {
            return null;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HomeListViewHolder homeListViewHolder = (HomeListViewHolder) holder;

        if (position == 0 && homeListViewHolder.type == IS_HEADER) {
            homeListViewHolder.showConvenientBanner(mList);

        } else {

            homeListViewHolder.getItemDate(mDatas.get(position - 1));


            if (mOnItemCardClickListener != null) {
                mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemCardClickListener.itemClickEvent(v, position);
                    }
                });

                mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemCardClickListener.longClickEvent(v, position);
                        return true;
                    }
                });
            }

        }
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



