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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements MainContract.View
{

    private int[] newsType = {R.string.top, R.string.shehui, R.string.guonei, R.string.guoji,
            R.string.yule, R.string.tiyu, R.string.junshi, R.string.keji, R.string.caijing, R.string.shishang};
    private List<NewsFragment> fragmentList = new ArrayList<NewsFragment>();
    private NewsPagerAdapter newsPagerAdapter;


    private MainContract.Presenter mMainPresenter;


    private View rootView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
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

    public static MainFragment newInstance(String param1, String param2)
    {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.i("Main", mParam1 + "===" + mParam2);
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
        Log.i("MainFragment", "setPresenter");
        if (presenter != null)
        {
            mMainPresenter = presenter;
        }
    }
}
