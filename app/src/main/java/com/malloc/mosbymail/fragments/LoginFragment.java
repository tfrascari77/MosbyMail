package com.malloc.mosbymail.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.presenters.LoginPresenter;
import com.malloc.mosbymail.states.LoginViewState;
import com.malloc.mosbymail.utils.Input;
import com.malloc.mosbymail.utils.Toaster;
import com.malloc.mosbymail.views.LoginView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends MvpViewStateFragment<LoginView, LoginPresenter> implements LoginView {

    public final static String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.username)
    MaterialEditText mUsername;

    @BindView(R.id.password)
    MaterialEditText mPassword;

    @BindView(R.id.submit)
    ActionProcessButton mSubmit;

    @OnClick(R.id.submit)
    public void onSubmitPressed() {
        Input.hideKeyboard(getActivity());
        boolean validation = true;

        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        mPassword.setText(getString(R.string.empty));
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.field_required));
            validation = false;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.field_required));
            validation = false;
        }

        if (!validation) {
            return;
        }

        presenter.doLogin(username, password);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @NonNull public ViewState createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showLoginForm();
    }

    @Override
    @NonNull  public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoginForm() {
        ((LoginViewState)viewState).setStateForm();

        resetForm();

        mUsername.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mSubmit.setProgress(0);
    }

    @Override
    public void showLoading() {
        ((LoginViewState)viewState).setStateLoading();

        mUsername.setEnabled(false);
        mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
        mSubmit.setProgress(50);
    }

    @Override
    public void showError() {
        ((LoginViewState)viewState).setStateError();

        mUsername.setEnabled(true);
        mPassword.setEnabled(true);
        mPassword.setText(getString(R.string.empty));
        mSubmit.setEnabled(true);
        mSubmit.setProgress(0);

        Toaster.showToast(getActivity(), R.string.login_error);
    }

    private void resetForm() {
        mUsername.setText(R.string.empty);
        mPassword.setText(R.string.empty);
        mSubmit.setProgress(0);
    }
}
