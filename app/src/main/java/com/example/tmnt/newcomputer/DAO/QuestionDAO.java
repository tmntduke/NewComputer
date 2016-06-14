package com.example.tmnt.newcomputer.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.Utils.Finallay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmnt on 2016/5/7.
 * 数据库操作
 */
public class QuestionDAO {
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase db, createFromSD;

    public QuestionDAO(Context mContext) {
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
        mDBHelper.OpenDataBase();
        createFromSD = SQLiteDatabase.openOrCreateDatabase(Finallay.FILE_PATH, null);
    }


    // 关闭连接
    private void closeConn() {
        if (db != null) {
            db.close();
            db = null;
        }
    }


    /**
     * 更新第一次使用信息
     *
     * @param isFirst
     */
    public void updateFirst(boolean isFirst) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isFirst", isFirst);
        db.update("T_First", values, "fid=1", null);
    }


    /**
     * 查询是否是第一次使用
     *
     * @return
     */
    public boolean isFirstUser() {
        int flag = 0;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_First", new String[]{"isFirst"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            flag = cursor.getInt(0);
        }
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新登陆信息
     *
     * @param isLogin
     */
    public void updateLogin(boolean isLogin) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isLogin", isLogin);
        db.update("T_First", values, "fid=1", null);
    }


    /**
     * 查询是否为第一次登陆
     *
     * @return
     */
    public boolean isLogin() {
        int flag = 0;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_First", new String[]{"isLogin"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            flag = cursor.getInt(0);
        }
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 添加用户
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void addUser(String username, String password) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.insert("T_User", "uid", values);
    }

    /**
     * 查询用户是否存在
     *
     * @param username
     * @param password
     * @return
     */
    public boolean queryUser(String username, String password) {
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_User", new String[]{"username", "password"}, "username=? and password=?", new String[]{username, password}, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询用户名是否存在
     *
     * @param name
     * @return
     */
    public boolean queryUserName(String name) {
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_User", new String[]{"username"}, "username=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将错误的添加到数据库
     *
     * @param questions
     */
    public void addWrong(Questions questions) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question", questions.getQuestion());
        values.put("answerA", questions.getOptionA());
        values.put("answerB", questions.getOptionB());
        values.put("answerC", questions.getOptionC());
        values.put("answerD", questions.getOptionD());
        values.put("answer", questions.getAnswer());
        values.put("kind", questions.getMexam_type());
        db.insert("T_Wrong", "wid", values);

    }

    /**
     * 查询所有错误问题
     *
     * @return
     */
    public ArrayList<Questions> queryAllWrong() {
        ArrayList<Questions> list = new ArrayList<>();
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_Wrong", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new Questions(cursor.getInt(0), 0, cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), null));
        }

        return list;
    }

    /**
     * 查询所有错误问题的题目
     *
     * @return
     */
    public ArrayList<String> queryWrongQuestion() {
        ArrayList<String> list = new ArrayList<>();
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_Wrong", new String[]{"question"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        return list;
    }

    /**
     * 查询登陆用户名
     *
     * @return
     */
    public String queryLoginUsername() {
        String name = null;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_User", new String[]{"username"}, "isLogin=?", new String[]{String.valueOf(1)}, null, null, null);
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        return name;
    }

    /**
     * 查询所有用户名
     *
     * @return
     */
    public List<String> queryAlluser() {
        List<String> list = new ArrayList<>();
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_User", new String[]{"username"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }

        return list;
    }

    /**
     * 更新登陆信息
     *
     * @param username
     * @param isLogin
     */
    public void updateUserLogin(String username, boolean isLogin) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isLogin", isLogin);
        db.update("T_User", values, "username=?", new String[]{username});
    }

    /**
     * 全部考题
     *
     * @return
     */
    public ArrayList<Questions> queryAll() {
        ArrayList<Questions> arrayList = new ArrayList<Questions>();

        Cursor cursor = createFromSD.query(Finallay.TABLE_NAME, null, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            arrayList.add(new Questions(cursor.getInt(0), cursor.getInt(1),
                    cursor.getString(2), cursor.getString(3), cursor
                    .getString(4), cursor.getString(5), cursor
                    .getString(6), cursor.getInt(7), cursor.getInt(8),
                    cursor.getBlob(9)));
        }
        cursor.close();
        return arrayList;
    }


    /**
     * 像数据库中添加用户头像
     *
     * @param username 用户名
     * @param path     头像路径
     */
    public void addUserIcon(String username, String path) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("iconPath", path);
        db.insert("T_UserIcon", "Iid", values);
    }

    /**
     * 更新用户头像路径
     *
     * @param username
     * @param path
     */
    public void updateUserIcon(String username, String path) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("iconPath", path);
        db.update("T_UserIcon", values, "username=?", new String[]{username});
    }

    /**
     * 查询用户是否设置图头像
     *
     * @param name
     * @return
     */
    public boolean queryUserIcon(String name) {
        int flag = 0;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_User", new String[]{"isIcon"}, "username=?", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            flag = cursor.getInt(0);
        }
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新用户是否设置头像
     *
     * @param username
     * @param isIcon
     */
    public void updateUserIconFlag(String username, boolean isIcon) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isIcon", isIcon);
        db.update("T_User", values, "username=?", new String[]{username});
    }


    /**
     * 查询用户头像路径
     *
     * @param name
     * @return
     */
    public String queryUserIconPath(String name) {
        String path = null;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_UserIcon", new String[]{"iconPath"}, "username=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            path = cursor.getString(0);
        }

        return path;
    }


    /**
     * 获取模拟考试次数
     *
     * @return
     */
    public int queryModelCount() {
        int count = 0;
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("T_Count", new String[]{"count"}, "cid=1", null, null, null, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public void updateModelCount(int count) {
        db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("count", count);
        db.update("T_Count", values, "cid=1", null);
    }


    /**
     * 删除错误题目
     *
     * @param question
     */
    public void deleteWrong(String question) {
        db = mDBHelper.getWritableDatabase();
        db.delete("T_Wrong", "question=?", new String[]{question});
    }

    /**
     * 根据题目查找
     *
     * @param question
     * @return
     */
    public Questions queryWrongQuestions(String question) {
//        byte[] bytes = question.getBytes();
//        String s = null;
//        try {
//            s = new String(bytes, "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Questions questions = null;
        db = mDBHelper.getReadableDatabase();
        //Cursor cursor = db.query("T_Wrong", null, "question=?", new String[]{question}, null, null, null, null);
        Cursor cursor = db.rawQuery("select * from T_Wrong where question=?", new String[]{question});

        if (cursor.moveToNext()) {
            questions = new Questions(cursor.getInt(0), 0, cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), null);
        }
        return questions;

    }

}
