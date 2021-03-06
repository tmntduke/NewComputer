package com.example.tmnt.newcomputer.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Finallay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by tmnt on 2016/5/7.
 * 数据库连接
 */
public class DBHelper extends SQLiteOpenHelper {


    private Context mContext;
    private static final int VERSION = 1;
    private static final String DBNAME = "My.db";

    private static final String CREATE = "create table T_User (uid integer PRIMARY KEY AUTOINCREMENT" +
            ", username varchar (10),password varchar (20)" +
            ",isLogin bool,isIcon bool)";

    private final static String QUESTION = "create table T_Question (jId integer primary key AUTOINCREMENT" +
            ",question varchar(200),answerA varchar(25),answerB varchar(25),answerC varchar(25)" +
            ",answerD varchar(25),answer integer,kind varchar(15))";

    private static final String FIRST = "create table T_First(fid integer primary key AUTOINCREMENT " +
            ", isFirst bool ,isLogin bool )";

    private final static String INS_FIRST = "insert into T_First(isFirst,isLogin) values(0,0)";

    private final static String WRONG = "create table T_Wrong (wid integer primary key AUTOINCREMENT" +
            ",question text,answerA varchar(25),answerB varchar(25),answerC varchar(25)" +
            ",answerD varchar(25),answer integer,kind integer)";

    private final static String UESER_ICON = "create table T_UserIcon(Iid integer primary key AUTOINCREMENT" +
            ",username varchar(25),iconPath varchar(200))";

    private final static String COUNT = "create table T_Count(cid integer primary key AUTOINCREMENT" +
            ",count integer,bmobCount integer)";

    private final static String INS_COUNT = "insert into T_Count(count,bmobCount) values (0,0)";

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE);
        db.execSQL(QUESTION);
        db.execSQL(FIRST);
        db.execSQL(INS_FIRST);
        db.execSQL(WRONG);

        db.execSQL(UESER_ICON);

        db.execSQL(COUNT);
        db.execSQL(INS_COUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion == 10) {
            db.execSQL("alter table copy add fillAnswer varchar(30) ");
            db.execSQL("alter table copy add objectId varchar(30) ");
        }


        if (newVersion == 20) {
            db.execSQL("alter table T_Wrong add objectId varchar(30) ");
        }
    }

    /**
     * 在sd卡创建数据库
     */
    public void OpenDataBase() {
        if (backEnvironment()) {// 判断sd卡是否挂载
            File file = new File(Finallay.FILE_PAPER_PATH);// 建立sd卡路径
            if (!file.exists()) {
                file.mkdirs();
                //System.out.println("sd卡路径创建文件夹");

            } else {
                //System.out.println("sd已有");

            }
            File filePath = new File(Finallay.FILE_PATH);// 建立sd卡下 的db文件
            if (!filePath.exists()) {
                InputStream in = mContext.getResources().openRawResource(
                        R.raw.examofcomputer);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            Finallay.FILE_PATH);// 得到输出流 文件夹下文件路径
                    byte[] buffer = new byte[8192];
                    int t = 0;
                    while ((t = in.read(buffer)) != -1) {// 半读边写
                        fileOutputStream.write(buffer, 0, t);
                    }
                    in.close();
                    fileOutputStream.close();

                } catch (Exception e) {
                    //System.out.print("write error");

                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(mContext, "您的sd卡没有挂载", Toast.LENGTH_LONG).show();
        }

    }

    public boolean backEnvironment() {// 判断sd卡是否挂载
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }
}
