package com.gaoyy.newsreadermvp.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.gaoyy.newsreadermvp.api.Api;
import com.gaoyy.newsreadermvp.api.Constant;
import com.gaoyy.newsreadermvp.bean.News;
import com.gaoyy.newsreadermvp.bean.NewsModel;
import com.gaoyy.newsreadermvp.newsdetail.NewsDetailActivity;
import com.gaoyy.newsreadermvp.util.Okhttp3Utils;
import com.gaoyy.newsreadermvp.util.OkhttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class NewsPresenter implements NewsContract.Presenter
{
    private static final String LOG_TAG = NewsPresenter.class.getSimpleName();
    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View mNewsView)
    {
        this.mNewsView = mNewsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void loadNewsData(Context context, String url)
    {
        mNewsView.showLoading("loading");
        OkhttpUtils.getAsync(context, url, "get_news", new OkhttpUtils.ResultCallback()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                if (!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                Log.e("NewsPresenter", "e---->" + e.toString());
            }

            @Override
            public void onSuccess(String body)
            {
                if (!mNewsView.isActive())
                {
                    return;
                }
                mNewsView.hideLoading();
                Log.e("NewsPresenter", "e---->" + body.toString());
                List<News> newsList = null;
                JSONObject jsonObject = null;
                JSONObject news = null;
                try
                {
                    jsonObject = new JSONObject(body);
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
    public void loadNewsData2(Map<String, String> map)
    {
        mNewsView.showLoading("loading");

        OkHttpClient okHttpClient = Okhttp3Utils.getOKhttpClientSingletonInstance();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_NEWS_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<NewsModel> call = api.getNewsData(map);
        call.enqueue(new Callback<NewsModel>()
        {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response)
            {
                mNewsView.hideLoading();
                if (response.isSuccessful() && response.body() != null)
                {
                    Log.e(LOG_TAG, response.body().getResult().getData().toString());
                    List<NewsModel.ResultBean.DataBean> list = response.body().getResult().getData();
                    mNewsView.showNews2(list);
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t)
            {
                mNewsView.hideLoading();

            }
        });
    }

    @Override
    public void onItemClick(Context context,NewsModel.ResultBean.DataBean news,ActivityOptionsCompat options)
    {
        Intent intent = new Intent();
        intent.setClass(context, NewsDetailActivity.class);
        intent.putExtra("title",news.getTitle());
        intent.putExtra("titleImg",news.getThumbnail_pic_s03());
        intent.putExtra("url",news.getUrl());
        context.startActivity(intent,options.toBundle());
    }

    @Override
    public void start()
    {
//        loadNewsData();
    }
}
