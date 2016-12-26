package com.gaoyy.newsreadermvp.news;

import android.content.Context;

import com.gaoyy.newsreadermvp.BasePresenter;
import com.gaoyy.newsreadermvp.BaseView;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class NewsContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading(String msg);
        void hideLoading();
        boolean isActive();
    }
    interface Presenter extends BasePresenter
    {
        void loadNewsData(Context context, String url);

    }
}
