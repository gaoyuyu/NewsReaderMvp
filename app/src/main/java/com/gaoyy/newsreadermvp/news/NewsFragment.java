package com.gaoyy.newsreadermvp.news;


import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
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
import com.gaoyy.newsreadermvp.bean.NewsModel;
import com.gaoyy.newsreadermvp.util.TransitionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment implements NewsContract.View, NewsListAdapter.OnItemClickListener
{
    private NewsContract.Presenter mNewsPresenter;
    private View rootView;
    private NewsListAdapter newsListAdapter;
    private List<NewsModel.ResultBean.DataBean> list = new ArrayList<>();

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
        Map<String,String> map = new HashMap<>();
        map.put("type",getResources().getString(getArguments().getInt("type")));
        map.put("key",Constant.APPKEY);
        mNewsPresenter.loadNewsData2(map);
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
//        newsListAdapter.updateData(list);
    }

    @Override
    public void showNews2(List<NewsModel.ResultBean.DataBean> list)
    {
        newsListAdapter.updateData(list);
        this.list = list;
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
        NewsModel.ResultBean.DataBean news = (NewsModel.ResultBean.DataBean)view.getTag();

        View img = view.findViewById(R.id.item_news_img);
        View title = view.findViewById(R.id.item_news_title);

        Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants
                (getActivity(), false,new Pair<>(img, "img"),new Pair<>(title, "title"));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);

        mNewsPresenter.onItemClick(getActivity(),news,options);
    }
}
