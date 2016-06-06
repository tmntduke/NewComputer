package com.example.tmnt.newcomputer.Model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 分类问题实体
 * Created by tmnt on 2016/5/31.
 */
public class AnotherAnswer extends BmobObject implements Serializable {

    private int q_type;//题型类型
    private String question;//题目
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int answer;//答案
    private String kind;//类型


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 问题实体
     *
     * @param
     * @param
     * @param question
     * @param optionA
     * @param optionB
     * @param optionC
     * @param optionD
     * @param answer
     */
    public AnotherAnswer(String question, String optionA, String optionB, String optionC, String optionD,
                         int answer, String kind) {


        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.kind = kind;

    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }


}

