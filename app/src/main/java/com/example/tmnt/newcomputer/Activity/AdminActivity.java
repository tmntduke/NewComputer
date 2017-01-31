package com.example.tmnt.newcomputer.Activity;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tmnt.newcomputer.BMOB.BmobUtils;
import com.example.tmnt.newcomputer.InterFace.IMPL.MaxIdIMPL;
import com.example.tmnt.newcomputer.InterFace.IMaxId;
import com.example.tmnt.newcomputer.MainActivity;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.ParseJson;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * 教师选择添加方式界面
 */
public class AdminActivity extends AppCompatActivity {

    @Bind(R.id.singleUpload)
    Button mSingleUpload;

    @Bind(R.id.select_main)
    FloatingActionButton mSelectMain;
    @Bind(R.id.fill_main)
    FloatingActionButton mFillMain;
    @Bind(R.id.mSortAdd)
    FloatingActionMenu mMSortAdd;
    @Bind(R.id.fileUpload)
    Button mFileUpload;

    private boolean isReturn;
    private Snackbar snackbar1;
    public static final String ADD = "add";

    public static final int REQUEST_CODE = 1001;
    private static final String TAG = "AdminActivity";
    public String getFilePath;
    private int idForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //适配4.4系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(AdminActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);

        Bmob.initialize(this, "4556f6a1fe01d72ebe7e4c62e41d381c");

        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        //mMSortAdd.showMenuButton(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("教师端");
        //进入添加题目界面
        Intent intent = new Intent(AdminActivity.this, UploadActivity.class);

        //浮动按钮--填空添加
        mFillMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ADD, 002);
                startActivity(intent);
            }
        });

        //浮动按钮--选择添加
        mSelectMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ADD, 001);
                startActivity(intent);
            }
        });


        mFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("file/*");
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @OnClick({R.id.singleUpload})
    public void onClick(View view) {

        //当连接wifi时进入添加界面
        if (Utils.isWifiConnected(getApplicationContext())) {
            Intent intent = new Intent(AdminActivity.this, UploadActivity.class);
            switch (view.getId()) {
                case R.id.singleUpload:
                    startActivity(intent);
                    break;

            }
        } else {
            //没有连接时 提示 并进入设置界面
            snackbar1 = Snackbar.make(view, "you are not open the network,click turn to setting"
                    , Snackbar.LENGTH_INDEFINITE).setAction("click", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.openNetworkSetting(getApplicationContext());

                }
            });
            snackbar1.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar1.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar1.show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (snackbar1 != null) {
            snackbar1.dismiss();//将snackbar取消
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BmobUtils.maxIdToBmob(this);
        MaxIdIMPL.setMaxId(new IMaxId() {
            @Override
            public void maxId(int id) {
                idForm = id;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isReturn) {
            ParseJson.getParseData(getFilePath, new ParseJson.CallBack() {
                @Override
                public void onSuccess(List<AnotherAnswer> list) {
                    BmobUtils.addForMore(list, AdminActivity.this, idForm);
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "onError: " + e.toString());
                    //Utils.showToast(AdminActivity.this, "文件加载失败");
                    Snackbar.make(getWindow().findViewById(R.id.fileUpload), "文件加载失败", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(data.getData(), null, null, null, null, null);
            cursor.moveToNext();
            getFilePath = cursor.getString(cursor.getColumnIndex("_data"));
            isReturn = true;
        }
    }
}
