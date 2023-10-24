package com.example.gplx_b2.DAO;

import android.util.Log;

import com.example.gplx_b2.Database.ConnSQL;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    public TopicDAO() {
    }

    Connection connection;

    public List<Topic> getListTopics() {
        List<Topic> listTopic = new ArrayList<>();
        ConnSQL connSql = new ConnSQL();

        connection = connSql.conn();
        if (connection != null) {
            try {
                String sqlStatement = "SELECT * FROM view_selectTopic";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);

                while (rs.next()) {
                    Topic topic = new Topic();

                    topic.setId(rs.getInt("id"));
                    topic.setImage(rs.getString("imageFileName"));
                    topic.setTitle(rs.getString("title"));
                    topic.setQuantity(rs.getInt("quantity"));
                    topic.setSubTitle(rs.getString("quantity"));

                    listTopic.add(topic);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connect failure!");
        }

        return listTopic;
    }
}
