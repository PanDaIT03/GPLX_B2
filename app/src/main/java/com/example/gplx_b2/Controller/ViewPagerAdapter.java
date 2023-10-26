package com.example.gplx_b2.Controller;

import android.os.Bundle;

import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.QuestionFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Question> questionList;
    private QuestionDAO questionDAO;
    private boolean isTest;
    private int examID;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> questionList, boolean isTest, int examID) {
        super(fragmentActivity);
        this.questionList = questionList;
        this.isTest = isTest;
        this.examID = examID;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (questionList == null || questionList.isEmpty())
            return null;

        Question question = questionList.get(position);
        String name = null;
        if (question.getTrafficSignsID() != 0) {
            questionDAO = new QuestionDAO();
            name = questionDAO.getImageFileName(question.getTrafficSignsID());
        }

        QuestionFragment questionFragment = new QuestionFragment(isTest, examID);
        Bundle bundle = new Bundle();

        bundle.putSerializable("question_object", question);
        bundle.putString("imageFileName", name);
        questionFragment.setArguments(bundle);

        return questionFragment;
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        return 0;
    }
}
