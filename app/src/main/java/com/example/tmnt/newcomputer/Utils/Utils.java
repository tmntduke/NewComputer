package com.example.tmnt.newcomputer.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Fragment.AnswerFragment;
import com.example.tmnt.newcomputer.InterFace.IMPL.ShowIcon;
import com.example.tmnt.newcomputer.InterFace.OnClickShowIcon;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tmnt on 2016/5/26.
 */
public class Utils {

    private static final String TAG = "Utils";


    /**
     * 显示Toast
     *
     * @param context
     * @param title
     */
    public static void showToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_LONG).show();
    }

    /**
     * 创建ViewPager的适配器
     *
     * @param manager
     * @param list
     * @param flag
     * @return
     */
    public static FragmentStatePagerAdapter getFragmentAdater(FragmentManager manager, ArrayList<Questions> list, int flag) {
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int i) {
                if (flag == 2) {
                    Random random = new Random();
                    int p = random.nextInt(150);
                    //Log.i(TAG, "getItem: " + i + p);
                    return AnswerFragment.newInstance(list, i + p, list.get(i + p).getMexam_type(), flag, 0);
                } else if (flag == 3) {
//                    Random random = new Random();
//                    int p = random.nextInt(20);
//                    //Log.i(TAG, "getItem: " + i + p);
                    Log.i(TAG, "getItem: " + (i - 1) * 5);
                    return AnswerFragment.newInstance(list, i, list.get(i).getMexam_type(), flag, i * 24);
                } else if (flag == 4 && list.size() != 0) {
                    return AnswerFragment.newInstance(list, i, list.get(i).getMexam_type(), flag, 0);
                } else if (flag == 4 && list.size() == 0) {
                    return AnswerFragment.newInstance(list, 0, 0, 0, 0);
                } else {
                    return AnswerFragment.newInstance(list, i, list.get(i).getMexam_type(), flag, 0);
                }

            }

            @Override
            public int getCount() {
                {
                    if (list.size() != 0) {
                        return list.size();
                    } else {
                        return 1;
                    }

                }

            }
        };

        return adapter;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    /**
     * 判断网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkStatus(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = Constants.getNetWorkClass(context);
            }
        }
        return netWorkType;
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetworkSetting(Context context) {

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开文件管理器
     *
     * @param context
     */
    public static void openContent(Context context) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("file/*");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static boolean isMobileConnected(Context context) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return mMobileNetworkInfo.isAvailable();
    }

    /**
     * 适配4.4系统
     *
     * @param activity
     * @param b
     */
    public static void setTranslucentStatus(Activity activity, boolean b) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 显示Dialog
     *
     * @param context
     * @param s
     * @param dao
     * @return
     */
    public static AlertDialog shoeDialog(Context context, String s, QuestionDAO dao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_icon_lay, null, false);
        builder.setView(view);
        TextView t = (TextView) view.findViewById(R.id.dialog_icon);
        Button camera = (Button) view.findViewById(R.id.camera);
        ImageView imageView = (ImageView) view.findViewById(R.id.setIcon);
        Button gallery = (Button) view.findViewById(R.id.gallery);
        t.setText(s);

        //设置头像
        if (dao.queryUserIcon(s)) {
            imageView.setImageBitmap(ImageUtils.readBitMap(context, dao.queryUserIconPath(s)));
        }

        //点击拍照
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowIcon.mOnClickShowIcon != null) {
                    ShowIcon.mOnClickShowIcon.toCamera();
                }
            }
        });

        //点击相册
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowIcon.mOnClickShowIcon != null) {
                    ShowIcon.mOnClickShowIcon.toGallery();
                }
            }
        });

        AlertDialog dialog = builder.create();
        //dialog.show();
        return dialog;
    }


}
