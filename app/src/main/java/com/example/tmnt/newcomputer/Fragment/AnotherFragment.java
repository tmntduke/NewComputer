package com.example.tmnt.newcomputer.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        view = inflater.inflate(R.layout.answer_view_lay, null, false);
        ButterKnife.bind(this, view);
        mQuestionTitleView.setText(mQuestionses.get(position).getQuestion());

        mCount.setVisibility(View.VISIBLE);
        mCount.setText(position + 1 + "/" + mQuestionses.size());
        mOptionA.setText(mQuestionses.get(position).getOptionA());
        mOptionB.setText(mQuestionses.get(position).getOptionB());
        mOptionC.setText(mQuestionses.get(position).getOptionC());
        mOptionD.setText(mQuestionses.get(position).getOptionD());


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
        } else if ((mQuestionses.get(position).getAnswer() != useranswer)) {
            Snackbar snackbar = Snackbar.make(view, "answer is " + getAnswer(mQuestionses.get(position).getAnswer()), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.blue_normal));
            snackbar.show();
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
