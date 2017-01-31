package com.example.tmnt.newcomputer.Fragment;

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

import com.example.tmnt.newcomputer.DView.PlanBar;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tmnt on 2016/5/31.
 */
public class AnotherFragment extends Fragment {

    public static final String AOTHERANSWER = "answer";
    public static final String AOTHERPOTISION = "potision";
    public static final String AOTHERTYPE = "type";
    public static final String AOTHERKIND = "kind";
    public static final String ANOTHER = "another";
    @Bind(R.id.test)
    PlanBar mTest;
    @Bind(R.id.count)
    TextView mCount;
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
    @Bind(R.id.select_view)
    LinearLayout mSelectView;
    @Bind(R.id.fill_blank_answer)
    EditText mFillBlankAnswer;
    @Bind(R.id.confirm)
    Button mConfirm;
    @Bind(R.id.fillBlank_view)
    LinearLayout mFillBlankView;


    private boolean a1, a2, a3, a4;

    private int type;
    private ArrayList<AnotherAnswer> mQuestionses;
    private int position;
    private int kind;
    private View view;

    private static final String TAG = "AnotherFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(AOTHERTYPE);
        mQuestionses = (ArrayList<AnotherAnswer>) getArguments().getSerializable(ANOTHER);

        position = getArguments().getInt(AOTHERPOTISION);
        kind = getArguments().getInt(AOTHERKIND);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mQuestionses.size() == 0) {
            view = inflater.inflate(R.layout.user_msg_empty, container, false);
        } else {


            view = inflater.inflate(R.layout.answer_view_lay, null, false);
            ButterKnife.bind(this, view);

            if (kind == 0) {
                mQuestionTitleView.setText(mQuestionses.get(position).getQuestion());
                mCount.setVisibility(View.VISIBLE);
                mCount.setText(position + 1 + "/" + mQuestionses.size());
                mOptionA.setText(mQuestionses.get(position).getOptionA());
                mOptionB.setText(mQuestionses.get(position).getOptionB());

                if (type == 1) {

                    mOptionC.setText(mQuestionses.get(position).getOptionC());
                    mOptionD.setText(mQuestionses.get(position).getOptionD());

                } else if (type == 0) {
                    mOptionC.setVisibility(View.INVISIBLE);
                    mOptionD.setVisibility(View.INVISIBLE);
                }

                mOptionC.setText(mQuestionses.get(position).getOptionC());
                mOptionD.setText(mQuestionses.get(position).getOptionD());
            } else {
                mQuestionTitleView.setText(mQuestionses.get(position).getQuestion());
                mCount.setVisibility(View.VISIBLE);
                mCount.setText(position + 1 + "/" + mQuestionses.size());
                mSelectView.setVisibility(View.GONE);
                mFillBlankView.setVisibility(View.VISIBLE);
                mConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mFillBlankAnswer.getText().toString().equals(mQuestionses.get(position).getFillAnswer())) {
                            Snackbar snackbar = Snackbar.make(view, "answer is " + mQuestionses.get(position).getFillAnswer(), Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else {
                            Utils.showToast(getActivity(), "right");
                        }
                    }
                });
            }

        }


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public static AnotherFragment newInstance(ArrayList<AnotherAnswer> questionses, int position, int type, int questionKind) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(AOTHERPOTISION, position);
        bundle.putSerializable(AOTHERTYPE, type);
        bundle.putSerializable(AOTHERKIND, questionKind);

        bundle.putSerializable(ANOTHER, questionses);

        AnotherFragment fragmentCrime = new AnotherFragment();
        fragmentCrime.setArguments(bundle);
        return fragmentCrime;
    }

    public void isClickOtherAnswer(View v, boolean isClickItem, int f) {
        if (isClickItem) {
            show(f);

        } else {
            Utils.showToast(getActivity(), "you already click other answer");
        }
    }


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

    public void show(int useranswer) {

        if (mQuestionses.get(position).getAnswer() == useranswer) {

            Utils.showToast(getActivity(), "right");
        } else if ((mQuestionses.get(position).getAnswer() != useranswer) && kind == 0) {
            Snackbar snackbar = Snackbar.make(view, "answer is "
                    + getAnswer(mQuestionses.get(position).getAnswer()), Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.blue_normal));
            snackbar.show();
        } else if (kind == 1 && (mQuestionses.get(position).getFillAnswer() != mFillBlankAnswer.getText().toString())) {
            Snackbar snackbar2 = Snackbar.make(view, "answer is " + mQuestionses.get(position).getFillAnswer(), Snackbar.LENGTH_SHORT);
            snackbar2.getView().setBackgroundColor(getResources().getColor(R.color.blue_normal));
            snackbar2.show();
        }
    }

    @OnClick({R.id.optionA, R.id.optionB, R.id.optionC, R.id.optionD})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.optionA:
                isClickOtherAnswer(mOptionA, !a1 && !a2 && !a3 && !a4, 1);
                a1 = true;
                break;
            case R.id.optionB:
                isClickOtherAnswer(mOptionB, !a1 && !a2 && !a3 && !a4, 2);
                a2 = true;
                break;
            case R.id.optionC:
                isClickOtherAnswer(mOptionC, !a1 && !a2 && !a3 && !a4, 3);
                a3 = true;
                break;
            case R.id.optionD:
                isClickOtherAnswer(mOptionD, !a1 && !a2 && !a3 && !a4, 4);
                a4 = true;
                break;
        }
    }
}
