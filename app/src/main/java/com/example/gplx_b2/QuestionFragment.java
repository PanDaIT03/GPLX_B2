package com.example.gplx_b2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gplx_b2.Controller.AnswerAdapter;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    private boolean isTest;
    private int examID;
    private Context context;
    private Question question;
    private Topic topic;
    private View layout;
    private AnswerAdapter answerAdapter;
    private RecyclerView answerRecyclerView;
    private QuestionDAO questionDAO;
    private TextView tvQuestion;
    private ImageView imgTrafficSign;

    public QuestionFragment(boolean isTest, int examID) {
        this.isTest = isTest;
        this.examID = examID;
    }

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_question, container, false);
        initUI(layout);

        question = getDataQuestion();
        if (question != null) {
            tvQuestion.setText(question.getQuestion());

            if (question.getTrafficSignsID() != 0) {
                String name = getImageFileName();
                Log.d("imgName", name);

                try {
                    int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
                    Log.d("imgRsrc", String.valueOf(imageResource));
                    imgTrafficSign.setImageResource(imageResource);
                } catch (Exception ex) {
                    Log.d("Error", ex.getMessage());
                }
            } else {
                imgTrafficSign.setVisibility(View.GONE);
            }

            renderListAnswer(question);
        }
        return layout;
    }

    private void initUI(View layout) {
        answerRecyclerView = layout.findViewById(R.id.rcvAnswer);
        tvQuestion = layout.findViewById(R.id.txtQuestion);
        imgTrafficSign = layout.findViewById(R.id.imgTrafficSign);
    }

    private Question getDataQuestion() {
        Bundle bundle = getArguments();
        if (bundle != null)
            question = (Question) bundle.get("question_object");

        return question;
    }

    private String getImageFileName() {
        String name = null;
        Bundle bundle = getArguments();
        if (bundle != null)
            name = bundle.getString("imageFileName");

        return name;
    }

    private void renderListAnswer(Question question) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        answerRecyclerView.setLayoutManager(linearLayoutManager);

        List<String> answerList = question.getAnswerList();
        answerAdapter = new AnswerAdapter(context, standardListAnswer(answerList), question.getId(), isTest, examID,
                new IClickAnswerItemListener() {
            @Override
            public void onClickCheckBox(ArrayList<Integer> selectCheck, int adapterPosition) {
                for (int i = 0; i < selectCheck.size(); i++) {
                    if (i == adapterPosition) {
                        selectCheck.set(i, 1);
                    } else {
                        selectCheck.set(i, 0);
                    }
                }
            }

            @Override
            public boolean onCheckedChange(String answer, boolean isChecked, int adapterPosition) {
                boolean isCorrect = false;

                if (isChecked) {
                    Question question = getDataQuestion();
                    String answerCorrect = question.getAnswerCorrect();

                    if (answer.equals(answerCorrect))
                        isCorrect = true;

//                    int rec = 0;
//                    questionDAO = new QuestionDAO();
//                    rec = questionDAO.insertUserAnswer(answer, question.getId());
//                    if (rec > 0)
//                        Log.d("insert", "Insert success");
//                    else
//                        Log.d("insert", "Insert failure");
                }

                return isCorrect;
            }
        });
        answerRecyclerView.setAdapter(answerAdapter);
    }

    private List<String> standardListAnswer(List<String> list) {
        List<String> newAnswerList = new ArrayList<>();

        for (String answer : list)
            if (answer != null)
                newAnswerList.add(answer);

//        set height for recycler view without RecyclerView parent container height as wrap_content
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) answerRecyclerView.getLayoutParams();
//        layoutParams.height = newAnswerList.size() * 200;
//        answerRecyclerView.setLayoutParams(layoutParams);
        return newAnswerList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (answerAdapter != null) {
            answerAdapter.release();
        }
    }
}