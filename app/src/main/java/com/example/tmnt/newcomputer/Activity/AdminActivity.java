package com.example.tmnt.newcomputer.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;

public class AdminActivity extends AppCompatActivity {

    @Bind(R.id.singleUpload)
    Button mSingleUpload;

    @Bind(R.id.select_main)
    FloatingActionButton mSelectMain;
    @Bind(R.id.fill_main)
    FloatingActionButton mFillMain;
    @Bind(R.id.mSortAdd)
    FloatingActionMenu mMSortAdd;

    private Snackbar snackbar1;
    public static final String ADD = "add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(AdminActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);

        Bmob.initialize(this, "5b5167d530b5db1c3696b59f02b904bb");

        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        //mMSortAdd.showMenuButton(true);

        Intent intent = new Intent(AdminActivity.this, UploadActivity.class);
        mFillMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ADD, 002);
                startActivity(intent);
            }
        });

        mSelectMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ADD, 001);
                startActivity(intent);
            }
        });


    }

    @OnClick({R.id.singleUpload})
    public void onClick(View view) {
        if (Utils.isWifiConnected(getApplicationContext())) {
            Intent intent = new Intent(AdminActivity.this, UploadActivity.class);
            switch (view.getId()) {
                case R.id.singleUpload:
                    startActivity(intent);
                    break;

            }
        } else {
            snackbar1 = Snackbar.make(view, "you are not open the network,click turn to setting", Snackbar.LENGTH_INDEFINITE).setAction("click", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.openSetting(getApplicationContext());

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
            snackbar1.dismiss();
        }
    }
}