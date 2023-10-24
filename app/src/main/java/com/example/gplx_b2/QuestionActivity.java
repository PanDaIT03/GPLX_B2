package com.example.gplx_b2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gplx_b2.Controller.ViewPagerAdapter;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.myInterface.IClickBottomSheetDialogItemListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    Context context = this;
    QuestionDAO questionDAO;
    ImageView imgQuestionTopic;
    TextView tvTitle, tvNumber, tvPrev, tvNext, tvShowAllQuestion;
    LinearLayout lnBottomSheet;
    ViewPager2 viewPager2;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView questionBottomSheetRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initUI();
        Topic topic = getData();

        String topicTitle = topic.getTitle();
        tvTitle.setText(topicTitle);

        renderQuestionPager(topic);

        int length = getListQuestion(topicTitle).size();
        initData(1, length);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    tvPrev.setClickable(false);
                } else if (position == length - 1) {
                    tvNext.setClickable(false);
                } else {
                    tvNext.setClickable(true);
                    tvPrev.setClickable(true);
                }
                initData(position + 1, length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        tvPrev.setOnClickListener(view ->
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1)
        );
        tvNext.setOnClickListener(view ->
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1)
        );

        List<UserAnswer> userAnswerList = getListUserAnswer();
        List<Question> questionList = getListQuestion(topic.getTitle());
        QuestionBottomSheetDialog questionBottomSheetDialog = new QuestionBottomSheetDialog(context, questionList, topic,
                userAnswerList, new IClickBottomSheetDialogItemListener() {
                    @Override
                    public void onClick(int adapterPosition) {
                        viewPager2.setCurrentItem(adapterPosition);
                    }

                    @Override
                    public void onClickHide() {
//                        bottomSheetBehavior.setHideable(true);
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

        tvShowAllQuestion.setOnClickListener(view -> questionBottomSheetDialog.show(getSupportFragmentManager(),
                questionBottomSheetDialog.getTag()));
    }

    private void initUI() {
        tvTitle = findViewById(R.id.title);
        imgQuestionTopic = findViewById(R.id.imgQuestionTopic);

        tvNumber = findViewById(R.id.txtNumber);
        tvPrev = findViewById(R.id.txtPrev);
        tvNext = findViewById(R.id.txtNext);

        tvShowAllQuestion = findViewById(R.id.txtShowAllQuestion);

        viewPager2 = findViewById(R.id.vpgQuestion);
        lnBottomSheet = findViewById(R.id.bottom_sheet_layout);
        questionBottomSheetRecyclerView = findViewById(R.id.rcvQuestionBottomSheet);

        bottomSheetBehavior = BottomSheetBehavior.from(lnBottomSheet);
    }

    private void initData(int currentQuestion, int length) {
        String number = "Câu " + currentQuestion + "/" + length;
        tvNumber.setText(number);
    }

    private Topic getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return null;
        }
        Topic topic = (Topic) bundle.get("object_topic");

        return topic;
    }

    private List<Question> getListQuestion(String topic) {
        questionDAO = new QuestionDAO();
        List<Question> questionList = questionDAO.getListQuestions(topic);

        return questionList;
    }

    private void renderQuestionPager(@NonNull Topic topic) {
        String name = topic.getImage();
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        imgQuestionTopic.setImageResource(imageResource);

        List<Question> questionList = getListQuestion(topic.getTitle());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, questionList);
        viewPager2.setAdapter(viewPagerAdapter);
    }

    private List<UserAnswer> getListUserAnswer() {
        questionDAO = new QuestionDAO();
        List<UserAnswer> userAnswerList = questionDAO.getListUserAnswer();

        return userAnswerList;
    }

    public void onClickBackToMain(View view) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}