package com.example.tmnt.newcomputer.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.DView.CircleBar;
import com.example.tmnt.newcomputer.R;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 统计界面
 * Created by tmnt on 2016/6/10.
 */
public class TotalActivity extends AppCompatActivity {
    @Bind(R.id.progress)
    CircleBar mProgress;
    @Bind(R.id.day)
    TextView mDay;
    @Bind(R.id.tag)
    TextView mTag;
    @Bind(R.id.total)
    TextView mTotal;
    @Bind(R.id.cancle)
    ImageView mCancle;

    private boolean isCancle;

    private int wrong;
    private QuestionDAO mDAO;
    private int total;
    private DecimalFormat fnum = new DecimalFormat("#.0");
    private static final String TAG = "TotalActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mDAO = new QuestionDAO(getApplicationContext());
        wrong = mDAO.queryAllWrong().size();

        setContentView(R.layout.totale_lay);
        ButterKnife.bind(this);
        total = mDAO.queryModelCount() * 20;


        //显示统计信息
        if (wrong != 0) {
            mTag.setText(String.valueOf(wrong));
            mTotal.setText(String.valueOf(total));
            float show = ((float) wrong) / total * 12;
            mProgress.update((int) show, 3500);
        } else {
            mTag.setText(String.valueOf(0));
            mTotal.setText(String.valueOf(0));
            mProgress.update(0, 0);
        }

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void defaultBack() {
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}



