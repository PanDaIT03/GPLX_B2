package com.example.gplx_b2.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickBottomSheetDialogItemListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class QuestionBottomSheetDialogItemAdapter extends RecyclerView.Adapter<QuestionBottomSheetDialogItemAdapter.ViewHolder> {
    private Context context;
    private List<Question> questionList;
    private List<UserAnswer> userAnswerList;
    private Topic topic;
    private IClickBottomSheetDialogItemListener iClickBottomSheetDialogItemListener;

    public QuestionBottomSheetDialogItemAdapter(Context context, List<Question> questionList, Topic topic,
                                                List<UserAnswer> userAnswerList, IClickBottomSheetDialogItemListener listener) {
        this.context = context;
        this.questionList = questionList;
        this.topic = topic;
        this.userAnswerList = userAnswerList;
        this.iClickBottomSheetDialogItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.question_bottom_sheet_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        if (question == null)
            return;

        boolean learned = false;
        for (UserAnswer userAnswer : userAnswerList)
            if (question.getId() == userAnswer.getQuestionID()) {
                learned = true;
                holder.tvLearned.setText(" | đã học");

                if (question.getAnswerCorrect().equals(userAnswer.getAnswer())) {
                    holder.imgLearned.setImageResource(getImageResource("tick"));
                    Log.d("check", "tick " + question.getId() + " " + userAnswer.getQuestionID());
                } else {
                    holder.imgLearned.setImageResource(getImageResource("x"));
                    Log.d("check", "x " + question.getId() + " " + userAnswer.getQuestionID());
                }
            }
        if (!learned)
            holder.imgLearned.setImageDrawable(null);

        String imageName = topic.getImage();
        String questionNumber = "Câu " + (position + 1) + "/" + questionList.size();

        try {
            holder.imgTopic.setImageResource(getImageResource(imageName));
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
        holder.tvQuestionNumber.setText(questionNumber);
        holder.tvQuestion.setText(question.getQuestion());

        int adapterPosition = holder.getBindingAdapterPosition();
        holder.lnQuestionItem.setOnClickListener(view -> iClickBottomSheetDialogItemListener.onClick(adapterPosition));
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        return 0;
    }

    private int getImageResource(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout lnQuestionItem;
        final private ImageView imgTopic, imgLearned;
        final private TextView tvQuestionNumber, tvLearned, tvQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lnQuestionItem = itemView.findViewById(R.id.lnQuestionItem);
            imgTopic = itemView.findViewById(R.id.imgTopicBottomSheet);
            imgLearned = itemView.findViewById(R.id.imgLearned);
            tvQuestionNumber = itemView.findViewById(R.id.txtQuestionNumberBottomSheet);
            tvLearned = itemView.findViewById(R.id.txtLearned);
            tvQuestion = itemView.findViewById(R.id.txtQuestionBottomSheet);
        }
    }
}
