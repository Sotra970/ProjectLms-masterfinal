package com.example.ahmed.projectlms.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Activity.MessActivity;
import com.example.ahmed.projectlms.Activity.NotificationActivity;
import com.example.ahmed.projectlms.Activity.TakeQuizActivity;
import com.example.ahmed.projectlms.Adapter.QuizAdapter;
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
 * Created by Mohab on 4/6/2017.
 */

public class QuizFragment extends Fragment implements QuizAdapter.Listener
{
    View view;
    private RecyclerView recyclerViewQuiz;
    private QuizAdapter quizAdapter;
    private ArrayList<QuizModel> quizList = new ArrayList<>();
    String pStudentId , pClassId ;
    Class_model extras= new Class_model();

    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }
    public static final String LOG_TAG = QuizFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_quiz, container, false);
        recyclerViewQuiz = (RecyclerView) view.findViewById(R.id.recycler_view_quiz);
        quizAdapter = new QuizAdapter(quizList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewQuiz.setLayoutManager(layoutManager);
        quizAdapter.setListener(this);
        recyclerViewQuiz.setAdapter(quizAdapter);
        toolbar_action_setup(getString(R.string.quiz_text));
        quizData();

        return view;
    }

    private void quizData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "quiz.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
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

                            quizList.add(quiz);
                        }
                        quizAdapter.notifyDataSetChanged();


                    }


                }
                catch (JSONException e)
                {
                    Log.e(LOG_TAG, ":  error in onResponse : " + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    @Override
    public void onTakeQuizClicked(QuizModel quiz)
    {
        Context context = view.getContext();
        Intent intent = new Intent(context, TakeQuizActivity.class);
        startActivity(intent);
    }

    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final View notificationbtn = view.findViewById(R.id.main_toolbar_noti);
        final View menu_action = view.findViewById(R.id.main_toolbar_inbox);
        TextView page_title = (TextView) view.findViewById(R.id.main_toolbar_title);
        page_title.setText(title);
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationbtn == view) {
                    Intent intent = new Intent(getContext(), NotificationActivity.class);
                    startActivity(intent);
                }
            }
        });
        menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_action == view) {
                    startActivity(new Intent(getContext(), MessActivity.class));


                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            getActivity().onBackPressed();
        return true;
    }
}
