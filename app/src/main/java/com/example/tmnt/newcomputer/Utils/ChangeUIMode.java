package com.example.tmnt.newcomputer.Utils;

import android.app.Activity;
import android.content.Intent;

import com.example.tmnt.newcomputer.R;

/**
 * Created by tmnt on 2016/6/9.
 */
public class ChangeUIMode {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity
     * of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        activity.setTheme(R.style.AppTheme1);
    }
}
