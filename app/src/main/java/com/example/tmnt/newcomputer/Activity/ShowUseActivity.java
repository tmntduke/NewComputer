package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Window;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.UserMessageFragment;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;

/**
 * 用户详细信息
 * Created by tmnt on 2016/6/9.
 */
public class ShowUseActivity extends AppCompatActivity {

    private QuestionDAO mDAO;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_lay);
        setEnterAnmition();

        Intent intent = getIntent();
        username=intent.getStringExtra("user");

        mDAO = new QuestionDAO(getApplicationContext());

        //加载fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.show_id, UserMessageFragment.newInstance(mDAO.queryWrongQuestion(), username, mDAO.queryUserIconPath(username)));
        transaction.commit();
    }

    public void setEnterAnmition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Fade fade = new Fade();
            fade.setDuration(1500);
            getWindow().setEnterTransition(fade);


            getWindow().setReturnTransition(fade);
        }

    }
}
