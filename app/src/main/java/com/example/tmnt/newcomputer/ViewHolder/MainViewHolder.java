package com.example.tmnt.newcomputer.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * 主页图片选择控件ViewHolder
 * Created by tmnt on 2016/6/5.
 */
public class MainViewHolder implements Holder<Integer> {
    private ImageView mImageView;

    public interface OnClickImageListener {
        void itemClick(View v, int position);
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
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setImageResource(data);
        p = position;
        if (mOnClickImageListener != null) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickImageListener.itemClick(v, p);
                }
            });
        }
    }

    public static void setOnClickImageListener(OnClickImageListener onClickImageListener) {
        mOnClickImageListener = onClickImageListener;
    }
}
