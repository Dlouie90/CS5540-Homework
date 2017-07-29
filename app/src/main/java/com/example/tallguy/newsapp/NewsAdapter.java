package com.example.tallguy.newsapp;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallguy.newsapp.data.Contract;
import com.squareup.picasso.Picasso;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "newsadapter";

    // The NewsAdapter constructor that takes in a cursor and a listener and sets them globally
    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
        Log.d(TAG, "NewsAdapter has been created");
}

    // The interface the MainActivity will implement to click on the news articles
    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    // When the viewholder is created, the news_list_item layout will be attached and returned.
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
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
        return cursor.getCount();
    }


    // This viewholder will initialize all the views in the news_list_item and bind the stuff.
    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView author;
        public TextView title;
        public TextView desc;
        public TextView publish;
        public ImageView img;

        // The constructor which sets the views to the ids.
        NewsAdapterViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            title = (TextView) view.findViewById(R.id.news_title);
            desc = (TextView) view.findViewById(R.id.description);
            publish = (TextView) view.findViewById(R.id.published_at);
            img = (ImageView) view.findViewById(R.id.img);
            view.setOnClickListener(this);
            Log.d(TAG, "NewsAdapterViewHolder has been created");
        }

        /*
            The bind method that binds the text using the database via cursor and sets the img
            using Picasso.
         */
        public void bind(int pos) {
            cursor.moveToPosition(pos);
            author.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR))));
            title.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_TITLE))));
            desc.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION))));
            publish.setText(cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT))));
            String imgUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE));
            if (imgUrl != null) {
                Picasso.with(context)
                        .load(imgUrl)
                        .into(img);
            }
            Log.d(TAG + " @imgUrl: ", cursor.getString(cursor.getColumnIndex((Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE))));
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }

}
