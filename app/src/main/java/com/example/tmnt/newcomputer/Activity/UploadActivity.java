package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.tmnt.newcomputer.Fragment.SingleUploadFragment;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.bmob.v3.Bmob;

/**
 * Created by tmnt on 2016/6/17.
 */
public class UploadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(UploadActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);

        Bmob.initialize(this, "5b5167d530b5db1c3696b59f02b904bb");

        setContentView(R.layout.upload_contain);
        Intent intent = getIntent();
        int flag = intent.getIntExtra(AdminActivity.ADD, 0);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        if (flag == 001) {
            transaction.replace(R.id.upload_contain, SingleUploadFragment.getInstance(flag)).commit();
        } else if (flag == 002) {
            transaction.replace(R.id.upload_contain, SingleUploadFragment.getInstance(flag)).commit();
        } else {
            transaction.replace(R.id.upload_contain, SingleUploadFragment.getInstance(000)).commit();
        }

    }
}
