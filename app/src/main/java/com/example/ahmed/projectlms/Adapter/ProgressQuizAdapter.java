package com.example.ahmed.projectlms.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.QuizModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/8/2017.
 */

public class ProgressQuizAdapter extends RecyclerView.Adapter<ProgressQuizAdapter.ProgressQuizViewHolder>
{
    private List<QuizModel> quizList;

    public ProgressQuizAdapter(List<QuizModel> quizList)
    {
        this.quizList = quizList;
    }

    @Override
    public ProgressQuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_quiz_row, parent, false);
        return new ProgressQuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProgressQuizViewHolder holder, int position)
    {
        QuizModel quiz = quizList.get(position);
        holder.textViewQuizTitleProgress.setText(quiz.getQuizTitle());
        holder.textViewQuizDescriptionProgress.setText(quiz.getQuizDescription());
        holder.textViewQuizScoreProgress.setText(quiz.getQuizScore());
        holder.textViewQuizTimeProgress.setText(quiz.getQuizTime());
    }

    @Override
    public int getItemCount()
    {
        return quizList.size();
    }

    public class ProgressQuizViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewQuizTitleProgress, textViewQuizDescriptionProgress, textViewQuizTimeProgress, textViewQuizScoreProgress;
        public ProgressQuizViewHolder(View itemView)
        {
            super(itemView);
            textViewQuizTitleProgress = (TextView) itemView.findViewById(R.id.textview_progress_quiz_title);
            textViewQuizDescriptionProgress =(TextView) itemView.findViewById(R.id.textview_progress_quiz_description);
            textViewQuizTimeProgress = (TextView) itemView.findViewById(R.id.textview_progress_quiz_time);
            textViewQuizScoreProgress = (TextView) itemView.findViewById(R.id.textview_progress_quiz_score);
        }
    }
}
