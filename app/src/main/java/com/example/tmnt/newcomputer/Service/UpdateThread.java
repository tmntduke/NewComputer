package com.example.tmnt.newcomputer.Service;

import android.content.Context;
import android.util.Log;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by tmnt on 2016/7/23.
 */
public class UpdateThread implements Runnable {

    private List<AnotherAnswer> mAnotherAnswers;
    private QuestionDAO mDAO;
    private Context mContext;
    private static final String TAG = "UpdateThread";

    public UpdateThread(List<AnotherAnswer> mAnotherAnswers, Context mContext) {
        this.mAnotherAnswers = mAnotherAnswers;
        this.mContext = mContext;
        mDAO = QuestionDAO.getInstance(mContext);
    }

    @Override
    public void run() {

        List<String> questions = mDAO.queryObjectId(199);
        List<String> netObject = new ArrayList<>();

        for (int i = 0; i < mAnotherAnswers.size(); i++) {
            netObject.add(mAnotherAnswers.get(i).getObjectId());
        }


        Log.i(TAG, "run: " + questions.size());
        questions.removeAll(netObject);
        netObject.removeAll(mDAO.queryObjectId(199));
        Log.i(TAG, "run: question  " + questions.size());
        Log.i(TAG, "run: net  " + netObject.size());

        for (int i = 0; i < netObject.size(); i++) {
        }

        BmobRealTimeData rtd = new BmobRealTimeData();
        if (rtd.isConnected()) {
            // 监听表更新
            rtd.subTableUpdate("AnotherAnswer");
            rtd.start(mContext, new ValueEventListener() {
                @Override
                public void onConnectCompleted() {
                    Log.d("bmob", "连接成功:" + rtd.isConnected());
                }

                @Override
                public void onDataChange(JSONObject jsonObject) {
                    Log.i(TAG, "onDataChange: " + jsonObject.toString());
                }
            });
        }


        if (mDAO.isOutDBConn()) {
            if (netObject.size() != 0 && netObject != null) {

                for (int j = 0; j < netObject.size(); j++) {

                    for (int i = 0; i < mAnotherAnswers.size(); i++) {

                        if (mAnotherAnswers.get(i).getObjectId().equals(netObject.get(j))) {
                            if (mAnotherAnswers.get(i).getKind().equals("fillBlank")) {
                                mDAO.addFillQuestion(mAnotherAnswers.get(i), mDAO.queryAll().size() + questions.size() + 2
                                        , 3, mAnotherAnswers.get(i).getQuestionId());
                            } else {
                                mDAO.addSelectQuestion(mAnotherAnswers.get(i), mDAO.queryAll().size() + questions.size() + 1
                                        , mAnotherAnswers.get(i).getQuestionId());
                            }
                        }
                    }
                }


            }

            if (questions.size() != 0 && questions != null) {
                //delete dateBase question
                for (int i = 0; i < questions.size(); i++) {
                    mDAO.deleteFromService(questions.get(i));
                }
            }

            mDAO.closeOutDB();
        }

    }
}
