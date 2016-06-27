package com.example.tmnt.newcomputer.InterFace.IMPL;

import com.example.tmnt.newcomputer.InterFace.IMaxId;

/**
 * 获取最大id
 * Created by tmnt on 2016/6/21.
 */
public class MaxIdIMPL {
    public static IMaxId sIMaxId;

    public static void setMaxId(IMaxId iMaxId) {
        sIMaxId = iMaxId;
    }
}
