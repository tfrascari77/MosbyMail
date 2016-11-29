package com.malloc.mosbymail.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.fragments.HomeFragment;
import com.malloc.mosbymail.fragments.LoginFragment;
import com.malloc.mosbymail.fragments.SplashFragment;
import com.malloc.mosbymail.presenters.MainPresenter;
import com.malloc.mosbymail.views.MainView;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    private SplashFragment mSplashFragment;
    private LoginFragment mLoginFragment;
    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragments();
        presenter.doLaunch();
    }

    private void setupFragments() {
        mSplashFragment = (SplashFragment) getSupportFragmentManager().findFragmentByTag(SplashFragment.TAG);
        if (mSplashFragment == null) {
            mSplashFragment = new SplashFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mSplashFragment, SplashFragment.TAG).commit();
        }

        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mLoginFragment, LoginFragment.TAG).commit();
        }

        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mHomeFragment, HomeFragment.TAG).commit();
        }

        getSupportFragmentManager().executePendingTransactions();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showSplash() {
        getSupportFragmentManager().beginTransaction()
                .hide(mLoginFragment)
                .hide(mHomeFragment)
                .show(mSplashFragment)
                .commit();
        setActionBarVisibility(false);
    }

    @Override
    public void showLogin() {
       getSupportFragmentManager().beginTransaction()
               .remove(mSplashFragment)
               .remove(mHomeFragment)
               .show(mLoginFragment)
               .commit();
        setActionBarVisibility(false);
    }

    @Override
    public void showHome() {
        setTitle(R.string.home);
        setActionBarVisibility(true);
        getSupportFragmentManager().beginTransaction()
                .hide(mSplashFragment)
                .hide(mLoginFragment)
                .show(mHomeFragment)
                .commit();
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
