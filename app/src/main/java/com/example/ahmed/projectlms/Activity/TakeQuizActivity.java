package com.example.ahmed.projectlms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ahmed.projectlms.Fragment.TakeQuizMcqFragment;
import com.example.ahmed.projectlms.Fragment.TakeQuizTrueFalseFragment;
import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/6/2017.
 */

public class TakeQuizActivity extends AppCompatActivity
{
    CardView cardViewNextQuestion;
    private int quizType;
    FrameLayout frameTakeQuizMcq, frameTakeQuizTrueFalse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        cardViewNextQuestion = (CardView) findViewById(R.id.cardview_next_question);
        frameTakeQuizMcq = (FrameLayout) findViewById(R.id.frame_take_quiz_mcq);
        frameTakeQuizTrueFalse = (FrameLayout) findViewById(R.id.frame_take_quiz_true_false);
        quizType = 0;

        if(quizType == 1)
        {
            Fragment fragment = new TakeQuizMcqFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.take_quiz_fragment_container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        else
        {

            Fragment fragment = new TakeQuizTrueFalseFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.take_quiz_fragment_container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

        toolbar_action_setup(getString(R.string.take_quiz));
    }
    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final View notificationbtn = findViewById(R.id.main_toolbar_noti);
        final View menu_action = findViewById(R.id.main_toolbar_inbox);
        TextView page_title = (TextView) findViewById(R.id.main_toolbar_title);
        page_title.setText(title);
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationbtn == view) {
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                }
            }
        });
        menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_action == view) {
                    startActivity(new Intent(getApplicationContext(), MessActivity.class));


                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            onBackPressed();
        return true;
    }
}
