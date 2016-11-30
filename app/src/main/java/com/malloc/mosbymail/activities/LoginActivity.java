package com.malloc.mosbymail.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.presenters.LoginPresenter;
import com.malloc.mosbymail.utils.Input;
import com.malloc.mosbymail.utils.Navigation;
import com.malloc.mosbymail.views.LoginView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.username)
    MaterialEditText mUsername;

    @BindView(R.id.password)
    MaterialEditText mPassword;

    @BindView(R.id.submit)
    ActionProcessButton mSubmit;

    @OnClick(R.id.submit)
    public void onSubmitPressed() {
        Input.hideKeyboard(this);
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

    @OnEditorAction(R.id.password)
    boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onSubmitPressed();
            return true;
        }
        return false;
    }

    @OnTextChanged(value = {R.id.username, R.id.password}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(final Editable editable) {
        mUsername.setError(null);
        mPassword.setError(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    @NonNull  public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoginForm() {
        mUsername.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mSubmit.setProgress(0);
    }

    @Override
    public void showLoading() {
        mUsername.setEnabled(false);
        mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
        mSubmit.setProgress(50);
    }

    @Override
    public void showError() {
        mUsername.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mSubmit.setProgress(0);

        mUsername.setError(getString(R.string.login_error));
    }

    @Override
    public void onSuccess() {
        Navigation.startMain(this);
    }
}
