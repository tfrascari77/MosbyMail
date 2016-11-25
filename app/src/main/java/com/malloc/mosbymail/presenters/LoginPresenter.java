package com.malloc.mosbymail.presenters;

import android.accounts.AccountManager;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.views.LoginView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private final AccountManager mAccountManager;
    private final EventBus mEventBus;

    @Inject public LoginPresenter(final AccountManager accountManager, final EventBus eventBus) {
        mAccountManager = accountManager;
        mEventBus = eventBus;
    }

}
