package com.example.tmnt.newcomputer.BMOB;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.a.a.V;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.InterFace.IFristLoad;
import com.example.tmnt.newcomputer.InterFace.IMPL.FristLoadIMPL;
import com.example.tmnt.newcomputer.InterFace.IMPL.MaxIdIMPL;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * bmob工具类
 * Created by tmnt on 2016/6/13.
 */
public class BmobUtils {

    private static final String TAG = "BmobUtils";
    int max;

    /**
     * 像云端保存数据
     *
     * @param o
     * @param context
     * @param view
     */
    public static void saveDataToBmob(Object o, Context context, View view, ActionProcessButton button) {
        if (o instanceof BmobObject) {
            ((BmobObject) o).save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    Snackbar.make(view, "you already add a question", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Snackbar snackbar = Snackbar.make(view, "add fail  because" + s, Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                    Log.i(TAG, "onFailure: " + s);
                    button.setEnabled(true);
                    button.setProgress(0);
                }
            });

        }
    }

    //取数据接口
    public interface DataResult {
        void getQuestionData(List<AnotherAnswer> l);
    }

    private static DataResult dataRe;

    public static void setDataResult(DataResult data) {
        dataRe = data;
    }

    /**
     * 查询指定类型数据
     *
     * @param context
     * @param kind
     * @param flag
     * @param filed
     */
    public static void getyAnotherAnswer(Context context, Object kind, int flag, String filed) {
        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        ArrayList<AnotherAnswer> question = new ArrayList<>();
        mAnotherQuestionBmobQuery.addWhereEqualTo(filed, kind);
        mAnotherQuestionBmobQuery.findObjects(context, new FindListener<AnotherAnswer>() {

            @Override
            public void onSuccess(List<AnotherAnswer> list) {
                Log.i(TAG, "onSuccess: " + list.size());
                if (dataRe != null) {
                    dataRe.getQuestionData(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                Utils.showToast(context, "wrong");
            }
        });

    }

    /**
     * 查询大于count的数据
     *
     * @param context
     * @param count
     */
    public static void getyBmobAnswer(Context context, int count) {
        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        ArrayList<AnotherAnswer> question = new ArrayList<>();
        mAnotherQuestionBmobQuery.addWhereGreaterThan("id", count);
        mAnotherQuestionBmobQuery.findObjects(context, new FindListener<AnotherAnswer>() {

            @Override
            public void onSuccess(List<AnotherAnswer> list) {
                if (FristLoadIMPL.mIFristLoad != null) {
                    FristLoadIMPL.mIFristLoad.fristLoad(list);
                }

            }


            @Override
            public void onError(int i, String s) {
                Utils.showToast(context, "wrong");
            }
        });


    }

    /**
     * 更新数据
     *
     * @param context
     * @param id
     */
    public static void updateLoad(Context context, String id) {
        AnotherAnswer answer = new AnotherAnswer();
        answer.setLoad(true);
        answer.update(context, id, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }


    /**
     * 查询最大id
     *
     * @param context
     */
    public static void maxIdToBmob(Context context) {

        BmobQuery<AnotherAnswer> mAnotherQuestionBmobQuery = new BmobQuery<>();
        mAnotherQuestionBmobQuery.max(new String[]{"id"});
        mAnotherQuestionBmobQuery.findStatistics(context, AnotherAnswer.class, new FindStatisticsListener() {
            @Override
            public void onSuccess(Object o) {
                JSONArray array = (JSONArray) o;

                try {
                    if (array == null) {
                        MaxIdIMPL.sIMaxId.maxId(0);
                    } else {
                        JSONObject object = array.getJSONObject(0);
                        if (object == null) {

                        } else {
                            if (MaxIdIMPL.sIMaxId != null) {
                                MaxIdIMPL.sIMaxId.maxId(object.getInt("_maxId"));
                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    /**
     * 批量添加
     *
     * @param list
     */
    public static void addForMore(List<AnotherAnswer> list, Context context, int id) {
        List<BmobObject> objects = new ArrayList<>();

        Log.i(TAG, "addForMore: " + list.size());
        Observable.range(0, list.size())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        new MsgHandle(context, objects).sendEmptyMessage(1001);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        objects.add(new AnotherAnswer(list.get(integer).getQuestion()
                                , list.get(integer).getOptionA()
                                , list.get(integer).getOptionB()
                                , list.get(integer).getOptionC()
                                , list.get(integer).getOptionD()
                                , list.get(integer).getAnswer()
                                , list.get(integer).getKind()
                                , list.get(integer).getQ_type()
                                , list.get(integer).getFillAnswer(), true, id + integer + 1));
                    }
                });
        Log.i(TAG, "addForMore: object" + objects.size());

    }

    static class MsgHandle extends Handler {
        private Context mContext;
        private List<BmobObject> mObjects;

        public MsgHandle(Context context, List<BmobObject> objects) {
            mContext = context;
            mObjects = objects;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                new BmobObject().insertBatch(mContext, mObjects, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Utils.showToast(mContext, "添加成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Utils.showToast(mContext, "添加失败" + s);
                    }
                });
            }
        }
    }

}

