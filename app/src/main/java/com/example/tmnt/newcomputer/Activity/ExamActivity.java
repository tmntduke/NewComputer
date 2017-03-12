package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.AnswerFragment;
import com.example.tmnt.newcomputer.Fragment.HomeFragment;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * 控制试题显示界面
 * Created by tmnt on 2016/6/11.
 */
public class ExamActivity extends FragmentActivity {

    private static QuestionDAO mDAO;
    private static ViewPager mViewPager;
    private ArrayList<Questions> mList;
    private static final String TAG = "ExamActivity";
    private static FragmentManager manager;
    private static int flag;
    private static ArrayList<Questions> turn;
    private static AnswerFragment fragment;

    /**
     * 根据不同标志位显示不同问题界面
     */
//    private static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            //判断不同类别
//            if (msg.what == 0) {
//                ArrayList<Questions> mList = (ArrayList<Questions>) msg.obj;
//                //点击顺序和随机练习取出数据
//                if (flag == 1 || flag == 2) {
//                    //创建viewPager的adapter
//                    FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager, mList, flag);
//                    mViewPager.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();//刷新适配器
//                } else if (flag == 3) {
//                    //点击模拟考试时随机取出20条数据
//                    for (int i = 0; i <= 19; i++) {
//                        Random random = new Random();
//                        int p = random.nextInt(mDAO.queryAll().size());
//                        turn.add(mList.get(p));
//                    }
//
//                    FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager, turn, flag);
//                    mViewPager.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
//            } else if (msg.what == 1) {
////                if (flag == 4) {
////                    //点击待加强时
////                    ArrayList<Questions> mList1 = new ArrayList<>();
////                    mList1 = (ArrayList<Questions>) msg.obj;
////                    FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager, mList1, flag);
////
////                    mViewPager.setAdapter(adapter);
////                    adapter.notifyDataSetChanged();
////                }
//            }
//        }
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitlebarColor();

        initData();

        initView();

        loadThread();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDAO = QuestionDAO.getInstance(getApplicationContext());
        mList = new ArrayList<Questions>();
        Intent intent = getIntent();
        //判断传入的类别
        flag = intent.getIntExtra(HomeFragment.FLAG, 0);

        turn = new ArrayList<>();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mViewPager = new ViewPager(this);//创建ViewPager
        mViewPager.setId(R.id.ViewPager);
        setContentView(mViewPager);
    }


    /**
     * 创建线程
     */
    private void loadThread() {
        manager = getSupportFragmentManager();
        fragment = new AnswerFragment();

        //在子线程中从数据库中将数据取出
//        new Thread(() -> {
//            ArrayList<Questions> list = mDAO.queryAll();
//            Message message = Message.obtain();
//            message.what = 0;
//            message.obj = list;
//            handler.sendMessage(message);
//        }).start();

        //将错误表中的数据取出
//        new Thread(() -> {
//            ArrayList<Questions> list = mDAO.queryAllWrong();
//            Message message = Message.obtain();
//            message.what = 1;
//            message.obj = list;
//            handler.sendMessage(message);
//        }).start();

        if (flag != 4) {

            Observable.create(e -> {
                ArrayList<Questions> list = mDAO.queryAll();
                e.onNext(list);
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(question -> {
                        if (flag != 3) {
                            //创建viewPager的adapter
                            FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager
                                    , (ArrayList<Questions>) question, flag);
                            mViewPager.setAdapter(adapter);
                            adapter.notifyDataSetChanged();//刷新适配器
                        } else if (flag == 3) {
                            //点击模拟考试时随机取出20条数据
                            for (int i = 0; i <= 19; i++) {
                                Random random = new Random();
                                int p = random.nextInt(mDAO.queryAll().size());
                                turn.add(((ArrayList<Questions>) question).get(p));
                            }

                            FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager, turn, flag);
                            mViewPager.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

        }else if (flag == 4) {
            Observable.create(e -> {
                ArrayList<Questions> list = mDAO.queryAllWrong();
                e.onNext(list);
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(questions -> {

                        FragmentStatePagerAdapter adapter = Utils.getFragmentAdater(manager
                                , (ArrayList<Questions>) questions, flag);

                        mViewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    });
        }


    }

    /**
     * 适配4.4系统 沉浸状态栏
     */
    private void setTitlebarColor() {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(ExamActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDAO.closeConn();
    }
}
