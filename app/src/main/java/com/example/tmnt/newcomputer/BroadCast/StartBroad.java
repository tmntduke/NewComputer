package com.example.tmnt.newcomputer.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tmnt.newcomputer.Service.StartOpService;

/**
 * Created by tmnt on 2016/7/22.
 */
public class StartBroad extends BroadcastReceiver {

    private static final String TAG = "StartBroad";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, StartOpService.class);
        context.startService(intent1);
        Log.i(TAG, "onReceive: i send a broad to service");
    }
}
