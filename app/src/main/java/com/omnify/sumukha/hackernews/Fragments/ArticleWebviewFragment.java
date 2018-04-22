package com.omnify.sumukha.hackernews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.omnify.sumukha.hackernews.R;

public class ArticleWebviewFragment extends Fragment {


    View articleWebView;
    TextView noUrlForArticleTv;
    WebView articleLoadWebview;
    public static String articleURL;
    public static boolean articleHasURL = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        articleWebView = inflater.inflate(R.layout.article_webview_fragment,container,false);
        noUrlForArticleTv = articleWebView.findViewById(R.id.urlinfo_textview);
        articleLoadWebview = articleWebView.findViewById(R.id.article_webview);

        if(articleHasURL){

            noUrlForArticleTv.setVisibility(View.GONE);
            articleLoadWebview.setVisibility(View.VISIBLE);
            articleLoadWebview.loadUrl(articleURL); //If you click on any link inside the webpage of the WebView, that page will not be loaded inside your WebView. In order to do that you need to extend your class from WebViewClient and override its method.
        }else{

            noUrlForArticleTv.setVisibility(View.VISIBLE);
            articleLoadWebview.setVisibility(View.GONE);
        }

        return articleWebView;
    }
}
