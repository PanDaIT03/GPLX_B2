package com.example.gplx_b2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gplx_b2.Controller.ExamAdapter;
import com.example.gplx_b2.DAO.ExamDAO;
import com.example.gplx_b2.DAO.ExamMarkDAO;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Exam;
import com.example.gplx_b2.Modal.ExamMark;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;
import com.example.gplx_b2.myInterface.IClickExamItemListener;

import java.util.List;

public class ExamActivity extends AppCompatActivity {
    Context context = this;
    private RecyclerView examRecyclerView;
    private TextView tvBack;
    private ExamAdapter examAdapter;
    private ExamDAO examDAO;
    private ExamMarkDAO examMarkDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        initUI();
        renderListExam();

        tvBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        examRecyclerView = findViewById(R.id.rcvExam);

        tvBack = findViewById(R.id.tvBack);
    }

    private List<Exam> getListExam() {
        examDAO = new ExamDAO();
        List<Exam> examList = examDAO.getListExam();

        return examList;
    }

    private List<Integer> getScore() {
        examMarkDAO = new ExamMarkDAO();
        return examMarkDAO.getScore();
    }

    private void renderListExam() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        examRecyclerView.setLayoutManager(linearLayoutManager);

        examAdapter = new ExamAdapter(context, getListExam(), getScore(), new IClickExamItemListener() {
            @Override
            public void onClick(Exam exam) {
                Intent intent = new Intent(ExamActivity.this, ExamActivitySecond.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("exam_object", exam);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        examRecyclerView.setAdapter(examAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (examAdapter != null)
            examAdapter.release();
    }
}