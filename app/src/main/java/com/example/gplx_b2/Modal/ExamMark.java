package com.example.gplx_b2.Modal;

public class ExamMark {
    private int id;
    private String question;
    private String answerCorrect;
    private String answer;

    public ExamMark() {
    }

    public ExamMark(int id, String question, String answerCorrect, String answer) {
        this.id = id;
        this.question = question;
        this.answerCorrect = answerCorrect;
        this.answer = answer;
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

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
