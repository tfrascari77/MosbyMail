package com.malloc.mosbymail.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.views.LoginView;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public LoginPresenter() {
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
                        getView().onSuccess();
                    }
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
