package com.omnify.sumukha.hackernews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnify.sumukha.hackernews.Adapters.AllCommentsAdapter;
import com.omnify.sumukha.hackernews.Interfaces.AllCommentsRetrieveListner;
import com.omnify.sumukha.hackernews.Models.ArticleComments;
import com.omnify.sumukha.hackernews.R;

import java.util.ArrayList;

public class ArticleCommentsFragment extends Fragment {


    View commentsView;
    RecyclerView commentsList;
    public static ArrayList<ArticleComments> articleComments;
    AllCommentsAdapter allCommentsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        commentsView = inflater.inflate(R.layout.article_comments_fragment,container,false);
        commentsList = commentsView.findViewById(R.id.comments_rview);

        commentsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        allCommentsAdapter = new AllCommentsAdapter(getActivity(),articleComments);
        commentsList.setAdapter(allCommentsAdapter);


        return commentsView;
    }


}
