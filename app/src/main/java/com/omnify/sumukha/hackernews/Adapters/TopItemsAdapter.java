package com.omnify.sumukha.hackernews.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omnify.sumukha.hackernews.Interfaces.StoryChoosenListner;
import com.omnify.sumukha.hackernews.Models.ArticleModel;
import com.omnify.sumukha.hackernews.R;

import java.util.ArrayList;

public class TopItemsAdapter extends RecyclerView.Adapter<TopItemsAdapter.StoriesViewHolder>{

    Context context;
    ArrayList<ArticleModel> articleModels;
    StoryChoosenListner storyChoosenListner;


    public TopItemsAdapter(Context context, ArrayList<ArticleModel> articleModels, StoryChoosenListner storyChoosenListner) {
        this.context = context;
        this.articleModels = articleModels;
        this.storyChoosenListner = storyChoosenListner;
    }

    @Override
    public StoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_item, parent, false);

        return new TopItemsAdapter.StoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoriesViewHolder holder, int position) {

        final StoriesViewHolder viewHolder = holder;
        final int pos = position;

        holder.articleTitleTv.setText(articleModels.get(position).getArticleTitle());
        holder.starsCountTv.setText(String.valueOf(articleModels.get(position).getArticleScore()));

        java.util.Date time=new java.util.Date((long)articleModels.get(position).getArticleSubmissionTime()*1000);
        holder.articlePublishTimeTv.setText(String.valueOf(time));
        holder.commentsCountTv.setText(String.valueOf(articleModels.get(position).getTotalNoOfComments()));

        if(articleModels.get(position).isHasURL()){
            holder.articleURLTv.setText(articleModels.get(position).getArticleURL());
        }else{
            holder.articleURLTv.setText("www.org3.com");
        }

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storyChoosenListner.storyChoosen(articleModels.get(pos));

            }
        });

    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    class StoriesViewHolder extends RecyclerView.ViewHolder {

        TextView starsCountTv, commentsCountTv, articleTitleTv,articleURLTv,articlePublishTimeTv;
        CardView rowLayout;
        public StoriesViewHolder(View itemView) {
            super(itemView);

            rowLayout = itemView.findViewById(R.id.row_item);
            starsCountTv =  itemView.findViewById(R.id.no_of_stars_textview);
            commentsCountTv =  itemView.findViewById(R.id.no_of_comments);
            articleTitleTv =  itemView.findViewById(R.id.article_title_textview);
            articleURLTv =  itemView.findViewById(R.id.article_url_textview);
            articlePublishTimeTv = itemView.findViewById(R.id.article_author_textview);
        }
    }

}
