package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface CommentActionBarView extends MvpView {

    // Show the create comment form
    void showCreateCommentForm();

    // Show the loading
    void showLoading();

    // Show error
    void showError();
}
