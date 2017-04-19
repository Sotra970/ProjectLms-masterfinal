package com.example.ahmed.projectlms.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Models.QuizModel;
import com.example.ahmed.projectlms.R;

import java.util.List;

/**
 * Created by Mohab on 4/6/2017.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder>
{

    private List<QuizModel> quizList;
    private Listener listener;
    private Context context;

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    public QuizAdapter(List<QuizModel> quizList, Context context)
    {
        this.quizList = quizList;
        this.context = context;
    }

    @Override
    public QuizAdapter.QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_row, parent, false);
        return new QuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizAdapter.QuizViewHolder holder, int position)
    {
        QuizModel quiz = quizList.get(position);
        holder.textViewQuizTitle.setText(quiz.getQuizTitle());
        holder.textViewQuizDescription.setText(quiz.getQuizDescription());
        holder.textViewQuizScore.setText(quiz.getQuizScore());
        holder.textViewQuizTime.setText(quiz.getQuizTime());

        if (quiz.getQuizScore().trim().isEmpty()){
            holder.score.setVisibility(View.GONE);
            holder.taken.setVisibility(View.GONE);
        }else {
            holder.cardViewTakeQuiz.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewQuizTitle, textViewQuizDescription, textViewQuizTime, textViewQuizScore;
        CardView cardViewTakeQuiz;
        View taken , score ;
        public QuizViewHolder(View itemView)
        {
            super(itemView);
            textViewQuizTitle = (TextView) itemView.findViewById(R.id.textview_quiz_title);
            textViewQuizDescription =(TextView) itemView.findViewById(R.id.textview_quiz_description);
            textViewQuizScore = (TextView) itemView.findViewById(R.id.textview_quiz_score);
            textViewQuizTime = (TextView) itemView.findViewById(R.id.textview_quiz_time);
            taken =  itemView.findViewById(R.id.taken);
            score = itemView.findViewById(R.id.score_layout);

            cardViewTakeQuiz = (CardView) itemView.findViewById(R.id.cardview_take_quiz);
            cardViewTakeQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(listener != null)
                    {
                        QuizModel quiz = quizList.get(getAdapterPosition());
                        listener.onTakeQuizClicked(quiz);

                    }

                }
            });
        }
    }

    public interface Listener
    {
        void onTakeQuizClicked(QuizModel quiz);
    }

}
