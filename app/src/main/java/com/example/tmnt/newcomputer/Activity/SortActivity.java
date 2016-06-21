package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.example.tmnt.newcomputer.BMOB.BmobUtils;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.AnotherFragment;
import com.example.tmnt.newcomputer.Fragment.AnswerFragment;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by tmnt on 2016/6/13.
 */
public class SortActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private static final String TAG = "SortActivity";

    private QuestionDAO mDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(SortActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);

        Bmob.initialize(this, "5b5167d530b5db1c3696b59f02b904bb");
        mDAO = new QuestionDAO(getApplicationContext());

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.ViewPager);
        setContentView(mViewPager);

        Intent intent = getIntent();


        switch (intent.getIntExtra("flag1", 0)) {
            case 0:
                getAnotherAdapter("select", 0);

                break;
            case 1:
                getAnotherAdapter("fillBlank", 1);
                break;

        }
    }

    public void getAnotherAdapter(String kind, int flag) {
        BmobUtils.getyAnotherAnswer(getApplicationContext(), kind, flag, "kind");
        FragmentManager manager = getSupportFragmentManager();

        BmobUtils.setDataResult(new BmobUtils.DataResult() {
            @Override
            public void getQuestionData(List<AnotherAnswer> l) {
                FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(manager) {
                    @Override
                    public Fragment getItem(int i) {
                        ArrayList<AnotherAnswer> ll = (ArrayList<AnotherAnswer>) l;
                        if (ll.size() == 0) {
                            return AnotherFragment.newInstance(ll, i, 0, flag);
                        } else {

                            return AnotherFragment.newInstance(ll, i, ll.get(i).getQ_type(), flag);
                        }

                    }

                    @Override
                    public int getCount() {
                        if (l.size() == 0) {
                            return 1;
                        } else {
                            return l.size();
                        }


                    }
                };
                mViewPager.setAdapter(adapter);
            }
        });

    }
}
