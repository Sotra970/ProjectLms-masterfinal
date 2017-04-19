package com.example.ahmed.projectlms.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.projectlms.Adapter.TitlePagerAdapter;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/8/2017.
 */

public class MessageTabsFragment extends Fragment
{
    View view;
    ViewPager viewPagerProgress;
    TitlePagerAdapter titlePagerAdapter;
    TeacherMessageFragment teacherMessageFragment = new TeacherMessageFragment();
    StudentMessageFragment studentMessageFragment= new StudentMessageFragment();
    Class_model extras ;
    public Class_model getExtras() {
        return extras;
    }

    public void setExtras(Class_model extras) {
        this.extras = extras;
    }

    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_message_tabs, container, false);
        viewPagerProgress = (ViewPager) view.findViewById(R.id.view_pager_progress);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        titlePagerAdapter = new TitlePagerAdapter(getChildFragmentManager());
       /* progressQuizFragment.setExtras(extras);
        progressAssignmentFragment.setExtras(extras);
       */
       titlePagerAdapter.addFragment(teacherMessageFragment, "To Teacher");
        titlePagerAdapter.addFragment(studentMessageFragment, "To Student");

        viewPagerProgress.setAdapter(titlePagerAdapter);
        tabLayout.setupWithViewPager(viewPagerProgress);

        return view;
    }
}
