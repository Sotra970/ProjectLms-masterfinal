package com.example.ahmed.projectlms.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ahmed.projectlms.Adapter.Home_adapter;
import com.example.ahmed.projectlms.Models.Class_model;
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
 * Created by ahmed on 4/6/2017.
 */

public class HomeActivity extends AppCompatActivity implements Home_adapter.Listener {
    RecyclerView recyclerView;
    public Home_adapter home_adapter;
    ArrayList<Class_model> item_model = new ArrayList<>();
    private String TAG = HomeActivity.class.getSimpleName();
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        View notficatio = findViewById(R.id.main_toolbar_noti);
        notficatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
            }
        });

        View power = findViewById(R.id.logout);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AppController.getInstance().getPrefManager().clear();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.home_item);
        home_adapter = new Home_adapter(getApplicationContext(),item_model);
        recyclerView.setAdapter(home_adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        home_adapter.setListener(this);
        toolbar_action_setup(getString(R.string.home));
        homeData();

    }

    public void homeData()
    {



        findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "teacher_class_student.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("signup", "Signup response" + response);
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONArray classData = obj.getJSONArray("data") ;
                        for (int i= (0); i<classData.length() ; i++){
                            JSONObject temp = classData.getJSONObject(i) ;
                            Class_model class_model = new Class_model();

                            class_model.setClassName(temp.getString("class_name"));
                            class_model.setClassID(temp.getString("class_id"));
                            class_model.setClassimg(temp.getString("thumbnails"));
                            class_model.setSubjectCode(temp.getString("subject_code"));
                            class_model.setTeacherClassID(temp.getString("teacher_class_id"));
                            class_model.setTeacherfirstName(temp.getString("firstname"));
                            class_model.setTeacherlastName(temp.getString("lastname"));
                            class_model.setLocation(temp.getString("location"));

                            item_model.add(class_model);

                        }
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        home_adapter.notifyDataSetChanged();


                    } else {
                        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.login_err), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "json error" + e.getMessage());
                    {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                }
                if (error instanceof NoConnectionError) {
                    findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                    String message =   "please check connection";
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                }
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", AppController.getInstance().getPrefManager().getUser().getStudent_id());

                //params.put("token", FirebaseInstanceId.getInstance().getToken());

                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    @Override
    public void onClassClicked(Class_model class_model)
    {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, ClassActivity.class);
        intent.putExtra(ClassActivity.EXTRAS_CLASS, class_model);
        startActivity(intent);
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
