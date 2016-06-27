package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.WrongQuestionFragment;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 错误列表
 * Created by tmnt on 2016/6/12.
 */
public class WrongItemActivity extends AppCompatActivity {
    private QuestionDAO mDAO;
    private Questions mQuestions;
    private static final String TAG = "WrongItemActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(WrongItemActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);

        setContentView(R.layout.wrong_question_view_lay);


        mDAO = new QuestionDAO(getApplicationContext());
        Intent intent = getIntent();
        String question = intent.getStringExtra(WrongListActivity.QUESTION);

        mQuestions = mDAO.queryWrongByQuestion(question);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.wrong_view_contain, WrongQuestionFragment.newInstance(mQuestions, mQuestions.getMexam_type()));
        transaction.commit();
    }
}
