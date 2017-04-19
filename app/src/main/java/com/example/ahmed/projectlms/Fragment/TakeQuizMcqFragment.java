package com.example.ahmed.projectlms.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/7/2017.
 */

public class TakeQuizMcqFragment extends Fragment
{
    View view;
    TextView textViewQuizMcqTitle,textViewQuizMcqDescription,textViewQuizMcqTime,textViewQuestionNumber
            ,textViewQuizMcqQuestion;
    RadioButton radioButtonA,radioButtonB,radioButtonC,radioButtonD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_take_quiz_mcq, container, false);

        textViewQuizMcqDescription = (TextView) view.findViewById(R.id.textview_quiz_mcq_description);
        textViewQuizMcqTime = (TextView) view.findViewById(R.id.textview_quiz_mcq_time_remaining);
        textViewQuestionNumber = (TextView) view.findViewById(R.id.text_view_mcq_question_number);
        textViewQuizMcqQuestion = (TextView) view.findViewById(R.id.textview_quiz_mcq_question);
        textViewQuizMcqTitle = (TextView) view.findViewById(R.id.textview_quiz_mcq_title);

        radioButtonA = (RadioButton) view.findViewById(R.id.radiobutton_a);
        radioButtonB = (RadioButton) view.findViewById(R.id.radiobutton_b);
        radioButtonC = (RadioButton) view.findViewById(R.id.radiobutton_c);
        radioButtonD = (RadioButton) view.findViewById(R.id.radiobutton_d);

        return view;
    }
}
