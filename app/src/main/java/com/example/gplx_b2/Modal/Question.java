package com.example.gplx_b2.Modal;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private int id;
    private String question;
    private List<String> answerList;
    private String answerCorrect;
    private int trafficSignsID;

    public Question() {}

    public Question(int id, String question, List<String> answerList, String answerCorrect, int trafficSignsID) {
        this.id = id;
        this.question = question;
        this.answerList = answerList;
        this.answerCorrect = answerCorrect;
        this.trafficSignsID = trafficSignsID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public int getTrafficSignsID() {
        return trafficSignsID;
    }

    public void setTrafficSignsID(int trafficSignsID) {
        this.trafficSignsID = trafficSignsID;
    }
}
