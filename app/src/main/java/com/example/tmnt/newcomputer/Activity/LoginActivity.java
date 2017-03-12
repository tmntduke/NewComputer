package com.example.tmnt.newcomputer.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.Adapter.LoginAutoCompleteAdapter;
import com.example.tmnt.newcomputer.AppInfo.FristSlideAppinfo;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
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
    @Bind(R.id.register)
    TextView mRegister;

    private QuestionDAO mDAO;
    private String username;
    private String password;

    private ProgressGenerator progressGenerator;

    private SharedPreferences get;
    private SharedPreferences exit;

    private LoginAutoCompleteAdapter adapter;

    private boolean isClick;
    private boolean isExit;
    public static final String FIRSTLOGIN = "fristlogin";
    public static final String NOTFIRST = "notfirst";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //将标题栏和状态栏去除
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);

        setTitlebarColor();

        initData();

        setEnterAnmition();//入场动画

        showExit();//退场动画

        checkLogin();

    }

    /**
     * 用户初始化操作
     */
    private void initData() {
        progressGenerator = new ProgressGenerator(this);
        mDAO = QuestionDAO.getInstance(getApplicationContext());

        //用户输入框初始化
        adapter = new LoginAutoCompleteAdapter(LoginActivity.this
                , R.layout.activity_autocomplete_item
                , mDAO.queryAlluser());
        adapter.notifyDataSetChanged();

        get = getSharedPreferences("slideApp", MODE_PRIVATE);
        exit = getSharedPreferences("exit", MODE_PRIVATE);
    }


    /**
     * 验证登录信息
     */
    private void checkLogin() {
        if (exit != null) {
            isExit = exit.getString("exitValue", "").equals("exit");
        }

        //登陆界面判断
        if (!mDAO.isLogin() || !mDAO.isFirstUser()) {
            //当从启动界面跳转过来或在主界面中退出时显示登陆布局
            if (get.getString("slide", "").equals("slide") || isExit) {
                setContentView(R.layout.login_layout);
                ButterKnife.bind(this);
                mLogin.setMode(ActionProcessButton.Mode.PROGRESS);//登陆按钮设置模式

                mRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
                mEditUsername.setAdapter(adapter);

                if (!mDAO.isLogin()) {
                    //没有用户登录时
                    Intent intent = getIntent();
                    //将注册界面中的用户信息取出
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

                    checkUser();

                    loginOperation();

                }

                registerOperation();

            } else {
                //是第一次使用跳转至启动界面
                Intent intent = new Intent(LoginActivity.this, FristSlideAppinfo.class);
                startActivity(intent);
                finish();
            }
        } else if (mDAO.isFirstUser() && mDAO.isLogin()) {
            //当已经登陆 直接进入主界面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(NOTFIRST, 1101);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 注册按钮
     */
    private void registerOperation() {

        mRegister.setOnClickListener(v -> {
            //跳转注册界面
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            mDAO.updateFirst(true);
            startActivity(intent);
            finish();
        });
    }

    /**
     * 登陆按钮
     */
    private void loginOperation() {

        mLogin.setOnClickListener((v) -> {
            hideSoftInput();
            mDAO.updateFirst(true);
            if (mDAO.queryUser(mEditUsername.getText().toString()
                    , mEditPwd.getText().toString())) {
                //用户名和密码正确
                isClick = true;
                progressGenerator.start(mLogin);
                mLogin.setEnabled(false);
            } else if ((mEditUsername.getText().toString().equals("admin")
                    && mEditPwd.getText().toString().equals("12345"))) {
                //教师账号正确
                progressGenerator.start(mLogin);
                mLogin.setEnabled(false);
            } else {
                //都不正确
                mEditPwd.setError("username or password wrong");
                mLogin.setError("ERROR");
            }
        });
    }

    /**
     * 用户框中的逻辑判断
     */
    private void checkUser() {

        mEditPwd.setOnFocusChangeListener((view, isCheck) -> {
            //当框内为空 或不存在输入的用户名时显示提示信息
            if (mEditUsername.getText().toString().isEmpty() ||
                    !mDAO.queryUserName(mEditUsername.getText().toString())) {
                //教师账号
                if (!mEditUsername.getText().toString().equals("admin")) {
                    mEditUsername.setError("username not have");
                    hideSoftInput();
                    mEditPwd.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);//隐藏软键盘
                    mRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }

    /**
     * 沉浸状态栏
     */
    private void setTitlebarColor() {
        //适配4.4系统
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(LoginActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        //初始化登陆按钮
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @Override
    public void onComplete() {

        if ((mEditUsername.getText().toString().equals("admin") && mEditPwd.getText().toString().equals("12345"))) {
            //教师账号正确 跳转至试题添加界面
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            //用户名和密码正确 跳转至主界面
            Intent turnMain = new Intent(LoginActivity.this, MainActivity.class);
            mDAO.updateLogin(true);
            mDAO.updateUserLogin(mEditUsername.getText().toString(), true);
            turnMain.putExtra(FIRSTLOGIN, 1100);
            turnMain.putExtra("user", mEditUsername.getText().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
                startActivity(turnMain, options.toBundle());
                finish();
            } else {
                startActivity(turnMain);
                finish();
            }
        }
    }

    //退场动画
    public void showExit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setReturnTransition(fade);
        }
    }


    //进场动画
    public void setEnterAnmition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Explode explode = new Explode();
            explode.setDuration(1500);
            getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(1500);
            getWindow().setReturnTransition(fade);
        }
    }

    //隐藏软键盘
    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDAO.closeConn();
    }
}
