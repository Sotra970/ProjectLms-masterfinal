package com.example.ahmed.projectlms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Fragment.QuizFragment;
import com.example.ahmed.projectlms.Fragment.TakeQuizMcqFragment;
import com.example.ahmed.projectlms.Fragment.TakeQuizTrueFalseFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.QuizQuestionModel;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohab on 4/6/2017.
 */

public class QuizActivity extends AppCompatActivity
{


    public static final String EXTRAS_CLASS = "extrasClass";
    Class_model class_model = new Class_model();

    ArrayList<QuizQuestionModel> quizQuestionList = new ArrayList<>();
    public static String LOG_TAG = QuizActivity.class.getSimpleName();
    String pQuestionId = "35";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        class_model = (Class_model) getIntent().getSerializableExtra(EXTRAS_CLASS);
        Fragment fragment = new QuizFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.quiz_fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();

        quizQuestionData();

    }

    public void quizQuestionData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "quiz_question.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e(LOG_TAG, " error in log1 quizActivity : "+ response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("response");
                    if (responseString.equals("success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            QuizQuestionModel quizQuestion = new QuizQuestionModel();

                            quizQuestion.setQuizQuestionId(jsonObject1.getString("quiz_question_id"));
                            quizQuestion.setQuestionTypeId(jsonObject1.getString("question_type_id"));
                            quizQuestion.setQuestionType(jsonObject1.getString("question_type"));
                            quizQuestion.setQuestionText(jsonObject1.getString("question_text"));
                            quizQuestion.setQuestionAnswer(jsonObject1.getString("answer"));

                            quizQuestionList.add(quizQuestion);
                            notifyAll();

                        }
                    }
                } catch (JSONException e)
                {
                    Log.e(LOG_TAG, "error in onResponse "+ e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("id",pQuestionId);

                return params;
            }
        };

        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);



        QuizQuestionModel quizQuestion = new QuizQuestionModel();
        if (quizQuestion.getQuestionTypeId().equals("1"))
        {
            Fragment fragment = new TakeQuizMcqFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.take_quiz_fragment_container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        else if(quizQuestion.getQuestionTypeId().equals("2"))
        {
            Fragment fragment = new TakeQuizTrueFalseFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.take_quiz_fragment_container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }


}
