package com.example.tmnt.newcomputer.Model;

public class Questions  {
    private int _id;
    private int q_type;//题型类型
    private String question;//题目
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int answer;//答案
    private int mexam_type;//类型
    private byte[] blob;//图片

    /**
     * 问题实体
     *
     * @param _id
     * @param q_type
     * @param question
     * @param optionA
     * @param optionB
     * @param optionC
     * @param optionD
     * @param answer
     * @param mexam_type
     * @param blob
     */
    public Questions(int _id, int q_type, String question, String optionA, String optionB, String optionC, String optionD,
                     int answer, int mexam_type, byte[] blob) {
        super();
        this._id = _id;
        this.q_type = q_type;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.mexam_type = mexam_type;
        this.blob = blob;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        _id = id;
    }

    public int getQ_type() {
        return q_type;
    }

    public void setQ_type(int qType) {
        q_type = qType;
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

    public int getMexam_type() {
        return mexam_type;
    }

    public void setMexam_type(int mexamType) {
        mexam_type = mexamType;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

}
