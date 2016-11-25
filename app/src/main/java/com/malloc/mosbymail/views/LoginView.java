package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    // Show the login form
    void showLoginForm();

    // Show the loading animation
    void showLoading();

    // Login successful
    void onSuccess();

    // Error occurred
    void onError();
}
