package com.example.ahmed.projectlms.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.example.ahmed.projectlms.Adapter.Home_adapter;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Mohab on 4/17/2017.
 */

public class ProfileActivity extends AppCompatActivity
{
    ImageView imageViewProfile;
    TextView textViewProfileName, textViewProfileID;
    RecyclerView recyclerViewProfileClasses;
    private Home_adapter adapterHome;
    ArrayList<Class_model> classModel = new ArrayList<>();
    private final static String LOG_TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppController.getInstance().getPrefManager().getUser()==null)
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_profile);

        imageViewProfile = (ImageView) findViewById(R.id.imageview_profile);
        textViewProfileName = (TextView) findViewById(R.id.textview_profile_name);
        textViewProfileID = (TextView) findViewById(R.id.textview_profile_id);
        recyclerViewProfileClasses = (RecyclerView) findViewById(R.id.recyclerview_profile_classes);

        Log.e(LOG_TAG,"Error here : " + AppController.getInstance().getPrefManager().getUser().getFirstName().toString());

        textViewProfileName.setText(AppController.getInstance().getPrefManager().getUser().getFirstName().toString()
                + " " + AppController.getInstance().getPrefManager().getUser().getLastName().toString());
        textViewProfileID.setText(AppController.getInstance().getPrefManager().getUser().getStudent_id().toString());
        Log.e(LOG_TAG,"Profile_img : " + Config.img_url+AppController.getInstance().getPrefManager().getUser().getStudent_img());
        imageViewProfile.setImageDrawable(null);
        Glide.with(getApplicationContext()).load(Config.img_url+AppController.getInstance().getPrefManager().getUser().getStudent_img()).crossFade().into(imageViewProfile);

        adapterHome = new Home_adapter(this, classModel);
        recyclerViewProfileClasses.setAdapter(adapterHome);
        recyclerViewProfileClasses.setLayoutManager(new GridLayoutManager(this,2));
        profileClassData();
        toolbar_action_setup(getString(R.string.profile));
        findViewById(R.id.edit_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , EditProfileActivity.class));
            }
        });

    }
    public void profileClassData()
    {
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

                            classModel.add(class_model);

                        }
                        adapterHome.notifyDataSetChanged();


                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_err), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(LOG_TAG, "json error" + e.getMessage());
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

                Log.e(LOG_TAG, "params: " + params.toString());
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
