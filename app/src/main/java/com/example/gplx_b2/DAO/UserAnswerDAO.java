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

public class UserAnswerDAO {
    Connection connection;

    public UserAnswerDAO() {
    }

    public List<UserAnswer> getListUserAnswer() {
        List<UserAnswer> userAnswerList = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "SELECT * FROM view_selectUserAnswerQuantity";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    UserAnswer UserAnswer = new UserAnswer();

                    UserAnswer.setTitle(rs.getString("title"));
                    UserAnswer.setQuantity(rs.getInt("quantity"));

                    userAnswerList.add(UserAnswer);
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return userAnswerList;
    }
}
