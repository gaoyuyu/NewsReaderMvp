package com.gaoyy.newsreadermvp.api;

import com.gaoyy.newsreadermvp.bean.NewsModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by gaoyy on 2016/12/27.
 */

public interface Api
{
    @GET("index")
    Call<NewsModel> getNewsData(@QueryMap Map<String,String>map);
}
