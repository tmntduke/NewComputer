package com.example.tmnt.newcomputer.BMOB;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.a.a.V;
import com.example.tmnt.newcomputer.InterFace.IFristLoad;
import com.example.tmnt.newcomputer.InterFace.IMPL.FristLoadIMPL;
import com.example.tmnt.newcomputer.InterFace.IMPL.MaxIdIMPL;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tmnt on 2016/6/13.
 */
public class BmobUtils {

    private static final String TAG = "BmobUtils";
    int max;

    public static void saveDataToBmob(Object o, Context context, View view) {
        if (o instanceof BmobObject) {
            ((BmobObject) o).save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    Snackbar.make(view, "you already add a question", Snackbar.LENGTH_LONG).show();

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

    public static void getyAnotherAnswer(Context context, Object kind, int flag, String filed) {
        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        ArrayList<AnotherAnswer> question = new ArrayList<>();
        mAnotherQuestionBmobQuery.addWhereEqualTo(filed, kind);
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

    public static void getyBmobAnswer(Context context, int count) {
        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        ArrayList<AnotherAnswer> question = new ArrayList<>();
        mAnotherQuestionBmobQuery.addWhereGreaterThan("id", count);
        mAnotherQuestionBmobQuery.findObjects(context, new FindListener<AnotherAnswer>() {

            @Override
            public void onSuccess(List<AnotherAnswer> list) {
                if (FristLoadIMPL.mIFristLoad != null) {
                    FristLoadIMPL.mIFristLoad.fristLoad(list);
                }

            }


            @Override
            public void onError(int i, String s) {
                Utils.showToast(context, "wrong");
            }
        });


    }

    public static void updateLoad(Context context, String id) {
        AnotherAnswer answer = new AnotherAnswer();
        answer.setLoad(true);
        answer.update(context, id, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }


    public static void maxIdToBmob(Context context) {

        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        mAnotherQuestionBmobQuery.max(new String[]{"id"});
        mAnotherQuestionBmobQuery.findStatistics(context, AnotherAnswer.class, new FindStatisticsListener() {
            @Override
            public void onSuccess(Object o) {
                JSONArray array = (JSONArray) o;
                try {
                    JSONObject object = array.getJSONObject(0);
                    if (MaxIdIMPL.sIMaxId != null) {
                        MaxIdIMPL.sIMaxId.maxId(object.getInt("_maxId"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

}

