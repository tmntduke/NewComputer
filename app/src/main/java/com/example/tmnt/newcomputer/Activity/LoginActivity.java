package com.example.tmnt.newcomputer.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.Adapter.LoginAutoCompleteAdapter;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.UserMessageFragment;
import com.example.tmnt.newcomputer.MainActivity;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ProgressGenerator;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登陆界面
 * Created by tmnt on 2016/5/7.
 */
public class LoginActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    @Bind(R.id.edit_username)
    AutoCompleteTextView mEditUsername;
    @Bind(R.id.edit_pwd)
    EditText mEditPwd;
    @Bind(R.id.login)
    ActionProcessButton mLogin;
    @Bind(R.id.loginLayout)
    LinearLayout mLoginLayout;
    private QuestionDAO mDAO;
    private String username;
    private String password;

    private boolean isClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(LoginActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }


        ProgressGenerator progressGenerator = new ProgressGenerator(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        setContentView(R.layout.login_layout);
        setEnterAnmition();
        showExit();
        ButterKnife.bind(this);


        mDAO = new QuestionDAO(getApplicationContext());
        mLogin.setMode(ActionProcessButton.Mode.PROGRESS);

        LoginAutoCompleteAdapter adapter = new LoginAutoCompleteAdapter(LoginActivity.this, R.layout.activity_autocomplete_item, mDAO.queryAlluser());
        mEditUsername.setAdapter(adapter);

        if (!mDAO.isLogin()) {
            Intent intent = getIntent();
            if (intent.getIntExtra("register", 0) == 1) {
                SharedPreferences sharedPreferences = getSharedPreferences("haveLogin", MODE_PRIVATE);
                username = sharedPreferences.getString("username", "");
                mEditUsername.setText(username);
            } else {
                username = intent.getStringExtra("username");
                password = intent.getStringExtra("password");
                mEditUsername.setText(username);
                mEditPwd.setText(password);
            }

            mEditPwd.setOnFocusChangeListener((view, isCheck) -> {
                if (!mDAO.queryUserName(mEditUsername.getText().toString()) || mEditUsername.getText().toString().isEmpty()) {
                    mEditUsername.setError("username not have");
                }
            });

            mLogin.setOnClickListener((v) -> {

                if (mDAO.queryUser(mEditUsername.getText().toString(), mEditPwd.getText().toString())) {
                    isClick = true;
                    progressGenerator.start(mLogin);
                    mLogin.setEnabled(false);
                } else {
                    mEditPwd.setError("username or password wrong");
                    mLogin.setError("ERROR");
                }


            });

        }

    }

    @Override
    public void onComplete() {
        Intent turnMain = new Intent(LoginActivity.this, MainActivity.class);
        mDAO.updateLogin(true);
        mDAO.updateUserLogin(mEditUsername.getText().toString(), true);
        turnMain.putExtra("user", mEditUsername.getText().toString());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
            startActivity(turnMain, options.toBundle());
            finish();
        } else {
            startActivity(turnMain);
            finish();
        }
    }

    public void showExit() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(2000);
            getWindow().setExitTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getWindow().setReturnTransition(fade);
        }
    }

    public void setEnterAnmition() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Explode explode = new Explode();
            explode.setDuration(2000);
            getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getWindow().setReturnTransition(fade);
        }
    }
}
