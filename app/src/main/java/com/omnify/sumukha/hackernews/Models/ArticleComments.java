package com.omnify.sumukha.hackernews.Models;

import java.util.ArrayList;

public class ArticleComments {


    private String commentBy;
    private int commentSubmissionTime;
    private int commentParent;
    private int commentId;
    private String commentText;


    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public int getCommentSubmissionTime() {
        return commentSubmissionTime;
    }

    public void setCommentSubmissionTime(int commentSubmissionTime) {
        this.commentSubmissionTime = commentSubmissionTime;
    }

    public int getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(int commentParent) {
        this.commentParent = commentParent;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
