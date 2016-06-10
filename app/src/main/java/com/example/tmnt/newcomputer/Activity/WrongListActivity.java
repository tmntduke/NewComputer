package com.example.tmnt.newcomputer.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.tmnt.newcomputer.Adapter.UserMsgAdapter;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/6/10.
 */
public class WrongListActivity extends AppCompatActivity {

    @Bind(R.id.wrong_contain)
    ListView mWrongContain;
    @Bind(R.id.wrong_tool)
    Toolbar mWrongTool;

    private QuestionDAO mDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(WrongListActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
        setContentView(R.layout.wrong_contain);

        ButterKnife.bind(this);

        mWrongTool.setTitle("我的错题");
        setSupportActionBar(mWrongTool);

        mDAO = new QuestionDAO(getApplicationContext());
        UserMsgAdapter adapter = new UserMsgAdapter(getApplicationContext(), mDAO.queryWrongQuestion());
        mWrongContain.setAdapter(adapter);


    }
}
