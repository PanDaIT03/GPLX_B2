package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {
    public ExamDAO() {
    }

    Connection connection;

    public List<Exam> getListExam() {
        List<Exam> examList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "SELECT * FROM exams";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    Exam exam = new Exam();

                    exam.setId(rs.getInt("id"));
                    exam.setName(rs.getString("name"));

                    examList.add(exam);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return examList;
    }

    public int insertUserAnswer(String answer, int userID, int questionID, int examID) {
        int rec = 0;
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "{CALL sp_insertUserAnswer(?, ?, ?, ?)}";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                preparedStatement.setString(1, answer);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, questionID);
                preparedStatement.setInt(4, examID);

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
}
