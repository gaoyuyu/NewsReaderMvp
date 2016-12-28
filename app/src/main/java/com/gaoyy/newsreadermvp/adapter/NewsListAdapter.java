package com.gaoyy.newsreadermvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoyy.newsreadermvp.R;
import com.gaoyy.newsreadermvp.bean.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by gaoyy on 2016/8/24 0024.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    private List<NewsModel.ResultBean.DataBean> data;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }


    public NewsListAdapter(Context context, List<NewsModel.ResultBean.DataBean> data)
    {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_news, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(rootView);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        NewsModel.ResultBean.DataBean news = data.get(position);

        newsViewHolder.itemNewsTitle.setText(news.getTitle());
        newsViewHolder.itemNewsDate.setText(news.getAuthor_name() + "        " + news.getDate());

        //Picasso加载图片
        Picasso.with(context)
                .load(news.getThumbnail_pic_s03())
                .fit()
                .into(newsViewHolder.itemNewsImg);


        if (onItemClickListener != null)
        {
            newsViewHolder.itemNewsLayout.setOnClickListener(new BasicOnClickListener(newsViewHolder));
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder
    {


        private TextView itemNewsTitle;
        private TextView itemNewsDate;
        private ImageView itemNewsImg;
        private RelativeLayout itemNewsLayout;


        public NewsViewHolder(View itemView)
        {
            super(itemView);
            itemNewsTitle = (TextView) itemView.findViewById(R.id.item_news_title);
            itemNewsDate = (TextView) itemView.findViewById(R.id.item_news_date);
            itemNewsImg = (ImageView) itemView.findViewById(R.id.item_news_img);
            itemNewsLayout = (RelativeLayout) itemView.findViewById(R.id.item_news_layout);
        }
    }


    public void updateData(List<NewsModel.ResultBean.DataBean> s)
    {
        this.data = s;
        notifyDataSetChanged();
    }

    private class BasicOnClickListener implements View.OnClickListener
    {
        private NewsViewHolder newsViewHolder;

        public BasicOnClickListener(NewsViewHolder newsViewHolder)
        {
            this.newsViewHolder = newsViewHolder;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_news_layout:
                    onItemClickListener.onItemClick(newsViewHolder.itemNewsLayout, newsViewHolder.getLayoutPosition());
                    break;
            }
        }
    }

}
