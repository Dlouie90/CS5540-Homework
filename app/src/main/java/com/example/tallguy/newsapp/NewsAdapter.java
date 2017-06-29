package com.example.tallguy.newsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private ArrayList<NewsItem> newsData;

    public NewsAdapter(ArrayList<NewsItem> list) {
        this.newsData = list;
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView title;
        public TextView desc;
        public TextView url;
        public TextView urlToImage;
        public TextView publishedAt;

        public NewsAdapterViewHolder(View view) {
            super(view);
            author = (TextView)view.findViewById(R.id.author);
            title = (TextView)view.findViewById(R.id.news_title);
            desc = (TextView)view.findViewById(R.id.description);
            url = (TextView)view.findViewById(R.id.url);
            urlToImage = (TextView)view.findViewById(R.id.url_to_image);
            publishedAt = (TextView)view.findViewById(R.id.published_at);
        }

        public void bind(int pos) {
            NewsItem item = newsData.get(pos);
            author.setText(item.getAuthor());
            title.setText(item.getTitle());
            desc.setText(item.getDescription());
            url.setText(item.getUrl());
            urlToImage.setText(item.getUrlToImage());
            publishedAt.setText(item.getPublishedAt());
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (newsData == null) {
            return 0;
        }
        return newsData.size();
    }

}
