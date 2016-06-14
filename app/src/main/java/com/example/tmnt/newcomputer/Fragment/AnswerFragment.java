package com.example.tmnt.newcomputer.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tmnt.newcomputer.Activity.ExamActivity;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.DView.PlanBar;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ImageUtils;
import com.example.tmnt.newcomputer.Utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerFragment extends Fragment {

    public static final String ANSWER = "answer";
    public static final String POTISION = "potision";
    public static final String TYPE = "type";
    public static final String KIND = "kind";
    public static final String ANOTHER = "another";
    public static final String UPDATEPROGRESS = "updateProgress";
    @Bind(R.id.test)
    PlanBar mTest;
    @Bind(R.id.question_title_view)
    TextView mQuestionTitleView;
    @Bind(R.id.optionA)
    Button mOptionA;
    @Bind(R.id.optionB)
    Button mOptionB;
    @Bind(R.id.optionC)
    Button mOptionC;
    @Bind(R.id.optionD)
    Button mOptionD;
    @Bind(R.id.count)
    TextView mCount;

    private View view;

    private boolean a1, a2, a3, a4;

    private int type;
    private ArrayList<Questions> mQuestionses;
    private int position;
    private int kind;

    private ArrayList<Questions> wrongCount;

    private static boolean isClickItem = true;

    private int answer;
    private QuestionDAO mDAO;

    private static final String TAG = "AnswerFragment";
    private int p;
    private int updateProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE);
        mQuestionses = (ArrayList<Questions>) getArguments().getSerializable(ANSWER);

        position = getArguments().getInt(POTISION);
        kind = getArguments().getInt(KIND);

        updateProgress = getArguments().getInt(UPDATEPROGRESS);
        //Log.i(TAG, "onCreate: " + type);
        mDAO = new QuestionDAO(getActivity());
        wrongCount = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mQuestionses.size() == 0) {
            view = inflater.inflate(R.layout.user_msg_empty, container, false);
        } else {

            view = inflater.inflate(R.layout.answer_view_lay, container, false);
            ButterKnife.bind(this, view);
            if (kind == 3) {
                mCount.setVisibility(View.VISIBLE);
                mCount.setTextColor(Color.BLACK);
                mCount.setText((position + 1) + "/" + "20");
                mTest.setVisibility(View.VISIBLE);
                if (updateProgress != -1) {
                    Log.i(TAG, "onCreateView: " + updateProgress);
                    mTest.update(updateProgress);
                }
            } else {
                mCount.setVisibility(View.VISIBLE);
                mCount.setText(mQuestionses.get(position).get_id() + "/" + mQuestionses.size());
            }


            mQuestionTitleView.setText(mQuestionses.get(position).getQuestion());
            mOptionA.setText(mQuestionses.get(position).getOptionA());
            mOptionB.setText(mQuestionses.get(position).getOptionB());
            if (type == 1) {

                mOptionC.setText(mQuestionses.get(position).getOptionC());
                mOptionD.setText(mQuestionses.get(position).getOptionD());

            } else if (type == 0) {
                mOptionC.setVisibility(View.INVISIBLE);
                mOptionD.setVisibility(View.INVISIBLE);
            }

//            if (kind == 2 && position == 19) {
//                if (position == 19) {
//                }
//            }

        }

        return view;
    }

    /**
     * 设置进度条进度
     *
     * @param updateProgress
     */


    /**
     * 创建fragment实例
     *
     * @param questionses 将问题集合传入
     * @param position    当前问题页面
     * @param type        问题的类型
     * @return
     */
    public static AnswerFragment newInstance(ArrayList<Questions> questionses, int position, int type, int questionKind, int updateProgress) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(POTISION, position);
        bundle.putSerializable(TYPE, type);
        bundle.putSerializable(KIND, questionKind);
        bundle.putSerializable(UPDATEPROGRESS, updateProgress);
        bundle.putSerializable(ANSWER, questionses);

        AnswerFragment fragmentCrime = new AnswerFragment();
        fragmentCrime.setArguments(bundle);
        return fragmentCrime;
    }

    /**
     * 根据用户答案，进行相应的提示
     *
     * @param useranswer 用户的答案
     */
    public void show(int useranswer) {

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("clickItem", Context.MODE_PRIVATE).edit();


        if (kind != 3 && mQuestionses.get(position).getAnswer() == useranswer) {
            isClickItem = false;
            Utils.showToast(getActivity(), "right");
        } else if (kind != 3 && (mQuestionses.get(position).getAnswer() != useranswer)) {
            isClickItem = false;

            Snackbar snackbar = Snackbar.make(view, "answer is " + getAnswer(mQuestionses.get(position).getAnswer()), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.blue_normal));
            snackbar.show();

        } else if (kind == 3 && (mQuestionses.get(position).getAnswer() != useranswer) && !a1 && !a2 && !a3 && !a4) {
            isClickItem = false;
            Log.i(TAG, "show: " + mQuestionses.get(position));
            wrongCount.add(mQuestionses.get(position));
            mDAO.addWrong(mQuestionses.get(position));
        } else if (kind == 3 && (mQuestionses.get(position).getAnswer() == useranswer)) {
            isClickItem = false;
        }

        editor.putBoolean("click", isClickItem);
        editor.commit();
    }

    /**
     * 判断是否点击了其他的按钮 并改变点击的选项
     *
     * @param v
     * @param isClickItem
     * @param f
     */
    public void isClickOtherAnswer(View v, boolean isClickItem, int f) {
        if (isClickItem) {
            changeBackground(v);
            show(f);

        } else {
            Utils.showToast(getActivity(), "you already click other answer");
        }
    }

    public void changeBackground(View v) {
        if (kind == 3) {
            v.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }


    /**
     * 将答案代码转换为英文
     *
     * @param answer
     * @return
     */
    public String getAnswer(int answer) {
        String a = null;
        switch (answer) {
            case 1:
                a = "A";
                break;
            case 2:
                a = "B";
                break;
            case 3:
                a = "C";
                break;
            case 4:
                a = "D";
                break;

        }
        return a;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.optionA, R.id.optionB, R.id.optionC, R.id.optionD})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.optionA:
                isClickOtherAnswer(mOptionA, !a1 && !a2 && !a3 && !a4, 1);
                a1 = true;
                showResult();
                break;
            case R.id.optionB:
                isClickOtherAnswer(mOptionB, !a1 && !a2 && !a3 && !a4, 1);
                a2 = true;
                showResult();
                break;
            case R.id.optionC:
                isClickOtherAnswer(mOptionC, !a1 && !a2 && !a3 && !a4, 1);
                a3 = true;
                showResult();
                break;
            case R.id.optionD:
                isClickOtherAnswer(mOptionD, !a1 && !a2 && !a3 && !a4, 1);
                a4 = true;
                showResult();
                break;
        }
    }

    private void showResult() {
        if (kind == 3 && position == 19) {
            Snackbar snackbar = Snackbar.make(view, "wrong:  " + mDAO.queryAllWrong().size() + "   turn to other view", Snackbar.LENGTH_INDEFINITE).setAction("click", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ExamActivity.class);
                    intent.putExtra(HomeFragment.FLAG, 4);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }

    }
}
