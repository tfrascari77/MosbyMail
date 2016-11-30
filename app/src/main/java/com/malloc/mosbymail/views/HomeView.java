package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface HomeView extends MvpView {

    // Show feed
    void showFeed();

    // Show error dialog
    void showErrorDialog();

}
