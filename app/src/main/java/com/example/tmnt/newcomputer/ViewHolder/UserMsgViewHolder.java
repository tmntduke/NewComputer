package com.example.tmnt.newcomputer.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DView.CircleImageView;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ImageUtils;

/**
 * Created by tmnt on 2016/6/9.
 */
public class UserMsgViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView mCircleImageView;
    private TextView userMsgname;
    private TextView userSub;

    private ImageView userMsgItemIcon;
    private TextView userMsgTitle;

    public int mType;


    public UserMsgViewHolder(View itemView, ImageView imageView, TextView textView, int type) {
        super(itemView);
        userMsgItemIcon = imageView;
        userMsgTitle = textView;
        mType = type;
    }

    public UserMsgViewHolder(View itemView, CircleImageView circleImageView, TextView name, TextView sub, int type) {
        super(itemView);
        mCircleImageView = circleImageView;
        userMsgname = name;
        userSub = sub;
        mType = type;
    }

    public static UserMsgViewHolder getInstance(View view, int type) {
        if (type == 0) {
            CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.user_msg_icon);
            TextView name = (TextView) view.findViewById(R.id.user_msg_name);
            TextView sub = (TextView) view.findViewById(R.id.user_msg_header_sub);
            return new UserMsgViewHolder(view, circleImageView, name, sub, type);
        } else {
            ImageView imageView = (ImageView) view.findViewById(R.id.user_msg_item_icon);
            TextView textView = (TextView) view.findViewById(R.id.user_msg_item_name);
            return new UserMsgViewHolder(view, imageView, textView, type);
        }
    }

    public void setUserHeader(Context context, String path, String name, String sub) {
        if (path == null) {
            mCircleImageView.setImageResource(R.drawable.image);
        } else {
            mCircleImageView.setImageBitmap(ImageUtils.readBitMap(context, path));
        }

        userMsgname.setText(name);
        userSub.setText(sub);
    }

    public void setUserItem(int resId, String title) {
        userMsgItemIcon.setImageResource(resId);
        userMsgTitle.setText(title);
    }


}
