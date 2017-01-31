package com.example.tmnt.newcomputer.Utils;

import android.os.Environment;
import android.util.Log;

import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by tmnt on 2016/7/15.
 */
public class ParseJson {

    public static List<AnotherAnswer> parswJson(String path) {

        File file = new File(path);

        ByteArrayOutputStream bos = null;
        try {
            InputStream in = new FileInputStream(file);
            //FileInputStream in = openFileInput("question.json");
            bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = in.read(data)) != -1) {
                bos.write(data, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        String ss = new String(bos.toByteArray(), 0, bos.size());

        List<AnotherAnswer> list = new Gson().fromJson(ss, new TypeToken<List<AnotherAnswer>>() {
        }.getType());

        return list;
    }


    public static void getParseData(String file, CallBack callBack) {

        try {
            List<AnotherAnswer> list = parswJson(file);
            if (callBack != null) {
                callBack.onSuccess(list);
            }
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

    }

    public interface CallBack {
        void onSuccess(List<AnotherAnswer> list);

        void onError(Exception e);
    }
}
