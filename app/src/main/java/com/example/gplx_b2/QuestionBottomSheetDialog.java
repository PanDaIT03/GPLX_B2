package com.example.gplx_b2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.gplx_b2.Controller.QuestionBottomSheetDialogItemAdapter;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.myInterface.IClickBottomSheetDialogItemListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionBottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private Context context;
    private List<Question> questionList;
    private List<UserAnswer> userAnswerList;
    private Topic topic;
    private IClickBottomSheetDialogItemListener iClickBottomSheetDialogItemListener;
    private RecyclerView rcvQuestion;
    private TextView tvHideBottomSheet;

    public QuestionBottomSheetDialog(Context context, List<Question> questionList, Topic topic, List<UserAnswer> userAnswerList,
                                     IClickBottomSheetDialogItemListener listener) {
        this.context = context;
        this.questionList = questionList;
        this.topic = topic;
        this.userAnswerList = userAnswerList;
        this.iClickBottomSheetDialogItemListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);

        initUI(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQuestion.setLayoutManager(linearLayoutManager);

        QuestionBottomSheetDialogItemAdapter questionBottomSheetDialogItemAdapter =
                new QuestionBottomSheetDialogItemAdapter(context, questionList, topic, userAnswerList,
                        new IClickBottomSheetDialogItemListener() {
                            @Override
                            public void onClick(int adapterPosition) {
                                iClickBottomSheetDialogItemListener.onClick(adapterPosition);
                            }

                            @Override
                            public void onClickHide() {
                                iClickBottomSheetDialogItemListener.onClickHide();
                            }
                        });
        rcvQuestion.setAdapter(questionBottomSheetDialogItemAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvQuestion.addItemDecoration(itemDecoration);

        tvHideBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickBottomSheetDialogItemListener.onClickHide();
            }
        });

        return bottomSheetDialog;
    }

    private void initUI(View view) {
        rcvQuestion = view.findViewById(R.id.rcvQuestionBottomSheet);
        tvHideBottomSheet = view.findViewById(R.id.txtHideQuestionBottomSheet);
    }
}
