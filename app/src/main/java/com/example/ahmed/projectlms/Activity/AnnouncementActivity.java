package com.example.ahmed.projectlms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ahmed.projectlms.Fragment.AnnouncementFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/4/2017.
 */

public class AnnouncementActivity extends AppCompatActivity
{

    public static final String EXTRAS_CLASS = "extrasClass";
    Class_model class_model = new Class_model();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        class_model = (Class_model) getIntent().getSerializableExtra(EXTRAS_CLASS);

        Fragment fragment = new AnnouncementFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.announcement_fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();

    }

}
