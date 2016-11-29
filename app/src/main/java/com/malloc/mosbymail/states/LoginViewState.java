package com.malloc.mosbymail.states;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.malloc.mosbymail.views.LoginView;

public class LoginViewState implements ViewState<LoginView> {

    private final static int STATE_FORM = 1;
    private final static int STATE_LOADING = 2;
    private final static int STATE_ERROR = 3;

    private int mState = STATE_FORM;

    @Override
    public void apply(LoginView view, boolean retained) {
        switch(mState) {
            case STATE_FORM:
                view.showLoginForm();
                break;
            case STATE_LOADING:
                view.showLoading();
                break;
            case STATE_ERROR:
                view.showError();
                break;
        }
    }

    public void setStateForm() {
        mState = STATE_FORM;
    }

    public void setStateLoading() {
        mState = STATE_LOADING;
    }

    public void setStateError() {
        mState = STATE_ERROR;
    }
}
