package com.example.gplx_b2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Controller.ExamQuestionAdapter;
import com.example.gplx_b2.Controller.ViewPagerAdapter;
import com.example.gplx_b2.DAO.ExamMarkDAO;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.DAO.UserAnswerDAO;
import com.example.gplx_b2.Modal.Exam;
import com.example.gplx_b2.Modal.ExamMark;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.myInterface.IClickBottomSheetDialogItemListener;
import com.example.gplx_b2.myInterface.IClickExamQuesItemListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.Objects;

public class ExamActivitySecond extends AppCompatActivity {
    private float mark = 10;
    private Exam exam;
    private QuestionDAO questionDAO;
    private ExamMarkDAO examMarkDAO;
    private ViewPager2 vpgExQuestion;
    private TextView tvExTitle, tvExNumber, tvExBack, tvExSubmit, tvExPrev, tvExNext, tvExShowAll;
    private LinearLayout lnBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView questionBottomSheetRecyclerView, rcvExQuestion;
    private ExamQuestionAdapter examQuestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_second);

        initUI();
        exam = getData();

        Log.d("exam_name", exam.getName());
        tvExTitle.setText(exam.getName());

        List<Question> questionList = getListQuestionFromExam(exam.getName());
        renderQuestionPager(questionList);
        renderListExamQuestion(questionList);

        int length = questionList.size();
        initData(1, length);
        vpgExQuestion.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    tvExPrev.setClickable(false);
                } else if (position == length - 1) {
                    tvExNext.setClickable(false);
                } else {
                    tvExPrev.setClickable(true);
                    tvExNext.setClickable(true);
                }
                initData(position + 1, length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        tvExPrev.setOnClickListener(view ->
                vpgExQuestion.setCurrentItem(vpgExQuestion.getCurrentItem() - 1)
        );
        tvExNext.setOnClickListener(view ->
                vpgExQuestion.setCurrentItem(vpgExQuestion.getCurrentItem() + 1)
        );

        QuestionBottomSheetDialog questionBottomSheetDialog = new QuestionBottomSheetDialog(ExamActivitySecond.this,
                questionList, new IClickBottomSheetDialogItemListener() {
            @Override
            public void onClick(int adapterPosition) {
                vpgExQuestion.setCurrentItem(adapterPosition);
            }

            @Override
            public void onClickHide() {
//                        bottomSheetBehavior.setHideable(true);
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        tvExShowAll.setOnClickListener(view -> questionBottomSheetDialog.show(getSupportFragmentManager(),
                questionBottomSheetDialog.getTag()));

        tvExBack.setOnClickListener(v -> {
            Intent intent = new Intent(ExamActivitySecond.this, ExamActivity.class);
            startActivity(intent);
        });

        tvExSubmit.setOnClickListener(v -> submit());
    }

    private void initUI() {
        vpgExQuestion = findViewById(R.id.vpgExQuestion);

        tvExTitle = findViewById(R.id.tvExTitle);
        tvExNumber = findViewById(R.id.tvExNumber);
        tvExBack = findViewById(R.id.tvExBack);
        tvExSubmit = findViewById(R.id.tvExSubmit);
        tvExPrev = findViewById(R.id.tvExPrev);
        tvExNext = findViewById(R.id.tvExNext);
        tvExShowAll = findViewById(R.id.tvExShowAllQuestion);

        lnBottomSheet = findViewById(R.id.bottom_sheet_layout);
        questionBottomSheetRecyclerView = findViewById(R.id.rcvQuestionBottomSheet);
        rcvExQuestion = findViewById(R.id.rcvExQuestion);

        bottomSheetBehavior = BottomSheetBehavior.from(lnBottomSheet);
    }

    private Exam getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return null;
        }
        Exam exam = (Exam) bundle.get("exam_object");

        return exam;
    }

    private void initData(int currentQuestion, int length) {
        String number = "Câu " + currentQuestion + "/" + length;
        tvExNumber.setText(number);
    }

    private List<Question> getListQuestionFromExam(String examName) {
        questionDAO = new QuestionDAO();
        return questionDAO.getListQuestionFromExam(examName);
    }

    private void renderQuestionPager(List<Question> list) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list, true, exam.getId());
        vpgExQuestion.setAdapter(viewPagerAdapter);
    }

    private void renderListExamQuestion(List<Question> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvExQuestion.setLayoutManager(linearLayoutManager);

        examQuestionAdapter = new ExamQuestionAdapter(ExamActivitySecond.this, list,
                new IClickExamQuesItemListener() {
                    @Override
                    public void onClick(int position) {
                        vpgExQuestion.setCurrentItem(position);
                    }
                });
        rcvExQuestion.setAdapter(examQuestionAdapter);
    }

    private void submit() {
        examMarkDAO = new ExamMarkDAO();

        List<ExamMark> examMarkList = examMarkDAO.getUserAnswerFromExam(exam.getName());
        if (examMarkList != null) {
            for (ExamMark examMark : examMarkList) {
                if (!examMark.getAnswer().equals(examMark.getAnswerCorrect()))
                    mark -= 0.5;
            }
        }

//        if(mark <= 8.5)
//            Log.d("mark", String.valueOf(mark) + " fail");
//        else {
//            Log.d("mark", String.valueOf(mark) + " pass");
        int rec = examMarkDAO.insertScore(mark, 1, exam.getId());
        Intent intent = new Intent(ExamActivitySecond.this, ExamActivity.class);
        startActivity(intent);
//            if(rec > 0) {
//                Log.d("mark", "insert ss");
//                Intent intent = new Intent(ExamActivitySecond.this, ExamActivity.class);
//                startActivity(intent);
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (examQuestionAdapter != null)
            examQuestionAdapter.release();
    }
}