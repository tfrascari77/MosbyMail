package com.malloc.mosbymail.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.fragments.HomeFragment;
import com.malloc.mosbymail.fragments.SplashFragment;
import com.malloc.mosbymail.presenters.MainPresenter;
import com.malloc.mosbymail.utils.Dialogs;
import com.malloc.mosbymail.utils.Navigation;
import com.malloc.mosbymail.utils.Toaster;
import com.malloc.mosbymail.views.MainView;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.doLaunch();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showSplash() {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if (homeFragment != null && homeFragment.isAdded()) {
            transaction.remove(homeFragment);
        }

        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager().findFragmentByTag(SplashFragment.TAG);
        if (splashFragment == null) {
            splashFragment = new SplashFragment();
        }
        if (!splashFragment.isAdded()) {
            transaction.add(android.R.id.content, splashFragment, SplashFragment.TAG);
        }

        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
        setActionBarVisibility(false);
    }

    @Override
    public void showHome() {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager().findFragmentByTag(SplashFragment.TAG);
        if (splashFragment != null && splashFragment.isAdded()) {
            transaction.remove(splashFragment);
        }

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (!homeFragment.isAdded()) {
            transaction.add(android.R.id.content, homeFragment, HomeFragment.TAG);
        }

        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        setActionBarVisibility(true);
        setTitle(R.string.home);
    }

    @Override
    public void showErrorDialog() {
        Dialogs.showErrorDialog(this);
    }

    @Override
    public void showCreatePostSuccess() {
        Toaster.showToast(this, R.string.post_created_successfully);
    }

    @Override
    public void onAuthenticationRequired() {
        Navigation.startLogin(this);
    }

    private void setActionBarVisibility(final boolean visible) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (visible && actionBar.isShowing()) {
                actionBar.show();
            }
            if (!visible && !actionBar.isShowing()) {
                actionBar.hide();
            }
        }
    }
}
