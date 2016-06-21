package com.example.tmnt.newcomputer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.tmnt.newcomputer.Adapter.HomeAdapter;
import com.example.tmnt.newcomputer.DView.DragBubbleView;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.UIModel.UIQuestionData;

import java.util.List;

/**
 * Created by tmnt on 2016/6/6.
 */
public class HomeListViewHolder extends RecyclerView.ViewHolder {

    private ConvenientBanner mConvenientBanner;
    private ImageView mImageView;
    private TextView title, subTitle;
    public int type;
    private DragBubbleView dbv;

    public HomeListViewHolder(View itemView, ImageView imageView, TextView title, TextView subTitle, int type, DragBubbleView dbv) {
        super(itemView);
        mImageView = imageView;
        this.title = title;
        this.subTitle = subTitle;
        this.type = type;
        this.dbv = dbv;
    }

    public HomeListViewHolder(View itemView, ConvenientBanner mConvenientBanner, int type) {
        super(itemView);
        this.mConvenientBanner = mConvenientBanner;
        this.type = type;
    }


    public static HomeListViewHolder getInstance(View view, int type) {
        if (type == HomeAdapter.IS_HEADER) {
            ConvenientBanner convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
            return new HomeListViewHolder(view, convenientBanner, type);
        } else {
            ImageView icon = (ImageView) view.findViewById(R.id.question_sort_icon);
            TextView title = (TextView) view.findViewById(R.id.question_sort_title);
            TextView subtitle = (TextView) view.findViewById(R.id.question_sort_subtitle);
            DragBubbleView dragBubbleView = (DragBubbleView) view.findViewById(R.id.dbv);
            return new HomeListViewHolder(view, icon, title, subtitle, type, dragBubbleView);
        }
    }


    public void getItemDate(UIQuestionData data) {
        mImageView.setImageResource(data.getResIdIcon());
        title.setText(data.getTitleData());
        subTitle.setText(data.getSubtitleData());
    }

    public void showConvenientBanner(List<Integer> list) {
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new MainViewHolder();
            }
        }, list)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    public void showDrag(int position, boolean isLoad) {
        if (position == 1 && isLoad) {
            dbv.setVisibility(View.VISIBLE);
        }
    }


}
