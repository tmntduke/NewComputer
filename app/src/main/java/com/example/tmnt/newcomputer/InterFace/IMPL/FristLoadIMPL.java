package com.example.tmnt.newcomputer.InterFace.IMPL;

import com.example.tmnt.newcomputer.InterFace.IFristLoad;

/**
 * 第一次启动应用
 * Created by tmnt on 2016/6/16.
 */
public class FristLoadIMPL {

    public static IFristLoad mIFristLoad;

    public static void setFirstLoad(IFristLoad iFristLoad) {
        mIFristLoad = iFristLoad;
    }
}
