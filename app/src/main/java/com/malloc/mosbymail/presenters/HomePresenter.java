package com.malloc.mosbymail.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.events.LogoutEvent;
import com.malloc.mosbymail.views.HomeView;

import org.greenrobot.eventbus.EventBus;

public class HomePresenter extends MvpBasePresenter<HomeView> {

    private final static String TAG = HomePresenter.class.getSimpleName();

    private final EventBus mEventBus;

    public HomePresenter() {
        mEventBus = EventBus.getDefault();
    }

    public void doLogout() {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseAuth.removeAuthStateListener(this);
                mEventBus.post(new LogoutEvent());
            }
        });
        firebaseAuth.signOut();
    }
}
