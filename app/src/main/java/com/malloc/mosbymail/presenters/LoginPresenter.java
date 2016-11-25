package com.malloc.mosbymail.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.events.LoginEvent;
import com.malloc.mosbymail.views.LoginView;

import org.greenrobot.eventbus.EventBus;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private final EventBus mEventBus;

    public LoginPresenter() {
        mEventBus = EventBus.getDefault();
    }

    public void doLogin(final String username, final String password) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseAuth.removeAuthStateListener(this);
                if (firebaseAuth.getCurrentUser() != null) {
                    if (isViewAttached()) {
                        getView().showLoginForm();
                    }
                    mEventBus.post(new LoginEvent());
                } else {
                    if (isViewAttached()) {
                        getView().showError();
                    }
                }
            }
        });
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password);
    }

}
