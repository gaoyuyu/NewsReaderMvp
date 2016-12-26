package com.gaoyy.newsreadermvp.news;

import android.content.Context;
import android.util.Log;

import com.gaoyy.newsreadermvp.util.OkhttpUtils;

import okhttp3.Request;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class NewsPresenter implements NewsContract.Presenter
{
    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View mNewsView)
    {
        this.mNewsView = mNewsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void loadNewsData(Context context,String url)
    {
        OkhttpUtils.getAsync(context, url, "get_news", new OkhttpUtils.ResultCallback()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                Log.e("NewsPresenter","e---->"+e.toString());
            }

            @Override
            public void onSuccess(String body)
            {
                Log.e("NewsPresenter","e---->"+body.toString());
            }
        });
    }

    @Override
    public void start()
    {
//        loadNewsData();
    }
}
