package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.UserMessageFragment;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ImageUtils;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 显示头像
 * Created by tmnt on 2016/6/8.
 */
public class ShowUIconActivity extends AppCompatActivity {

    @Bind(R.id.show_user_Icon)
    ImageView mShowUserIcon;

    private QuestionDAO mDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(ShowUIconActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(android.R.color.transparent);

        mDAO = QuestionDAO.getInstance(getApplicationContext());

        setContentView(R.layout.show_user_icon);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String p = intent.getStringExtra(UserMessageFragment.PATH);
        if (mDAO.queryUserIcon(mDAO.queryLoginUsername())) {
            mShowUserIcon.setImageBitmap(ImageUtils.readBitMap(getApplicationContext()
                    , mDAO.queryUserIconPath(mDAO.queryLoginUsername())));
        } else {
            mShowUserIcon.setImageResource(R.drawable.image);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDAO.closeConn();
    }
}
