package com.example.ahmed.projectlms.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ahmed.projectlms.Adapter.Title_Pager_Adapter;
import com.example.ahmed.projectlms.Fragment.MessageFragment;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.app.AppController;


public class MessActivity extends AppCompatActivity {
    ViewPager viewPager ;
    MessageFragment inbox , outbox ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        View main_container = findViewById(R.id.viewPager_mess);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            FabTransform.setup(this, main_container);}
        toolbar_action_setup(getString(R.string.messages));

        View compass = findViewById(R.id.compass);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivityForResult(new Intent(getApplicationContext() , MessageCompasActivity.class) , 2033);
            }
        });



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
         viewPager = (ViewPager) findViewById(R.id.viewPager_mess);
        View power = findViewById(R.id.logout);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getPrefManager().clear();
            }
        });
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setTranslationY(1200);
                viewPager.animate()
                        .translationY(0f)
                        .setDuration(600)
                        .setInterpolator(new FastOutSlowInInterpolator());
            }
        });


        Title_Pager_Adapter viewPagerAdapter = new Title_Pager_Adapter(getSupportFragmentManager());

          inbox  = new MessageFragment();
        inbox.setType("inbox");

          outbox  = new MessageFragment();
        outbox.setType("outbox");

        viewPagerAdapter.addFragment(inbox,getString(R.string.inbox));
        viewPagerAdapter.addFragment(outbox,getString(R.string.outbox));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }



    void toolbar_action_setup(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final View notificationbtn = findViewById(R.id.main_toolbar_noti);
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
        View power = findViewById(R.id.logout);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getPrefManager().clear();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home )
            onBackPressed();
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2033 && resultCode==RESULT_OK  ){
            outbox.refresh();
        }
    }
}
