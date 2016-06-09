package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.DView.AutoComplete;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ProgressGenerator;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/5/7.
 */
public class RegisterActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    @Bind(R.id.register_username)
    EditText mRegisterUsername;
    @Bind(R.id.register_password)
    EditText mRegisterPassword;
    @Bind(R.id.register_password_commit)
    EditText mRegisterPasswordCommit;
    @Bind(R.id.register_btn)
    ActionProcessButton mRegisterBtn;
    @Bind(R.id.register_mail)
    AutoComplete mRegisterMail;


    private boolean isOver = false;

    private QuestionDAO mDAO;

    private boolean isUsername, isPassword;
    private int x, y;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(RegisterActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        mDAO = new QuestionDAO(getApplicationContext());

        mRegisterBtn.setMode(ActionProcessButton.Mode.PROGRESS);


        //mRegisterBtn.setProgress(100);

        mRegisterPassword.setOnFocusChangeListener((view, isCheck) -> {
            String regex = "\\w{4,8}";
            if (!(mRegisterUsername.getText().toString().matches(regex)) || (mRegisterUsername.getText().toString().isEmpty())) {
                mRegisterUsername.setError("username illegimate");
            } else if (mDAO.queryUserName(mRegisterUsername.getText().toString())) {
                mRegisterUsername.setError("username already have");

                Snackbar snackbar = Snackbar.make(view, "you have a account you can login", Snackbar.LENGTH_LONG)
                        .setAction("click here", (v) -> {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("haveLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", mRegisterUsername.getText().toString());
                            editor.commit();
                            intent.putExtra("register", 1);
                            startActivity(intent);
                            finish();
                        });
                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));//设置SnackBar颜色
                snackbar.setActionTextColor(Color.rgb(127, 256, 169));
                snackbar.show();

            } else {
                isUsername = true;
            }
        });

        mRegisterPasswordCommit.setOnFocusChangeListener((view, isCheck) -> {
            String regex = "(?=.*\\d)(?=.*[a-zA-Z]).{8,10}";
            if (!(mRegisterPassword.getText().toString().matches(regex)) || (mRegisterPassword.getText().toString().isEmpty())) {
                mRegisterPassword.setError("password illegimate");

            } else {
                isPassword = true;
            }
        });

//        Random random = new Random();
//        int a = random.nextInt(100);
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);

        mRegisterBtn.setOnClickListener((v) ->
                {
                    if (!(mRegisterPasswordCommit.getText().toString().equals(mRegisterPassword.getText().toString()))
                            || (mRegisterPasswordCommit.getText().toString().isEmpty()) || mRegisterMail.getText().toString().isEmpty()) {
                        mRegisterPasswordCommit.setError("password not identical");
                    } else if ((mRegisterPasswordCommit.getText().toString().equals(mRegisterPassword.getText().toString()) && isUsername && isPassword)) {
//                        Animator animator = ViewAnimationUtils.createCircularReveal(mRegisterBtn, x, y, (float) (mRegisterBtn.getWidth() * 2.5), 0);
//                        animator.setInterpolator(new BounceInterpolator());
//                        animator.setDuration(1500);
//                        animator.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                mRegisterBtn.setBackgroundResource(R.drawable.fgt);
//                                mRegisterBtn.setText("");
//                                try {
//                                    Thread.sleep(1500);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        });
//                        animator.setTarget(mRegisterBtn);
//                        animator.start();
                        isOver = true;
                        progressGenerator.start(mRegisterBtn);
                        mRegisterBtn.setEnabled(false);


                    }
                }

        );

    }


    @Override
    public void onComplete() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        mDAO.addUser(mRegisterUsername.getText().toString(), mRegisterPassword.getText().toString());
        intent.putExtra("username", mRegisterUsername.getText().toString());
        intent.putExtra("username", mRegisterUsername.getText().toString());
        intent.putExtra("password", mRegisterPasswordCommit.getText().toString());
        startActivity(intent);
        finish();
    }
}


