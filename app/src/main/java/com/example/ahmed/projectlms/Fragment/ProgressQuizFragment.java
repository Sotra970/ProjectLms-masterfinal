package com.example.ahmed.projectlms.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Adapter.ProgressQuizAdapter;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.QuizModel;
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
 * Created by Mohab on 4/8/2017.
 */

public class ProgressQuizFragment extends Fragment
{
    RecyclerView recyclerViewQuizProgress;
    ProgressQuizAdapter progressQuizAdapter;
    private ArrayList<QuizModel> quizList = new ArrayList<>();
    public static final String LOG_TAG = ProgressQuizFragment.class.getSimpleName();
    String pStudentId, pClassId;
    Class_model extras= new Class_model();

    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_quiz_progress,container,false);
        recyclerViewQuizProgress = (RecyclerView) view.findViewById(R.id.recycler_view_quiz_progress);
        progressQuizAdapter = new ProgressQuizAdapter(quizList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewQuizProgress.setLayoutManager(layoutManager);
        recyclerViewQuizProgress.setAdapter(progressQuizAdapter);

        quizData();
        return view;
    }

    private void quizData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "quiz.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, ":  error in log1 : " + response);

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString("response");
                    if(responseString.equals("success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            QuizModel quiz = new QuizModel();
                            quiz.setQuizTitle(jsonObject1.getString("quiz_title"));
                            quiz.setQuizDescription(jsonObject1.getString("quiz_description"));
                            quiz.setQuizTime(jsonObject1.getString("quiz_time"));
                            quiz.setQuizScore(jsonObject1.getString("grade"));
                             if (!quiz.getQuizScore().trim().isEmpty())
                            quizList.add(quiz);
                        }
                        progressQuizAdapter.notifyDataSetChanged();


                    }
                }
                catch (JSONException e) {
                    Log.e(LOG_TAG, ":  error in onResponse : " + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<>();
                params.put("student_id", AppController.getInstance().getPrefManager().getUser().getStudent_id());
                params.put("class_id", extras.getTeacherClassID());
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

    }
}
