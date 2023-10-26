package com.example.gplx_b2.Controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.gplx_b2.DAO.ExamDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    Context context;
//    final private Question question;
    final private int questionID;
    final private int examID;
    final private List<String> answerList;
    final private boolean isTest;
    final private IClickAnswerItemListener iClickAnswerItemListener;
    final private ArrayList<Integer> selectCheck = new ArrayList<>();
    final private ArrayList<String> answerChooseList = new ArrayList<>();
    private boolean isCorrect = false, isFirstClick = false;
    private ExamDAO examDAO;

    public AnswerAdapter(Context context, List<String> list, int questionID, boolean isTest, int examID,
                         IClickAnswerItemListener listener) {
        this.context = context;
        this.answerList = list;
        this.questionID = questionID;
        this.isTest = isTest;
        this.examID = examID;
        this.iClickAnswerItemListener = listener;

        for (int i = 0; i < answerList.size(); i++) {
            selectCheck.add(0);
            answerChooseList.add(null);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.answer_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String answer = answerList.get(position);
        if (answer == null)
            return;

        String answerNumber = (position + 1) + " - ",
                answerItem = answer + ".";

        //is checked - Single selection
        if (selectCheck.get(position) == 1)
            holder.radioButton.setChecked(true);
            //unchecked
        else
            holder.radioButton.setChecked(false);
        holder.tvAnswerNumber.setText(answerNumber);
        holder.tvAnswer.setText(answerItem);

        //events
        int adapterPosition = holder.getBindingAdapterPosition();
        holder.lnAnswerItem.setOnClickListener(view -> {
            if(!isTest) {
                if (!isFirstClick) {
                    iClickAnswerItemListener.onClickCheckBox(selectCheck, adapterPosition);
                    answerChooseList.set(adapterPosition, answer);

                    isFirstClick = true;
                }
            } else {
                iClickAnswerItemListener.onClickCheckBox(selectCheck, adapterPosition);
                answerChooseList.set(adapterPosition, answer);
            }
            Log.d("Ans", answer);
            notifyDataSetChanged();
        });
        holder.radioButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isTest) {
                if (answerChooseList.get(adapterPosition) != null) {
                    isCorrect = iClickAnswerItemListener.onCheckedChange(answer, isChecked, adapterPosition);

                    if (isCorrect)
                        holder.lnAnswerItem.setBackgroundColor(Color.parseColor("#1dd1a1"));
                    else
                        holder.lnAnswerItem.setBackgroundColor(Color.parseColor("#e74c3c"));

                    holder.tvAnswer.setTextColor(Color.parseColor("#f5f6fa"));
                    holder.tvAnswerNumber.setTextColor(Color.parseColor("#f5f6fa"));
                } else {
                    holder.radioButton.setChecked(false);
                }
                holder.lnAnswerItem.setClickable(false);
            } else {
                examDAO = new ExamDAO();

                if(examID != 0) {
                    int rec = examDAO.insertUserAnswer(answer,1, questionID, examID);
                    if(rec > 0)
                        Log.d("insert", "Insert successfully");
                    else {
                        Log.d("insert", "Insert failure");
                    }
                }
            }
            Log.d("check", String.valueOf(isTest));
        });
    }

    @Override
    public int getItemCount() {
        if (answerList != null)
            return answerList.size();
        return 0;
    }

    public void release() {
        context = null;
    }

    private List<String> standardListAnswer(List<String> list) {
        List<String> newAnswerList = new ArrayList<>();

        for (String answer : list)
            if (answer != null)
                newAnswerList.add(answer);

        return newAnswerList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final private RadioButton radioButton;
        final private TextView tvAnswerNumber;
        final private TextView tvAnswer;
        final private LinearLayout lnAnswerItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.cbAnswer);
            tvAnswerNumber = itemView.findViewById(R.id.txtAnswerNumber);
            tvAnswer = itemView.findViewById(R.id.txtAnswer);
            lnAnswerItem = itemView.findViewById(R.id.lnAnswerItem);
        }
    }
}
