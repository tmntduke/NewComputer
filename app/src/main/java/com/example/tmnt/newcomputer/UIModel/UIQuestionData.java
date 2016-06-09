package com.example.tmnt.newcomputer.UIModel;

/**
 * Created by tmnt on 2016/6/6.
 */
public class UIQuestionData {

    private int resIdIcon;
    private String titleData;
    private String subtitleData;

    public UIQuestionData(int resIdIcon, String titleData, String subtitleData) {
        this.resIdIcon = resIdIcon;
        this.titleData = titleData;
        this.subtitleData = subtitleData;
    }

    public int getResIdIcon() {
        return resIdIcon;
    }


    public String getTitleData() {
        return titleData;
    }

    public void setTitleData(String titleData) {
        this.titleData = titleData;
    }

    public String getSubtitleData() {
        return subtitleData;
    }

    public void setSubtitleData(String subtitleData) {
        this.subtitleData = subtitleData;
    }
}
