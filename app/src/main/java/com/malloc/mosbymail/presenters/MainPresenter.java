package com.malloc.mosbymail.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.events.CreatePostEvent;
import com.malloc.mosbymail.events.LogoutEvent;
import com.malloc.mosbymail.views.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainPresenter extends MvpBasePresenter<MainView> {

    private final EventBus mEventBus;

    public MainPresenter() {
        mEventBus = EventBus.getDefault();
    }

    public void doLaunch() {
        if (isViewAttached()) {
            getView().showSplash();
        }

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            getView().onAuthenticationRequired();
        } else {
            getView().showHome();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final LogoutEvent event) {
        if (isViewAttached()) {
            getView().onAuthenticationRequired();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final CreatePostEvent event) {
        if (isViewAttached()) {
            if (event.isSuccess()) {
                getView().showCreatePostSuccess();
            } else {
                getView().showErrorDialog();
            }
        }
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        mEventBus.register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mEventBus.unregister(this);
    }
}
