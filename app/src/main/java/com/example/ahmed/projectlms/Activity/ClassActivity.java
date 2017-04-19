package com.example.ahmed.projectlms.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.ahmed.projectlms.Adapter.TitlePagerAdapter;
import com.example.ahmed.projectlms.Fragment.AnnouncementFragment;
import com.example.ahmed.projectlms.Fragment.AssignmentFragment;
import com.example.ahmed.projectlms.Fragment.DownlaodableFragment;
import com.example.ahmed.projectlms.Fragment.OverViewFragment;
import com.example.ahmed.projectlms.Fragment.ProgressAssignmentFragment;
import com.example.ahmed.projectlms.Fragment.ProgressFragment;
import com.example.ahmed.projectlms.Fragment.QuizFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.Notification_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;
import com.example.ahmed.projectlms.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 4/16/2017.
 */

public class ClassActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static final String EXTRAS_CLASS = "extrasClass";
    Class_model class_model = new Class_model();
    public static final String EXTRAS_NOTIFICATION= "extrasNotification";
    TextView teacherfirstName,teacherlastName,className;
    ImageView teacherImg;
    CardView overviewCard,myprogressCard,downloadableCard,assignmentCard,announcmentCard,classCalenderCard,quizCard;
    ImageView overviewImg,myprogressImg,downloadableImg,assignmentImg,announcemntImg,classCalenderImg,quizImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        class_model = (Class_model) getIntent().getSerializableExtra(EXTRAS_CLASS);

//        overviewCard = (CardView) findViewById(R.id.overview_card);
//        myprogressCard = (CardView) findViewById(R.id.myprogress_card);
//        downloadableCard = (CardView) findViewById(R.id.downloadable_card);
//        assignmentCard = (CardView) findViewById(R.id.assignment_card);
//        announcmentCard = (CardView) findViewById(R.id.announcement_card);
//        classCalenderCard = (CardView) findViewById(R.id.calender_card);
//        quizCard = (CardView) findViewById(R.id.quiz_card);
//
//        overviewImg = (ImageView) findViewById(R.id.overview_img);
//        myprogressImg = (ImageView) findViewById(R.id.downloadable_img);
//        downloadableImg = (ImageView) findViewById(R.id.downloadable_img);
//        assignmentImg = (ImageView) findViewById(R.id.assignment_img);
//        announcemntImg = (ImageView) findViewById(R.id.announcement_img);
//        classCalenderImg = (ImageView) findViewById(R.id.class_calender_img);
//        quizImg = (ImageView) findViewById(R.id.quiz_img);
//
//        overviewCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (overviewCard == view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),OverviewActivity.class);
//                    intent.putExtra(OverviewActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });
//        myprogressCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (myprogressCard == view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),ProgressActivity.class);
//                    intent.putExtra(ProgressActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });
//        downloadableCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (downloadableCard == view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),DownloadableActivity.class);
//                    intent.putExtra(DownloadableActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });
//        assignmentCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (assignmentCard == view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),AssignmentActivity.class);
//                    intent.putExtra(AssignmentActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });
//        announcmentCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (announcmentCard == view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),AnnouncementActivity.class);
//                    intent.putExtra(AnnouncementActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });
//        classCalenderCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (classCalenderCard == view)
//                {/*
//                    Intent intent = new Intent(getApplicationContext(),AnnouncementActivity.class);
//                    intent.putExtra(AnnouncementActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);*/
//                }
//            }
//        });
//        quizCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (quizCard== view)
//                {
//                    Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
//                    intent.putExtra(QuizActivity.EXTRAS_CLASS, class_model);
//                    startActivity(intent);
//                }
//            }
//        });



        toolbar_action_setup(getString(R.string.class_activity));


        teacherfirstName= (TextView) findViewById(R.id.teacher_first_name);
        teacherlastName= (TextView) findViewById(R.id.teacher_last_name);
        className = (TextView) findViewById(R.id.class_text);
        teacherImg = (ImageView) findViewById(R.id.teacher_img);

        getTeacherClass();

        ViewPager viewPager = (ViewPager)   findViewById(R.id.view_pager_class);
        TabLayout tabLayout= (TabLayout)   findViewById(R.id.tab_layout_class);;
        TitlePagerAdapter titlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager());

        OverViewFragment overViewFragment = new OverViewFragment() ;
        overViewFragment.setExtras(class_model);
        titlePagerAdapter.addFragment(overViewFragment,"classmates");


        ProgressAssignmentFragment progressAssignmentFragment = new ProgressAssignmentFragment() ;
        progressAssignmentFragment.setExtras(class_model);
        titlePagerAdapter.addFragment(progressAssignmentFragment , "progress");

        DownlaodableFragment downlaodableFragment = new DownlaodableFragment()  ;
        downlaodableFragment.setExtras(class_model);
        titlePagerAdapter.addFragment(downlaodableFragment,"downloadable");

        AssignmentFragment assignmentFragment = new AssignmentFragment()  ;
        assignmentFragment.setExtras(class_model);
        titlePagerAdapter.addFragment(assignmentFragment,"assignments");


        AnnouncementFragment announcementFragment = new AnnouncementFragment()  ;
        announcementFragment.setExtras(class_model);
        titlePagerAdapter.addFragment(announcementFragment,"announcements");


        viewPager.setAdapter(titlePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



        Notification_model notification= (Notification_model) getIntent().getExtras().get(EXTRAS_NOTIFICATION) ;
        if (notification == null) {
            viewPager.setCurrentItem(0,false);
        }else {
            switch (notification.getNotificationType()) {
                case "assignment_student.php": {

                    viewPager.setCurrentItem(3,false);
                    break;
                }
                case "downloadable_student.php": {
                    viewPager.setCurrentItem(2,false);
                    break;
                }
                case "announcements_student.php": {
                    viewPager.setCurrentItem(4,false);
                    break;
                }
            }
        }
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
    public void getTeacherClass()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URL + "subject_overview_student.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("overview", "overview response" + response);
                    JSONObject obj = new JSONObject(response);
                    String res = obj.getString("response");
                    if (res.equals("success")) {
                        JSONArray classData = obj.getJSONArray("data") ;

                        JSONObject temp = classData.getJSONObject(0) ;
                        teacherfirstName.setText(temp.getString("firstname"));
                        teacherlastName.setText(temp.getString("lastname"));
                        className.setText(temp.getString("class_name"));
                        Glide.with(getApplicationContext()).load(Config.img_url+temp.getString("location")).crossFade().into(teacherImg);

                    } else {

                        Toast.makeText(getApplicationContext(), getString(R.string.NO_TEACHER), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d("class_ac", "json error" + e.getMessage());
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
                params.put("teacher_class_id", class_model.getTeacherClassID());

                //params.put("token", FirebaseInstanceId.getInstance().getToken());

                Log.e("calss activity", "params: " + params.toString());
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


}
