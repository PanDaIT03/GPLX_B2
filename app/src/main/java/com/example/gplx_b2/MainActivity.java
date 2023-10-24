package com.example.gplx_b2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.gplx_b2.DAO.TopicDAO;
import com.example.gplx_b2.DAO.UserAnswerDAO;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Controller.TopicAdapter;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.myInterface.IClickTopicItemListener;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView topicRecyclerView;
    private TopicAdapter topicAdapter;
    private TopicDAO topicDAO;
    private UserAnswerDAO userAnswerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        renderListTopic();
    }

    public void initUI() {
        topicRecyclerView = findViewById(R.id.rcvTopic);
    }

    public void renderListTopic() {
        userAnswerDAO = new UserAnswerDAO();
        List<UserAnswer> userAnswerList = userAnswerDAO.getListUserAnswer();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topicRecyclerView.setLayoutManager(linearLayoutManager);

        topicAdapter = new TopicAdapter(MainActivity.this, getListTopic(), userAnswerList, new IClickTopicItemListener() {
            @Override
            public void onClickItemTopic(Topic topic) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("object_topic", topic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        topicRecyclerView.setAdapter(topicAdapter);
    }

    public List<Topic> getListTopic() {
        topicDAO = new TopicDAO();
        List<Topic> listTopic = topicDAO.getListTopics();

//        set height for recycler view without RecyclerView parent container height as wrap_content
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) topicRecyclerView.getLayoutParams();
        layoutParams.height = listTopic.size() * 355; //315
        topicRecyclerView.setLayoutParams(layoutParams);

        return listTopic;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (topicAdapter != null) {
            topicAdapter.release();
        }
    }
}