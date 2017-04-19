package com.example.ahmed.projectlms.Activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.projectlms.Adapter.Notification_Adapter;
import com.example.ahmed.projectlms.Adapter.OverviewAdapter;
import com.example.ahmed.projectlms.Fragment.AnnouncementFragment;
import com.example.ahmed.projectlms.Fragment.AssignmentFragment;
import com.example.ahmed.projectlms.Fragment.DownlaodableFragment;
import com.example.ahmed.projectlms.Fragment.OverViewFragment;
import com.example.ahmed.projectlms.Fragment.ProgressFragment;
import com.example.ahmed.projectlms.Fragment.QuizFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.Models.Notification_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;

import java.util.ArrayList;

import static com.example.ahmed.projectlms.R.id.toolbar;

/**
 * Created by ahmed on 4/7/2017.
 */

public class OverviewActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    public static final String EXTRAS_CLASS = "extrasClass";
    private Class_model class_model;
    private Notification_model notification_model;
    public static final String EXTRAS_NOTIFICATION= "extrasNotification";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


        class_model = (Class_model) getIntent().getSerializableExtra(EXTRAS_CLASS);

        notification_model = (Notification_model) getIntent().getSerializableExtra(EXTRAS_NOTIFICATION);

        toolbar_action_setup(getString(R.string.overview_text));

           Notification_model notification= (Notification_model) getIntent().getExtras().get(EXTRAS_NOTIFICATION) ;
        if (notification == null) {

            OverViewFragment overViewFragment = new OverViewFragment();
            overViewFragment.setExtras(class_model);
            addFragment(overViewFragment);
        }else {
            switch (notification.getNotificationType()){
                case "assignment_student.php":{

                    AssignmentFragment assignmentFragment = new AssignmentFragment()  ;
                    assignmentFragment.setExtras(class_model);
                    addFragment(assignmentFragment);
                    Log.e("overview","notfi  "+notification.getNotificationType()  );
               break;
                }
                case "downloadable_student.php":{
                    addFragment( new DownlaodableFragment());
                    Log.e("overview","notfi  "+notification.getNotificationType()  );
                    break;
                }
                case "announcements_student.php":{
                    AnnouncementFragment announcementFragment = new AnnouncementFragment()  ;
                    announcementFragment.setExtras(class_model);
                    addFragment(announcementFragment);
                    Log.e("overview","notfi  "+notification.getNotificationType()  );
                    break;
                }
                case "student_quiz_list.php":{
                    QuizFragment quizFragment = new QuizFragment()  ;
                    addFragment(quizFragment);
                    Log.e("overview","notfi  "+notification.getNotificationType()  );
                    break;
                }

            default: Log.e("overview","notfi  "+notification.getNotificationType()  );
            }

        }


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

    void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container , fragment);
        fragmentManager.addOnBackStackChangedListener(null);
        fragmentTransaction.commit() ;
    }

}
