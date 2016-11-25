package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface MainView extends MvpView {

    // Show the splash screen
    void showSplash();

    // Show the login screen
    void showLogin();

    // Show the home screen
    void showHome();
}