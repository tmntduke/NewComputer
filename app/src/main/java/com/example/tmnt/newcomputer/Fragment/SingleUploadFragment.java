package com.example.tmnt.newcomputer.Fragment;

import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.BMOB.BmobUtils;
import com.example.tmnt.newcomputer.InterFace.IMPL.MaxIdIMPL;
import com.example.tmnt.newcomputer.InterFace.IMaxId;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ProgressGenerator;
import com.example.tmnt.newcomputer.Utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by tmnt on 2016/6/17.
 */
public class SingleUploadFragment extends Fragment implements ProgressGenerator.OnCompleteListener {

    @Bind(R.id.select)
    RadioButton mSelect;
    @Bind(R.id.fill_blank)
    RadioButton mFillBlank;
    @Bind(R.id.kind)
    RadioGroup mKind;
    @Bind(R.id.add_question)
    EditText mAddQuestion;
    @Bind(R.id.nomoal)
    RadioButton mNomoal;
    @Bind(R.id.judge)
    RadioButton mJudge;
    @Bind(R.id.type)
    RadioGroup mType;
    @Bind(R.id.add_optionA)
    EditText mAddOptionA;
    @Bind(R.id.add_optionB)
    EditText mAddOptionB;
    @Bind(R.id.add_optionC)
    EditText mAddOptionC;
    @Bind(R.id.add_optionD)
    EditText mAddOptionD;
    @Bind(R.id.A)
    RadioButton mA;
    @Bind(R.id.B)
    RadioButton mB;
    @Bind(R.id.C)
    RadioButton mC;
    @Bind(R.id.D)
    RadioButton mD;
    @Bind(R.id.add_answer)
    RadioGroup mAddAnswer;
    @Bind(R.id.fill_blank_answer)
    EditText mFillBlankAnswer;
    @Bind(R.id.textInput)
    TextInputLayout mTextInput;
    @Bind(R.id.add_commit)
    ActionProcessButton mAddCommit;
    @Bind(R.id.select_question_view)
    LinearLayout mSelectQuestionView;

    private String question;
    private int type;
    private int answer;
    private boolean isKind;
    private ProgressGenerator progressGenerator;
    private AnotherAnswer another;
    private AnotherAnswer another1;
    private View view;

    private int idFromBomb;

    private static final String FLAG = "flag";
    private int flag;
    private static final String TAG = "SingleUploadFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getInt(FLAG);
        Bmob.initialize(getActivity(), "4556f6a1fe01d72ebe7e4c62e41d381c");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.single_upload_fragment, container, false);
        ButterKnife.bind(this, view);

        progressGenerator = new ProgressGenerator(this);


        if (flag == 001 || flag == 002) {
            mKind.setVisibility(View.INVISIBLE);
        }

        if (flag == 001) {
            mTextInput.setVisibility(View.GONE);
            isKind = false;
        } else if (flag == 002) {
            mSelectQuestionView.setVisibility(View.GONE);
            mTextInput.setVisibility(View.VISIBLE);
            isKind = true;
        } else {

        }
        mKind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.select:
                        isKind = false;
                        mSelectQuestionView.setVisibility(View.VISIBLE);
                        mTextInput.setVisibility(View.GONE);
                        break;
                    case R.id.fill_blank:
                        isKind = true;
                        mSelectQuestionView.setVisibility(View.GONE);
                        mTextInput.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });

        mAddAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.A:
                        answer = 1;
                        break;
                    case R.id.B:
                        answer = 2;
                        break;
                    case R.id.C:
                        answer = 3;
                        break;
                    case R.id.D:
                        answer = 4;
                        break;
                }
            }
        });

        mType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.nomoal) {
                    type = 1;
                    mAddOptionC.setEnabled(true);
                    mAddOptionD.setEnabled(true);
                    mC.setVisibility(View.VISIBLE);
                    mD.setVisibility(View.VISIBLE);
                } else {
                    type = 0;
                    mAddOptionC.setEnabled(false);
                    mAddOptionD.setEnabled(false);
                    mC.setVisibility(View.GONE);
                    mD.setVisibility(View.GONE);
                }
            }

        });

        mAddCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(mAddCommit);
                mAddCommit.setEnabled(false);

            }
        });

        return view;
    }

    public static Fragment getInstance(int flag) {
        SingleUploadFragment fragment = new SingleUploadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FLAG, flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onComplete() {

        BmobUtils.maxIdToBmob(getActivity());
        MaxIdIMPL.setMaxId(new IMaxId() {
            @Override
            public void maxId(int id) {
                idFromBomb = id;
            }
        });
        if (!isKind) {


            another = new AnotherAnswer(mAddQuestion.getText().toString()
                    , "A." + mAddOptionA.getText().toString()
                    , "B." + mAddOptionB.getText().toString()
                    , "C." + mAddOptionC.getText().toString()
                    , "D." + mAddOptionD.getText().toString()
                    , answer, "select", type, null, true, idFromBomb + 1
            );
            BmobUtils.saveDataToBmob(another, getActivity(), view, mAddCommit);
        } else {
            another1 = new AnotherAnswer(mAddQuestion.getText().toString(), null, null, null, null
                    , 1, "fillBlank", 3, mFillBlankAnswer.getText().toString(), true, idFromBomb + 1);
            BmobUtils.saveDataToBmob(another1, getActivity(), view, mAddCommit);
        }

    }
}
