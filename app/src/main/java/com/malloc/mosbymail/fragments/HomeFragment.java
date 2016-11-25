package com.malloc.mosbymail.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.presenters.HomePresenter;
import com.malloc.mosbymail.states.HomeViewState;
import com.malloc.mosbymail.views.HomeView;

import butterknife.ButterKnife;

public class HomeFragment extends MvpViewStateFragment<HomeView, HomePresenter> implements HomeView {

    public final static String TAG = HomeFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                presenter.doLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public ViewState createViewState() {
        return new HomeViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

}
