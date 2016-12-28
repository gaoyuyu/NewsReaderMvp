package com.gaoyy.newsreadermvp.newsdetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoyy.newsreadermvp.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity
{
    private CollapsingToolbarLayout newsDetailCollapsingToolbarLayout;
    private Toolbar newsDetailToolbar;
    private RelativeLayout newsDetailHead;
    private ImageView newsDetailImg;
    private TextView newsDetailTitle;
    private WebView newsDetailWebview;

    private void assignViews() {
        newsDetailCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.news_detail_collapsingToolbarLayout);
        newsDetailToolbar = (Toolbar) findViewById(R.id.news_detail_toolbar);
        newsDetailHead = (RelativeLayout) findViewById(R.id.news_detail_head);
        newsDetailImg = (ImageView) findViewById(R.id.news_detail_img);
        newsDetailTitle = (TextView) findViewById(R.id.news_detail_title);
        newsDetailWebview = (WebView) findViewById(R.id.news_detail_webview);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        assignViews();


        newsDetailCollapsingToolbarLayout.setTitleEnabled(false);
        newsDetailToolbar.setTitle("");
        newsDetailToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        setSupportActionBar(newsDetailToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String title = getIntent().getStringExtra("title");
        String imgUrl = getIntent().getStringExtra("titleImg");
        String url = getIntent().getStringExtra("url");


        Picasso.with(this).load(imgUrl).into(newsDetailImg);

        newsDetailTitle.setText(title);

        setWebView(url);



    }

    private void setWebView(String newsUrl)
    {
        newsDetailWebview.getSettings().setJavaScriptEnabled(true);
        newsDetailWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        newsDetailWebview.loadUrl(newsUrl);

        newsDetailWebview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
