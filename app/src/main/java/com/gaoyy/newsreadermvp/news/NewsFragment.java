package com.gaoyy.newsreadermvp.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gaoyy.newsreadermvp.R;
import com.gaoyy.newsreadermvp.adapter.NewsListAdapter;
import com.gaoyy.newsreadermvp.api.Constant;
import com.gaoyy.newsreadermvp.bean.News;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsContract.View, NewsListAdapter.OnItemClickListener
{
    private NewsContract.Presenter mNewsPresenter;
    private View rootView;
    private NewsListAdapter newsListAdapter;
    private List<News> list = new ArrayList<>();

    private RecyclerView fragmentNewsRv;
    private ProgressBar fragmentNewsProgressBar;

    private void assignViews(View rootView)
    {
        fragmentNewsRv = (RecyclerView) rootView.findViewById(R.id.fragment_news_rv);
        fragmentNewsProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_news_progressBar);
    }


    private static final String LOG_TAG = NewsFragment.class.getSimpleName();


    public NewsFragment()
    {
        // Required empty public constructor
    }

    public static NewsFragment newInstance()
    {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if(rootView == null )
        {
            rootView = inflater.inflate(R.layout.fragment_news, container, false);
        }
        assignViews(rootView);
        String url = Constant.NEWS_URL + "?type=" + getResources().getString(getArguments().getInt("type")) + "&key=" + Constant.APPKEY;
        newsListAdapter = new NewsListAdapter(getActivity(), list);
        fragmentNewsRv.setAdapter(newsListAdapter);
        fragmentNewsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNewsPresenter = new NewsPresenter(this);
        mNewsPresenter.loadNewsData(getActivity(), url);
        newsListAdapter.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void showLoading(String msg)
    {
        fragmentNewsProgressBar.setVisibility(View.VISIBLE);
        fragmentNewsRv.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading()
    {
        fragmentNewsProgressBar.setVisibility(View.GONE);
        fragmentNewsRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNews(List<News> list)
    {
        newsListAdapter.updateData(list);
    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mNewsPresenter = presenter;
        }
    }

    @Override
    public void onItemClick(View view, int position)
    {
        mNewsPresenter.onItemClick(view,position);
    }
}
