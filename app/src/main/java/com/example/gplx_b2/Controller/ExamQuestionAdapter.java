package com.example.gplx_b2.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickExamQuesItemListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamQuestionAdapter extends RecyclerView.Adapter<ExamQuestionAdapter.ViewHolder> {
    Context context;
    private List<Question> questionList;
    private IClickExamQuesItemListener iClickExamQuesItemListener;

    public ExamQuestionAdapter(Context context, List<Question> questionList, IClickExamQuesItemListener listener) {
        this.context = context;
        this.questionList = questionList;
        this.iClickExamQuesItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.question_exam_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        if(question == null)
            return;

        holder.tvExQuestionNum.setText(String.valueOf(position + 1));

        int adapterPosition = holder.getBindingAdapterPosition();
        holder.lnExQuestionItem.setOnClickListener(v -> iClickExamQuesItemListener.onClick(adapterPosition));
    }

    @Override
    public int getItemCount() {
        if(questionList != null)
            return questionList.size();
        return 0;
    }

    public void release() {
        context = null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final private TextView tvExQuestionNum;
        final private LinearLayout lnExQuestionItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvExQuestionNum = itemView.findViewById(R.id.tvExQuestionItem);
            lnExQuestionItem = itemView.findViewById(R.id.lnExQuestionItem);
        }
    }
}
