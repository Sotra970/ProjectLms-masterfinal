package com.example.ahmed.projectlms.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmed.projectlms.Fragment.DownlaodableFragment;
import com.example.ahmed.projectlms.Models.Class_model;
import com.example.ahmed.projectlms.R;

/**
 * Created by Mohab on 4/8/2017.
 */

public class DownloadableActivity extends AppCompatActivity
{
    public static final String EXTRAS_CLASS = "extrasClass";
    Class_model class_model = new Class_model();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadable);
        class_model = (Class_model) getIntent().getSerializableExtra(EXTRAS_CLASS);
        Fragment fragment = new DownlaodableFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.downloadable_fragment_container, fragment)
                .commit();
    }
}
