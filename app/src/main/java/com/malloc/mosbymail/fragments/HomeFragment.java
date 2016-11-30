package com.malloc.mosbymail.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.adapters.PostAdapter;
import com.malloc.mosbymail.presenters.HomePresenter;
import com.malloc.mosbymail.states.HomeViewState;
import com.malloc.mosbymail.utils.Content;
import com.malloc.mosbymail.utils.Dialogs;
import com.malloc.mosbymail.utils.Firebase;
import com.malloc.mosbymail.utils.Navigation;
import com.malloc.mosbymail.views.HomeView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends MvpViewStateFragment<HomeView, HomePresenter> implements HomeView {

    public final static String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.fab)
    FloatingActionButton mTakePictureButton;

    private Uri mPhotoUri = null;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecycler();
        setupFAB();
    }

    private void setupRecycler() {
        final PostAdapter postAdapter = new PostAdapter(getActivity(), Firebase.getPostQuery());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(postAdapter);
    }

    private void setupFAB() {
        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTakePicturePressed();
            }
        });
    }

    private void onTakePicturePressed() {
        File photoFile;
        try {
            photoFile = Content.createImageFile(getActivity());
        } catch (final IOException e) {
            Log.e(TAG, "Error while creating image file - " + e);
            showErrorDialog();
            return;
        }

        mPhotoUri = FileProvider.getUriForFile(getActivity(), Constants.FILE_PROVIDER_AUTHORITY, photoFile);
        if (!Navigation.startTakePicture(this, mPhotoUri)) {
            Dialogs.showErrorDialog(getActivity());
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Navigation.REQUEST_CODE_TAKE_PICTURE:
                onTakePictureResult(resultCode);
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onTakePictureResult(final int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            Navigation.startCreatePost(this, mPhotoUri);
        } else {
            Dialogs.showErrorDialog(getActivity());
        }
    }

    @Override
    public void showFeed() {

    }

    @Override
    public void showErrorDialog() {
        Dialogs.showErrorDialog(getActivity());
    }

    @Override
    @NonNull  public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    @NonNull  public ViewState createViewState() {
        return new HomeViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showFeed();
    }

}
