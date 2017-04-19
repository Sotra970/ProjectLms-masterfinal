package com.example.ahmed.projectlms.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmed.projectlms.Adapter.TitlePagerAdapter;
import com.example.ahmed.projectlms.Fragment.MessageTabsFragment;
import com.example.ahmed.projectlms.Fragment.ProgressFragment;
import com.example.ahmed.projectlms.Fragment.StudentMessageFragment;
import com.example.ahmed.projectlms.Fragment.TeacherMessageFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;
import com.example.ahmed.projectlms.WrapContentViewPager;

/**
 * Created by Mohab on 4/7/2017.
 */

public class MessageCompasActivity extends AppCompatActivity
{
    WrapContentViewPager viewPagerProgress;
    TitlePagerAdapter titlePagerAdapter;
    TeacherMessageFragment teacherMessageFragment = new TeacherMessageFragment();
    StudentMessageFragment studentMessageFragment= new StudentMessageFragment();

    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        viewPagerProgress = (WrapContentViewPager) findViewById(R.id.view_pager_progress);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        titlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager());
       /* progressQuizFragment.setExtras(extras);
        progressAssignmentFragment.setExtras(extras);
       */
        titlePagerAdapter.addFragment(teacherMessageFragment, "To Teacher");
        titlePagerAdapter.addFragment(studentMessageFragment, "To Student");

        viewPagerProgress.setAdapter(titlePagerAdapter);
        tabLayout.setupWithViewPager(viewPagerProgress);

        viewPagerProgress.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerProgress.reMeasureCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
