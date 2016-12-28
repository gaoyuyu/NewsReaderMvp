package com.gaoyy.newsreadermvp.news;

import android.content.Context;

import com.gaoyy.newsreadermvp.BasePresenter;
import com.gaoyy.newsreadermvp.BaseView;
import com.gaoyy.newsreadermvp.bean.News;
import com.gaoyy.newsreadermvp.bean.NewsModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class NewsContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading(String msg);

        void hideLoading();

        void showNews(List<News> list);
        void showNews2(List<NewsModel.ResultBean.DataBean> list);

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        void loadNewsData(Context context, String url);
        void loadNewsData2(Map<String,String> map);

        void onItemClick(android.view.View view, int position);

    }
}
