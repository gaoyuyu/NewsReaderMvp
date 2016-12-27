package com.gaoyy.newsreadermvp.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.gaoyy.newsreadermvp.R;
import com.gaoyy.newsreadermvp.util.ActivityUtils;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private FrameLayout contentFrame;

    private void assignViews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initToolbar();

        initFragment();
    }

    private void initFragment()
    {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mainFragment == null)
        {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mainFragment, R.id.content_frame);
        }
        new MainPresenter(mainFragment);


    }

    private void initToolbar()
    {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }
}
