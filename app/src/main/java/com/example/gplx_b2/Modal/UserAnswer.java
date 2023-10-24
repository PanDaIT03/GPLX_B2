package com.example.gplx_b2.Modal;

public class UserAnswer {
    private String answer, title;
    private int questionID, quantity;

    public UserAnswer(String answer, String title, int questionID, int quantity) {
        this.answer = answer;
        this.title = title;
        this.questionID = questionID;
        this.quantity = quantity;
    }

    public UserAnswer() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}
