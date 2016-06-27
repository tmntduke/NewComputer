package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tmnt.newcomputer.Activity.ShowUIconActivity;
import com.example.tmnt.newcomputer.DView.CircleImageView;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.ViewHolder.UserMsgViewHolder;

import java.util.List;

/**
 * 用户信息
 * Created by tmnt on 2016/6/9.
 */
public class UserMsgItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int IS_HEADER = 0;
    private static final int IS_NORMAL = 1;
    private List<Integer> icons;
    private List<String> title;
    private Context cotext;
    private String path;
    private String name;
    private String sub;

    private LinearLayout cardView;
    private CircleImageView iCircleImageView;

    public OnclickUserMsgListener mOnclickUserMsgListener;

    private static final String TAG = "UserMsgItemAdapter";

    //将信息传入
    public UserMsgItemAdapter(List<Integer> icons, List<String> title, Context cotext, String path, String name, String sub) {
        this.icons = icons;
        this.title = title;
        this.cotext = cotext;
        this.path = path;
        this.name = name;
        this.sub = sub;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            //header
            View view = LayoutInflater.from(cotext).inflate(R.layout.user_meg_list_header, parent, false);
            iCircleImageView = (CircleImageView) view.findViewById(R.id.user_msg_icon);
            return UserMsgViewHolder.getInstance(view, viewType);
        } else {
            //item
            View view = LayoutInflater.from(cotext).inflate(R.layout.user_msg_list_item, parent, false);
            cardView = (LinearLayout) view.findViewById(R.id.wrong_list_id);
            return UserMsgViewHolder.getInstance(view, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserMsgViewHolder userMsgViewHolder = (UserMsgViewHolder) holder;
        if (userMsgViewHolder.mType == IS_HEADER && position == 0) {
            userMsgViewHolder.setUserHeader(cotext, path, name, sub);
        } else {
            userMsgViewHolder.setUserItem(icons.get(position - 1), title.get(position - 1));
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnclickUserMsgListener != null) {
                        mOnclickUserMsgListener.onClickMsgItem(v, position);
                    }
                }
            });
        }
        iCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnclickUserMsgListener != null) {
                    mOnclickUserMsgListener.turnToShow(v);
                }
            }
        });

    }

    //进入详细界面接口
    public interface OnclickUserMsgListener {
        void onClickMsgItem(View v, int position);

        void turnToShow(View v);
    }


    public void setOnclickUserMsgListener(OnclickUserMsgListener mOnclickUserMsgListener) {
        this.mOnclickUserMsgListener = mOnclickUserMsgListener;
    }

    @Override
    public int getItemCount() {
        return icons.size() + 1;
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
