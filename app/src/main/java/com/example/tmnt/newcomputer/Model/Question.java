package com.example.tmnt.newcomputer.Model;

/**
 * 用户选择实体
 * @author tmnt
 *
 */
public class Question {
	private String question;
	private String qselect;
	private String Aselect;
	private String Bselect;
	private String Cselect;
	private String Dselect;
	private int answer;
	public Question(String question,String qselect,int answer){
		this.question=question;
		this.qselect=qselect;
		this.answer=answer;
		answers();
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQselect() {
		return qselect;
	}
	public void setQselect(String qselect) {
		this.qselect = qselect;
	}
	public String getAselect() {
		return Aselect;
	}
	public void setAselect(String aselect) {
		Aselect = aselect;
	}
	public String getBselect() {
		return Bselect;
	}
	public void setBselect(String bselect) {
		Bselect = bselect;
	}
	public String getCselect() {
		return Cselect;
	}
	public void setCselect(String cselect) {
		Cselect = cselect;
	}
	public String getDselect() {
		return Dselect;
	}
	public void setDselect(String dselect) {
		Dselect = dselect;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public void answers(){
		String []b=qselect.split("@");
		Aselect=b[0];
		Bselect=b[1];
		Cselect=b[2];
		Dselect=b[3];
	}
      
}
