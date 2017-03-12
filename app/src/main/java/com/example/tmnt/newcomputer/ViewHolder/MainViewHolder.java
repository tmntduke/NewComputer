package com.example.tmnt.newcomputer.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.tmnt.newcomputer.Model.NewsInfo;
import com.squareup.picasso.Picasso;

/**
 * 主页图片选择控件ViewHolder
 * Created by tmnt on 2016/6/5.
 */
public class MainViewHolder implements Holder<NewsInfo.NewslistBean> {
    private ImageView mImageView;

    public interface OnClickImageListener {
        void itemClick(View v, String url);
    }

    private static OnClickImageListener mOnClickImageListener;
    private int p;

    @Override
    public View createView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsInfo.NewslistBean data) {
        Picasso.with(context).load(data.getPicUrl()).into(mImageView);
        p = position;
        if (mOnClickImageListener != null) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickImageListener.itemClick(v, data.getUrl());
                }
            });
        }
    }

    public static void setOnClickImageListener(OnClickImageListener onClickImageListener) {
        mOnClickImageListener = onClickImageListener;
    }
}
