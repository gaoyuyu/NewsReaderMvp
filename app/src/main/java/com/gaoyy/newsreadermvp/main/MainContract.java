package com.gaoyy.newsreadermvp.main;

import com.gaoyy.newsreadermvp.BasePresenter;
import com.gaoyy.newsreadermvp.BaseView;

/**
 * Created by gaoyy on 2016/12/26.
 */

public class MainContract
{
    interface View extends BaseView<Presenter>
    {
        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {

    }

}
