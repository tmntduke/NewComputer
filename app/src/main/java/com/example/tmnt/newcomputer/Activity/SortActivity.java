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
 * 分类界面
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

        mDAO = QuestionDAO.getInstance(getApplicationContext());

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.ViewPager);
        setContentView(mViewPager);

        Intent intent = getIntent();

        //判断点击那个按钮
        switch (intent.getIntExtra("flag1", 0)) {
            case 0:
                getAnotherAdapter("select", 0);//点击选择

                break;
            case 1:
                getAnotherAdapter("fillBlank", 1);//点击填空
                break;

        }
    }

    //从云端获取指定类型题目数据
    public void getAnotherAdapter(String kind, int flag) {
        BmobUtils.getyAnotherAnswer(getApplicationContext(), kind, flag, "kind");//指定类型
        FragmentManager manager = getSupportFragmentManager();

        //获取数据
        BmobUtils.setDataResult(new BmobUtils.DataResult() {
            @Override
            public void getQuestionData(List<AnotherAnswer> l) {

                //创建ViewPager
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
