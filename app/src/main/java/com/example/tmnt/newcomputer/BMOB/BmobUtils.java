package com.example.tmnt.newcomputer.BMOB;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.a.a.V;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tmnt on 2016/6/13.
 */
public class BmobUtils {

    private static final String TAG = "BmobUtils";

    public static void saveDataToBmob(Object o, Context context, View view) {
        if (o instanceof BmobObject) {
            ((BmobObject) o).save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    Snackbar.make(view, "you already add a person", Snackbar.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(int i, String s) {
                    Snackbar snackbar = Snackbar.make(view, "add fail", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                }
            });

        }
    }

    public interface DataResult {
        void getQuestionData(List<AnotherAnswer> l);
    }

    private static DataResult dataRe;

    public static void setDataResult(DataResult data) {
        dataRe = data;
    }

    public static void getyAnotherAnswer(Context context, String kind, int flag) {
        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        ArrayList<AnotherAnswer> question = new ArrayList<>();
        mAnotherQuestionBmobQuery.addWhereEqualTo("kind", kind);
        mAnotherQuestionBmobQuery.findObjects(context, new FindListener<AnotherAnswer>() {

            @Override
            public void onSuccess(List<AnotherAnswer> list) {
                if (dataRe != null) {
                    dataRe.getQuestionData(list);
                }

            }


            @Override
            public void onError(int i, String s) {
                Utils.showToast(context, "wrong");
            }
        });


    }


}

