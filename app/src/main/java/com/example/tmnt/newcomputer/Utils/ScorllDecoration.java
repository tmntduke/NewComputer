package com.example.tmnt.newcomputer.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tmnt on 2016/3/23.
 */
public class ScorllDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public ScorllDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view)!=0){
            outRect.top=space;
        }
    }
}
