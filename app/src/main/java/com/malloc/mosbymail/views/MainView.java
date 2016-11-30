package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface MainView extends MvpView {

    // Show the splash screen
    void showSplash();

    // Show the home screen
    void showHome();

    // Show the create post success toast
    void showCreatePostSuccess();

    // Show the error dialog
    void showErrorDialog();

    // Authentication is required
    void onAuthenticationRequired();
}
