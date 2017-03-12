package com.example.tmnt.newcomputer.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Widget.PlanBar;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WrongQuestionFragment extends Fragment {

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
    @Bind(R.id.select_view)
    LinearLayout mSelectView;
    @Bind(R.id.fill_blank_answer)
    EditText mFillBlankAnswer;
    @Bind(R.id.confirm)
    Button mConfirm;
    @Bind(R.id.fillBlank_view)
    LinearLayout mFillBlankView;

    private View view;

    private boolean a1, a2, a3, a4;

    private int type;
    private Questions mQuestionses;
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
        mQuestionses = (Questions) getArguments().getSerializable(ANSWER);

        position = getArguments().getInt(POTISION);

        //Log.i(TAG, "onCreate: " + type);
        mDAO = QuestionDAO.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.answer_view_lay, container, false);
        ButterKnife.bind(this, view);

        mQuestionTitleView.setText(mQuestionses.getQuestion());
        mOptionA.setText(mQuestionses.getOptionA());
        mOptionB.setText(mQuestionses.getOptionB());
        if (type == 1) {

            mOptionC.setText(mQuestionses.getOptionC());
            mOptionD.setText(mQuestionses.getOptionD());

        } else if (type == 0) {
            mOptionC.setVisibility(View.INVISIBLE);
            mOptionD.setVisibility(View.INVISIBLE);
        } else if (type == 3) {
            mSelectView.setVisibility(View.GONE);
            mFillBlankView.setVisibility(View.VISIBLE);
            mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFillBlankAnswer.getText().toString().equals(mQuestionses.getAnswer())) {
                        Snackbar snackbar = Snackbar.make(view, "answer is " + mQuestionses.getAnswer(), Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }
                }
            });
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
     * @return
     */
    public static WrongQuestionFragment newInstance(Questions questionses, int type) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(TYPE, type);
        bundle.putSerializable(ANSWER, questionses);

        WrongQuestionFragment fragmentCrime = new WrongQuestionFragment();
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


        if (mQuestionses.getAnswer() == useranswer) {
            isClickItem = false;
            Utils.showToast(getActivity(), "right");
        } else if ((mQuestionses.getAnswer() != useranswer)) {
            isClickItem = false;

            Snackbar snackbar = Snackbar.make(view, "answer is " + getAnswer(mQuestionses.getAnswer()), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.blue_normal));
            snackbar.show();

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
                break;
            case R.id.optionB:
                isClickOtherAnswer(mOptionB, !a1 && !a2 && !a3 && !a4, 1);
                a2 = true;
                break;
            case R.id.optionC:
                isClickOtherAnswer(mOptionC, !a1 && !a2 && !a3 && !a4, 1);
                a3 = true;
                break;
            case R.id.optionD:
                isClickOtherAnswer(mOptionD, !a1 && !a2 && !a3 && !a4, 1);
                a4 = true;
                break;
        }
    }
}
