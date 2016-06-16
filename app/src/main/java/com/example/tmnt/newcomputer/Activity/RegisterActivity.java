package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import cn.bmob.v3.Bmob;

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

        Bmob.initialize(this, "5b5167d530b5db1c3696b59f02b904bb");

        mRegisterBtn.setMode(ActionProcessButton.Mode.PROGRESS);


        //mRegisterBtn.setProgress(100);

        mRegisterPassword.setOnFocusChangeListener((view, isCheck) -> {
            String regex = "\\w{4,8}";
            if (!(mRegisterUsername.getText().toString().matches(regex)) || (mRegisterUsername.getText().toString().isEmpty())) {
                mRegisterUsername.setError("username illegimate");
            } else if (mDAO.queryUserName(mRegisterUsername.getText().toString())) {
                mRegisterUsername.setError("username already have");

//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                hideSoftInput();
                mRegisterPassword.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);//隐藏软键盘
                Snackbar snackbar = Snackbar.make(view, "you have a account you can login", Snackbar.LENGTH_INDEFINITE)
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
//
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

    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}



