package com.example.tmnt.newcomputer.AppInfo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.tmnt.newcomputer.Activity.FirstUseActivity;
import com.example.tmnt.newcomputer.Fragment.FirstSlideFragment;
import com.example.tmnt.newcomputer.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by tmnt on 2016/5/16.
 */
public class FristSlideAppinfo extends AppIntro {
    public static String SLIDE = "slide";

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance("slide1", "this is test ont ", R.drawable.register, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("slide2", "this is test two", R.drawable.login, getResources().getColor(R.color.holo_green_light)));
        addSlide(FirstSlideFragment.newInstance(R.layout.slide_lay));

        setFadeAnimation();

        showSkipButton(true);
        setProgressButtonEnabled(true);

        showStatusBar(false);
        //setBarColor(getResources().getColor(R.color.colorLogin));
        //setSupportActionBar(null);

        setBarColor(getResources().getColor(R.color.colorPrimary));
        setVibrate(true);
        setVibrateIntensity(30);

        askForPermissions(new String[]{Manifest.permission.CAMERA}, 2);
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, FirstUseActivity.class);
        SharedPreferences send = getSharedPreferences("slideApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = send.edit();
        editor.putString("slide", SLIDE);
        editor.commit();
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }
}
