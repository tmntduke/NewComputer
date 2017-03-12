package com.example.tmnt.newcomputer.Widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.tmnt.newcomputer.Model.NewsInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by tmnt on 2017/3/7.
 */
public class LocalImageHolderView implements Holder<NewsInfo.NewslistBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, NewsInfo.NewslistBean data) {
        Picasso.with(context).load(data.getPicUrl()).into(imageView);
    }
}
