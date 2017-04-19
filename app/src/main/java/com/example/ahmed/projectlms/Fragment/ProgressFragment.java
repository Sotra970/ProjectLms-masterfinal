package com.example.ahmed.projectlms.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.projectlms.Activity.MessActivity;
import com.example.ahmed.projectlms.Activity.NotificationActivity;
import com.example.ahmed.projectlms.Adapter.TitlePagerAdapter;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/8/2017.
 */

public class ProgressFragment extends Fragment
{
    View view;
    ViewPager viewPagerProgress;
    TitlePagerAdapter titlePagerAdapter;
    ProgressQuizFragment progressQuizFragment = new ProgressQuizFragment();
    ProgressAssignmentFragment progressAssignmentFragment = new ProgressAssignmentFragment();
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
        view = inflater.inflate(R.layout.fragment_progress, container, false);
        viewPagerProgress = (ViewPager) view.findViewById(R.id.view_pager_progress);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        titlePagerAdapter = new TitlePagerAdapter(getChildFragmentManager());
        progressQuizFragment.setExtras(extras);
        progressAssignmentFragment.setExtras(extras);
        titlePagerAdapter.addFragment(progressQuizFragment, "Quiz");
        titlePagerAdapter.addFragment(progressAssignmentFragment, "Assignment");

        viewPagerProgress.setAdapter(titlePagerAdapter);
        tabLayout.setupWithViewPager(viewPagerProgress);
        toolbar_action_setup(getString(R.string.myprogress_text));
        return view;
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
