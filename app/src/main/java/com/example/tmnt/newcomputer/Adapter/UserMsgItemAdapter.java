package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tmnt.newcomputer.Widget.CircleImageView;
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
    private final List<Integer> icons;
    private final List<String> title;
    private final Context cotext;
    private final String path;
    private final String name;
    private final String sub;
    private final boolean isIcon;

    private LinearLayout cardView;
    private CircleImageView iCircleImageView;

    public OnclickUserMsgListener mOnclickUserMsgListener;

    private static final String TAG = "UserMsgItemAdapter";


    /**
     * 构建者模式 创建对象
     */
    public static class Bulider {
        private List<Integer> icons;
        private List<String> title;
        private Context cotext;
        private String path;
        private String name;
        private String sub;
        private boolean isIcon;

        public Bulider(List<Integer> icons,
                       List<String> title,
                       Context bContext) {
            this.cotext = bContext;
            this.title = title;
            this.icons = icons;

        }

        public Bulider setPath(String path) {
            this.path = path;
            return this;
        }

        public Bulider setName(String name) {
            this.name = name;
            return this;
        }

        public Bulider setSub(String sub) {
            this.sub = sub;
            return this;
        }

        public Bulider isIcons(boolean isIcon) {
            this.isIcon = isIcon;
            return this;
        }

        public UserMsgItemAdapter bulid() {
            return new UserMsgItemAdapter(this);
        }

    }

    //将信息传入
    private UserMsgItemAdapter(Bulider bulider) {
        this.isIcon = bulider.isIcon;
        this.cotext = bulider.cotext;
        this.icons = bulider.icons;
        this.title = bulider.title;
        this.path = bulider.path;
        this.name = bulider.name;
        this.sub = bulider.sub;
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
            userMsgViewHolder.setUserHeader(cotext, path, name, sub, isIcon);
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
