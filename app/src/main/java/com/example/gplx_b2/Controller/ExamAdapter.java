package com.example.gplx_b2.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Modal.Exam;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickExamItemListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private Context context;
    private List<Exam> examList;
    private List<Integer> scoreList;
    private IClickExamItemListener iClickExamItemListener;

    public ExamAdapter(Context context, List<Exam> examList, List<Integer> scoreList, IClickExamItemListener listener) {
        this.context = context;
        this.examList = examList;
        this.scoreList = scoreList;
        this.iClickExamItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.exam_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exam exam = examList.get(position);
        int score = scoreList.get(position);
        if(exam == null)
            return;

        Log.d("Exam", exam.getName());
        holder.tvExamName.setText(exam.getName());
        if(score <= 8.5)
            holder.tvPass.setText("Rớt");
        else
            holder.tvPass.setText("Đạt");

        holder.lnExamItem.setOnClickListener(v -> {
            iClickExamItemListener.onClick(exam);
        });
    }

    @Override
    public int getItemCount() {
        if(examList != null)
            return examList.size();
        return 0;
    }

    public void release() {
        context = null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnExamItem;
        private TextView tvExamName;
        private TextView tvPass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnExamItem = itemView.findViewById(R.id.lnExamItem);
            tvExamName = itemView.findViewById(R.id.txtExamName);
            tvPass = itemView.findViewById(R.id.tvPass);
        }
    }
}
