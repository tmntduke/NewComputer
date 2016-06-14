package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Window;

import com.example.tmnt.newcomputer.Fragment.TitleSlideFragment;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by tmnt on 2016/6/7.
 */
public class TitleSlideActivity extends AppCompatActivity {
    private TitleSlideFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(TitleSlideActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        if (intent.getIntExtra("position", 0) == 0) {
            tintManager.setStatusBarTintResource(R.color.android);
        } else if (intent.getIntExtra("position", 0) == 1) {
            tintManager.setStatusBarTintResource(R.color.java);
        } else if (intent.getIntExtra("position", 0) == 2) {
            tintManager.setStatusBarTintResource(R.color.python);
        }



        setContentView(R.layout.title_contaner_lay);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterAnmition();
        }


        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        if (intent.getIntExtra("position", 0) == 0) {
            fragment = TitleSlideFragment.newInstance(0);
        } else if (intent.getIntExtra("position", 0) == 1) {
            fragment = TitleSlideFragment.newInstance(1);
        } else if (intent.getIntExtra("position", 0) == 2) {
            fragment = TitleSlideFragment.newInstance(2);
        }

        transaction.replace(R.id.title_id, fragment).commit();


    }

    public void setEnterAnmition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Explode explode = new Explode();
            explode.setDuration(1500);
            getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(1500);
            getWindow().setReturnTransition(fade);
        }

    }
}
