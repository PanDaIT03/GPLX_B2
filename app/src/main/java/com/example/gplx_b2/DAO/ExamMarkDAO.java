package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.ExamMark;
import com.example.gplx_b2.Modal.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExamMarkDAO {
    public ExamMarkDAO() {
    }
    Connection connection;

    public List<ExamMark> getUserAnswerFromExam(String exam) {
        List<ExamMark> examMarkList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_selectUserAnswer(?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setString(1, exam);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    ExamMark examMark = new ExamMark();

                    examMark.setId(rs.getInt("id"));
                    examMark.setQuestion(rs.getString("question"));
                    examMark.setAnswerCorrect(rs.getString("answer_correct"));
                    examMark.setAnswer(rs.getString("answer"));

                    examMarkList.add(examMark);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return examMarkList;
    }

    public int insertScore(float score, int userID, int examID) {
        int rec = 0;
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_insertScore(?, ?, ?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                preparedStatement.setFloat(1, score);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, examID);

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

    public List<Integer> getScore() {
        List<Integer> scoreList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "SELECT score FROM scores";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    int score = rs.getInt("score");

                    scoreList.add(score);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return scoreList;
    }
}
