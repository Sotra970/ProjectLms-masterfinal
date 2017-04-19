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

public class TakeQuizTrueFalseFragment extends Fragment
{
    View view;
    TextView textViewTrueFalseTitle, textViewTrueFalseDescription, textViewTrueFalseTimeRemaining,
            textViewTrueFalseQuestionNumber, textViewTrueFalseQuestion;
    RadioButton radioButtonTrue, radioButtonFalse;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_take_quiz_true_false, container, false);
        textViewTrueFalseTitle = (TextView) view.findViewById(R.id.textview_quiz_true_false_title);
        textViewTrueFalseDescription = (TextView) view.findViewById(R.id.textview_quiz_true_false_description);
        textViewTrueFalseTimeRemaining = (TextView) view.findViewById(R.id.textview_quiz_true_false_time_remaining);
        textViewTrueFalseQuestionNumber = (TextView) view.findViewById(R.id.textview_true_false_question_number);
        textViewTrueFalseQuestion = (TextView) view.findViewById(R.id.textview_quiz_true_false_question);

        radioButtonTrue = (RadioButton) view.findViewById(R.id.radiobutton_true);
        radioButtonFalse = (RadioButton) view.findViewById(R.id.radiobutton_false);

        return view;
    }
}
