package com.gaoyy.newsreadermvp.news;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.gaoyy.newsreadermvp.bean.News;
import com.gaoyy.newsreadermvp.util.OkhttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Request;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class NewsPresenter implements NewsContract.Presenter
{
    private static final String LOG_TAG= NewsPresenter.class.getSimpleName();
    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View mNewsView)
    {
        this.mNewsView = mNewsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void loadNewsData(Context context,String url)
    {
        mNewsView.showLoading("loading");
        OkhttpUtils.getAsync(context, url, "get_news", new OkhttpUtils.ResultCallback()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                if(!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                Log.e("NewsPresenter","e---->"+e.toString());
            }

            @Override
            public void onSuccess(String body)
            {
                if(!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                Log.e("NewsPresenter","e---->"+body.toString());
                List<News> newsList = null;
                JSONObject jsonObject  = null;
                JSONObject news = null;
                try
                {
                    jsonObject  = new JSONObject(body);
                    news = (JSONObject) jsonObject.get("result");
                    Gson gson = new Gson();
                    newsList = gson.fromJson(news.get("data").toString(),
                            new TypeToken<List<News>>()
                            {
                            }.getType());
                    mNewsView.showNews(newsList);
                }
                catch (JSONException e)
                {
                    Log.i(LOG_TAG, "catch Exception when getNewsJSONObject：" + e.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Log.e(LOG_TAG,"onItemClick"+position);
    }

    @Override
    public void start()
    {
//        loadNewsData();
    }
}
