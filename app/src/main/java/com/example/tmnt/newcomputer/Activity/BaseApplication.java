package com.example.tmnt.newcomputer.Activity;

import android.app.Application;

import cn.bmob.v3.Bmob;

/** 全局Application
 * Created by tmnt on 2017/3/6.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "5b5167d530b5db1c3696b59f02b904bb");
    }
}
