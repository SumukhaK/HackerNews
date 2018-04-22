package com.omnify.sumukha.hackernews.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omnify.sumukha.hackernews.Models.ArticleComments;
import com.omnify.sumukha.hackernews.R;

import java.util.ArrayList;

public class AllCommentsAdapter extends RecyclerView.Adapter<AllCommentsAdapter.CommentsViewHolder>{


    Context context;
    ArrayList<ArticleComments> articleComments;

    public AllCommentsAdapter(Context context, ArrayList<ArticleComments> articleComments) {
        this.context = context;
        this.articleComments = articleComments;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_row, parent, false);

        return new AllCommentsAdapter.CommentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {

        java.util.Date time=new java.util.Date((long)articleComments.get(position).getCommentSubmissionTime()*1000);
        holder.dateNuserTv.setText(String.valueOf(time)+"  "+"\u2022 "+articleComments.get(position).getCommentBy());
        holder.commentTextTv.setText(articleComments.get(position).getCommentText());

    }

    @Override
    public int getItemCount() {
        return articleComments.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {

        TextView dateNuserTv, commentTextTv;
        CardView rowLayout;
        public CommentsViewHolder(View itemView) {
            super(itemView);

            rowLayout = itemView.findViewById(R.id.comment_row);
            dateNuserTv =  itemView.findViewById(R.id.time_user_texview);
            commentTextTv =  itemView.findViewById(R.id.comment_textview);

        }
    }

}
