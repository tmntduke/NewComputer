package com.example.tmnt.newcomputer.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tmnt.newcomputer.BMOB.BmobUtils;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.InterFace.IFristLoad;
import com.example.tmnt.newcomputer.InterFace.IMPL.FristLoadIMPL;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by tmnt on 2016/7/22.
 */
public class UpdateDataService extends IntentService {


    private static final String TAG = "UpdateDataService";

    public UpdateDataService() {
        super("com.example.tmnt.newcomputer.Service.UpdateDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bmob.initialize(this, "key");


        if (Utils.isWifiConnected(getApplicationContext())) {

            BmobUtils.getyAnotherAnswer(getApplicationContext(), true, 0, "isLoad");

            BmobUtils.setDataResult(new BmobUtils.DataResult() {
                @Override
                public void getQuestionData(List<AnotherAnswer> l) {
                    new Thread(new UpdateThread(l, getApplicationContext())).start();
                    Log.i(TAG, "getQuestionData: " + l.size());
                }
            });


        }
    }
}
