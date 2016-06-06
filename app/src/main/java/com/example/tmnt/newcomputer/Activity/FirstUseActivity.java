package com.example.tmnt.newcomputer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tmnt.newcomputer.AppInfo.FristSlideAppinfo;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.MainActivity;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/5/7.
 */
public class FirstUseActivity extends AppCompatActivity {
    @Bind(R.id.first_use_image)
    ImageView mFirstViewPager;
    @Bind(R.id.first_login)
    Button mFirstLogin;
    @Bind(R.id.first_register)
    Button mFirstRegister;
    @Bind(R.id.firstShowLay)
    LinearLayout mFirstShowLay;
    private QuestionDAO mDAO;
    Display mDisplay;

    private boolean isExit;
    private static final String TAG = "FirstUseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(FirstUseActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        SharedPreferences get = getSharedPreferences("slideApp", MODE_PRIVATE);
        SharedPreferences exit = getSharedPreferences("exit", MODE_PRIVATE);
        mDisplay = wm.getDefaultDisplay();
        mDAO = new QuestionDAO(getApplicationContext());
        if (exit != null) {
            isExit = exit.getString("exitValue", "").equals("exit");
        }

        if (!mDAO.isFirstUser() || !mDAO.isLogin()) {
            if (get.getString("slide", "").equals("slide") || isExit) {
                setContentView(R.layout.frist_show_lay);

                ButterKnife.bind(this);

                mFirstShowLay.setBackgroundResource(R.drawable.first_use_back);

                mFirstLogin.setOnClickListener((v) -> {
                    Intent turnLogin = new Intent(FirstUseActivity.this, LoginActivity.class);
                    mDAO.updateFirst(true);
                    startActivity(turnLogin);

                });

                mFirstRegister.setOnClickListener((v) -> {
                    Intent intent = new Intent(FirstUseActivity.this, RegisterActivity.class);
                    mDAO.updateFirst(true);
                    startActivity(intent);
                    finish();
                });

            } else {

                Intent intent = new Intent(FirstUseActivity.this, FristSlideAppinfo.class);
                startActivity(intent);
            }


        } else if (mDAO.isFirstUser() && mDAO.isLogin()) {
            Intent intent = new Intent(FirstUseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Bitmap getBitmapFromAsset(Context context, int res) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeResource(getResources(), res);
        return bitmap;
    }

}
