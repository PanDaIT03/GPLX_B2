package com.example.gplx_b2.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.Modal.UserAnswer;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickTopicItemListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private Context context;
    final private List<Topic> topicList;
    final private List<UserAnswer> userAnswerList;
    final private IClickTopicItemListener iClickTopicItemListener;

    public TopicAdapter(Context context, List<Topic> topicList, List<UserAnswer> userAnswerList, IClickTopicItemListener listener) {
        this.context = context;
        this.topicList = topicList;
        this.userAnswerList = userAnswerList;
        this.iClickTopicItemListener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.topic_item, parent, false);

        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
//        UserAnswer userAnswer = userAnswerList.get(position);
        if (topic == null) {
            return;
        }

        String name = topic.getImage();
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

        String questionQuantity = topic.getSubTitle() + " câu hỏi";
//        int userAnsQuantity = 0;
//        if(userAnswer != null) {
//            questionQuantity = userAnswer.getQuantity() + "/" + topic.getSubTitle() + " câu hỏi";
//            userAnsQuantity = userAnswer.getQuantity();
//        }
//        else
//            questionQuantity = topic.getSubTitle() + " câu hỏi";

        holder.resourceId.setImageResource(imageResource);
        holder.title.setText(topic.getTitle());
        holder.numberQuestion.setText(questionQuantity);

        holder.bar.setProgress(0);
        holder.bar.setMax(topic.getQuantity());

//        Log.d("bar", "user" + userAnswer.getQuantity());
//        Log.d("bar", "topic" + topic.getQuantity());

        holder.relativeLayout.setOnClickListener(view -> iClickTopicItemListener.onClickItemTopic(topic));
    }

    @Override
    public int getItemCount() {
        if (topicList != null) {
            return topicList.size();
        }
        return 0;
    }

    public void release() {
        context = null;
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        final private ImageView resourceId;
        final private TextView title;
        final private ProgressBar bar;
        final private TextView numberQuestion;
        final private RelativeLayout relativeLayout;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceId = itemView.findViewById(R.id.imgTopic);
            title = itemView.findViewById(R.id.txtTitle);
            bar = itemView.findViewById(R.id.barProgress);
            numberQuestion = itemView.findViewById(R.id.txtQuestionNumber);
            relativeLayout = itemView.findViewById(R.id.layout_item);

        }
    }
}