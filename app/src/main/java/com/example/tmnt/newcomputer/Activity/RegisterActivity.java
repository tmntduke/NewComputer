package com.example.tmnt.newcomputer.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Widget.AutoComplete;
import com.example.tmnt.newcomputer.InterFace.IMPL.ShowIcon;
import com.example.tmnt.newcomputer.InterFace.OnClickShowIcon;
import com.example.tmnt.newcomputer.MainActivity;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ImageUtils;
import com.example.tmnt.newcomputer.Utils.ProgressGenerator;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * 注册界面
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

    private static final String TAG = "RegisterActivity";

    private String s;

    private QuestionDAO mDAO;

    private AlertDialog mAlertDialog;

    private boolean isUsername, isPassword;
    private static String TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "image/jpg";
    private int x, y;

    private boolean isCamera, isGaerlly;
    private int i = 1;


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
        mDAO = QuestionDAO.getInstance(getApplicationContext());

        mRegisterBtn.setMode(ActionProcessButton.Mode.PROGRESS);


        //mRegisterBtn.setProgress(100);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegisterPassword.setOnFocusChangeListener((view, isCheck) -> {
            String regex = "\\w{4,8}";//用户信息验证
            //当不匹配时显示提示信息
            if (!(mRegisterUsername.getText().toString().matches(regex))
                    || (mRegisterUsername.getText().toString().isEmpty())) {
                mRegisterUsername.setError("username illegimate");
            } else if (mDAO.queryUserName(mRegisterUsername.getText().toString())) {
                //已存在用户名
                mRegisterUsername.setError("username already have");

//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                hideSoftInput();
                mRegisterPassword.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);//隐藏软键盘

                //显示提示信息 并设置跳转至登陆界面的按钮
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
                snackbar.getView().setBackgroundColor(getResources()
                        .getColor(R.color.colorPrimary));//设置SnackBar颜色
                snackbar.setActionTextColor(Color.rgb(127, 256, 169));
                snackbar.show();

            } else {
                isUsername = true;
//                mRegisterIcon.setVisibility(View.VISIBLE);
//                mIconShow.setVisibility(View.VISIBLE);
//                if ((!isCamera || !isGaerlly) && s == null) {
//                    Utils.showToast(getApplicationContext(), "请先设置头像");
//                    mRegisterPassword.setEnabled(false);
//                    mRegisterPasswordCommit.setEnabled(false);
//                    hideSoftInput();
//                    //mRegisterPassword.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);//隐藏软键盘
            }

        });

        //密码框判断
        mRegisterPasswordCommit.setOnFocusChangeListener((view, isCheck) -> {
            String regex = "(?=.*\\d)(?=.*[a-zA-Z]).{8,10}";//密码验证

            //当密码不匹配时
            if (!(mRegisterPassword.getText().toString().matches(regex))
                    || (mRegisterPassword.getText().toString().isEmpty())) {
                mRegisterPassword.setError("password illegimate");

            } else {
                isPassword = true;
            }
        });

//        Random random = new Random();
//        int a = random.nextInt(100);
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);

        //注册按钮
        mRegisterBtn.setOnClickListener((v) ->
                {
                    //用户名和密码不符合要求
                    if (!(mRegisterPasswordCommit.getText().toString()
                            .equals(mRegisterPassword.getText().toString()))
                            || (mRegisterPasswordCommit.getText().toString().isEmpty())
                            || mRegisterMail.getText().toString().isEmpty()) {

                        mRegisterPasswordCommit.setError("password not identical");

                    } else if ((mRegisterPasswordCommit.getText().toString()
                            .equals(mRegisterPassword.getText().toString())
                            && isUsername && isPassword)) {
//
                        isOver = true;
                        progressGenerator.start(mRegisterBtn);
                        mRegisterBtn.setEnabled(false);

                    }
                }
        );


        ShowIcon.setOnClickShowIcon(new OnClickShowIcon() {
            @Override
            public void toGallery() {
                ImageUtils.toGallery(MainActivity.RESULT_IMAGE, RegisterActivity.this);
            }

            @Override
            public void toCamera() {
                ImageUtils.toCamera(RegisterActivity.this, TEMP_IMAGE_PATH, MainActivity.RESULT_CAMERA);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (isGaerlly || isCamera) {
//
//            mRegisterPassword.setEnabled(true);
//            mRegisterPasswordCommit.setEnabled(true);
//            showSoftInput(mRegisterPassword);
//            //mRegisterPassword.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
//
//
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mDAO.closeConn();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: start");
        if (resultCode == RESULT_OK) {
            if (requestCode == MainActivity.RESULT_IMAGE && data != null) {
                isCamera = true;
                s = ImageUtils.getImageFromGallery(RegisterActivity.this, data);

//                if (!mDAO.queryUserIcon(mRegisterUsername.getText().toString())) {
//                    mDAO.addUserIcon(mRegisterUsername.getText().toString(), s);
//                } else {
//                    mDAO.updateUserIcon(mRegisterUsername.getText().toString(), s);
//                }
//                mDAO.updateUserIconFlag(mRegisterUsername.getText().toString(), true);
            } else if (requestCode == MainActivity.RESULT_CAMERA) {
                isGaerlly = true;
//                mDAO.addUserIcon(mRegisterUsername.getText().toString(), TEMP_IMAGE_PATH);
                //mDAO.updateUserIconFlag(mRegisterUsername.getText().toString(), true);


            }
        }
    }

    @Override
    public void onComplete() {
        //验证完成后进入登陆
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        mDAO.addUser(mRegisterUsername.getText().toString(), mRegisterPassword.getText().toString());

        //intent.putExtra("username", mRegisterUsername.getText().toString());
        intent.putExtra("username", mRegisterUsername.getText().toString());
        intent.putExtra("password", mRegisterPasswordCommit.getText().toString());
        startActivity(intent);
        finish();


    }


    /**
     * 显示软键盘
     *
     * @param edit
     */
    public void showSoftInput(EditText edit) {
//        edit.setFocusable(true);
//        edit.setFocusableInTouchMode(true);
//        edit.requestFocus();
        View view = getWindow().peekDecorView();
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}



