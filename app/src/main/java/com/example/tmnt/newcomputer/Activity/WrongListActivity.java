package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tmnt.newcomputer.Adapter.UserMsgAdapter;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 错误列表
 * Created by tmnt on 2016/6/10.
 */
public class WrongListActivity extends AppCompatActivity {

    @Bind(R.id.wrong_contain)
    ListView mWrongContain;
    @Bind(R.id.wrong_tool)
    Toolbar mWrongTool;

    private QuestionDAO mDAO;
    public static final String QUESTION = "question";
    private static final String TAG = "WrongListActivity";

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

        mDAO = new QuestionDAO(getApplicationContext());

        int wrong = mDAO.queryWrongQuestion().size();

        //当有错误题目
        if (wrong != 0) {
            setContentView(R.layout.wrong_contain);
            ButterKnife.bind(this);
            mWrongTool.setTitle("我的错题");
            setSupportActionBar(mWrongTool);

            UserMsgAdapter adapter = new UserMsgAdapter(getApplicationContext(), mDAO.queryWrongQuestion());
            adapter.notifyDataSetChanged();
            mWrongContain.setAdapter(adapter);

            //删除错误题目
            adapter.setOnClickSlideItemListener(new UserMsgAdapter.OnClickSlideItemListener() {
                @Override
                public void clickDelete(View view, String question, int position) {
                    mDAO.deleteWrong(question);
                    adapter.deleteData(position);
                }

                @Override
                public void recover(int position) {

                }

                @Override
                public void onClickQurstionItem(View v, String question, boolean isOpen) {
                    //当删除按钮开启时 不能点击
                    if (!isOpen) {
                        Intent intent = new Intent(WrongListActivity.this, WrongItemActivity.class);
                        intent.putExtra(QUESTION, question);
                        startActivity(intent);
                    }
                }
            });

        } else {
            setContentView(R.layout.user_msg_empty);
        }


    }
}
