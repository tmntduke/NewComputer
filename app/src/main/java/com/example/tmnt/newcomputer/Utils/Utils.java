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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.InterFace.IMPL.ShowIcon;
import com.example.tmnt.newcomputer.InterFace.OnClickShowIcon;
import com.example.tmnt.newcomputer.R;

import java.io.InputStream;

/**
 * Created by tmnt on 2016/5/26.
 */
public class Utils {

    private static final String TAG = "Utils";


    public static void showToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }

//    public static FragmentStatePagerAdapter getFragmentAdater(FragmentManager manager, ArrayList<Questions> list, int flag) {
//        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(manager) {
//            @Override
//            public Fragment getItem(int i) {
//                if (flag == 1) {
//                    Random random = new Random();
//                    int p = random.nextInt(150);
//                    //Log.i(TAG, "getItem: " + i + p);
//                    return AnswerFragment.newInstance(list, i + p, list.get(i + p).getMexam_type(), flag);
//                } else if (flag == 2) {
////                    Random random = new Random();
////                    int p = random.nextInt(20);
////                    //Log.i(TAG, "getItem: " + i + p);
//                    return AnswerFragment.newInstance(list, i, list.get(i).getMexam_type(), flag);
//                } else {
//                    return AnswerFragment.newInstance(list, i, list.get(i).getMexam_type(), flag);
//                }
//
//            }
//
//            @Override
//            public int getCount() {
//                {
//                    return list.size();
//                }
//
//            }
//        };
//
//        return adapter;
//    }

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
    public static void openSetting(Context context) {

        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    public static boolean isMobileConnected(Context context) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return mMobileNetworkInfo.isAvailable();
    }

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


    public static AlertDialog shoeDialog(Context context, String s, QuestionDAO dao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_icon_lay, null, false);
        builder.setView(view);
        TextView t = (TextView) view.findViewById(R.id.dialog_icon);
        Button camera = (Button) view.findViewById(R.id.camera);
        ImageView imageView = (ImageView) view.findViewById(R.id.setIcon);
        Button gallery = (Button) view.findViewById(R.id.gallery);
        t.setText(s);

        if (dao.queryUserIcon(s)) {
            imageView.setImageBitmap(ImageUtils.readBitMap(context, dao.queryUserIconPath(s)));
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowIcon.mOnClickShowIcon != null) {
                    ShowIcon.mOnClickShowIcon.toCamera();
                }
            }
        });

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
