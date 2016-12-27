package com.gaoyy.newsreadermvp.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoyy.newsreadermvp.R;
import com.gaoyy.newsreadermvp.adapter.NewsPagerAdapter;
import com.gaoyy.newsreadermvp.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements MainContract.View
{
    private static final String LOG_TAG= MainFragment.class.getSimpleName();


    private int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
            R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
    private List<NewsFragment> fragmentList = new ArrayList<NewsFragment>();
    private NewsPagerAdapter newsPagerAdapter;


    private MainContract.Presenter mMainPresenter;


    private View rootView;

    private TabLayout mainTablayout;
    private ViewPager mainViewpager;

    private void assignViews(View rootView)
    {
        mainTablayout = (TabLayout) rootView.findViewById(R.id.main_tablayout);
        mainViewpager = (ViewPager) rootView.findViewById(R.id.main_viewpager);
    }


    public MainFragment()
    {
    }

    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            //get Params here
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        assignViews(rootView);

        for (int i = 0; i < newsType.length; i++)
        {
            Bundle bundle = new Bundle();
            NewsFragment fragment = new NewsFragment();
            bundle.putInt("type",newsType[i]);
            fragment.setArguments(bundle);
            fragmentList.add(i, fragment);
        }

        newsPagerAdapter = new NewsPagerAdapter(getActivity(),getActivity().getSupportFragmentManager(), newsType, fragmentList);
        mainViewpager.setAdapter(newsPagerAdapter);
        mainViewpager.setOffscreenPageLimit(1);
        mainTablayout.setBackgroundColor(getResources().getColor(R.color.white));
        mainTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mainTablayout.setupWithViewPager(mainViewpager);
        mainTablayout.setTabsFromPagerAdapter(newsPagerAdapter);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG,"onResume");
        mMainPresenter.start();
    }


    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mMainPresenter = presenter;
        }
    }
}
