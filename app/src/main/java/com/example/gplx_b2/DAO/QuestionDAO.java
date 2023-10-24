package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public QuestionDAO() {
    }

    Connection connection;

    public List<Question> getListQuestions(String topic) {
        List<Question> questionList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_selectQuestionFromTopic(?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setString(1, topic);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    Question question = new Question();
                    List<String> answerList = new ArrayList<>();

                    question.setId(rs.getInt("id"));
                    question.setQuestion(rs.getString("question"));
                    question.setAnswerCorrect(rs.getString("answer_correct"));
                    question.setTrafficSignsID(rs.getInt("traffic_signs_id"));

                    answerList.add(rs.getString("answer_a"));
                    answerList.add(rs.getString("answer_b"));
                    answerList.add(rs.getString("answer_c"));
                    answerList.add(rs.getString("answer_d"));

                    question.setAnswerList(answerList);
                    questionList.add(question);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return questionList;
    }

    public int insertUserAnswer(String answer, int questionID) {
        int rec = 0;
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_insertUserAnswer(?, ?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                preparedStatement.setString(1, answer);
                preparedStatement.setInt(2, questionID);

                rec = preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return rec;
    }

    public List<UserAnswer> getListUserAnswer() {
        List<UserAnswer> userAnswerList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "SELECT id, answer, questions_id FROM user_answer";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    UserAnswer userAnswer = new UserAnswer();

                    userAnswer.setAnswer(rs.getString("answer"));
                    userAnswer.setQuestionID(rs.getInt("questions_id"));

                    userAnswerList.add(userAnswer);
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return userAnswerList;
    }

    public String getImageFileName(int imgID) {
        String name = null;
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_getImageName(?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, imgID);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next())
                    name = rs.getString("name");

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return name;
    }
}
