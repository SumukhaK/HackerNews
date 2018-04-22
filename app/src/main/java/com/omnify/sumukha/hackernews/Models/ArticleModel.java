package com.omnify.sumukha.hackernews.Models;

import java.util.ArrayList;

public class ArticleModel {

    private String articleBy;
    private String articleTitle;
    private int articleSubmissionTime;
    private int articleScore;
    private int totalNoOfComments;
    private boolean hasURL;
    private boolean hasKids;
    //private ArrayList<ArticleComments> allComments;
    private Integer[] allCommentsIds;
    private String articleURL;


    public String getArticleBy() {
        return articleBy;
    }

    public void setArticleBy(String articleBy) {
        this.articleBy = articleBy;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public int getArticleSubmissionTime() {
        return articleSubmissionTime;
    }

    public void setArticleSubmissionTime(int articleSubmissionTime) {
        this.articleSubmissionTime = articleSubmissionTime;
    }

    public int getArticleScore() {
        return articleScore;
    }

    public void setArticleScore(int articleScore) {
        this.articleScore = articleScore;
    }

    public int getTotalNoOfComments() {
        return totalNoOfComments;
    }

    public void setTotalNoOfComments(int totalNoOfComments) {
        this.totalNoOfComments = totalNoOfComments;
    }

    public Integer[] getAllCommentsIds() {
        return allCommentsIds;
    }

    public void setAllCommentsIds(Integer[] allCommentsIds) {
        this.allCommentsIds = allCommentsIds;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public boolean isHasURL() {
        return hasURL;
    }

    public void setHasURL(boolean hasURL) {
        this.hasURL = hasURL;
    }

    public boolean isHasKids() {
        return hasKids;
    }

    public void setHasKids(boolean hasKids) {
        this.hasKids = hasKids;
    }

}
