package com.malloc.mosbymail.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.layout.MvpFrameLayout;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.presenters.CommentActionBarPresenter;
import com.malloc.mosbymail.utils.Input;
import com.malloc.mosbymail.utils.Toaster;
import com.malloc.mosbymail.views.CommentActionBarView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class CommentActionBar extends MvpFrameLayout<CommentActionBarView, CommentActionBarPresenter> implements CommentActionBarView {

    @BindView(R.id.input_comment)
    MaterialEditText mCommentInput;

    @BindView(R.id.send_comment)
    ImageView mSendComment;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @OnClick(R.id.send_comment)
    public void onSendCommentPressed() {
        final String commentText = mCommentInput.getText().toString();
        mCommentInput.setText(R.string.empty);
        Input.hideKeyboard(getContext(), mCommentInput);

        presenter.doCreateComment(commentText, mPostId);
    }

    @OnEditorAction(R.id.input_comment)
    public boolean onCommentInputDonePressed(final TextView textView, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onSendCommentPressed();
            return true;
        }
        return false;
    }

    @OnTextChanged(value = R.id.input_comment, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCommentInputTextChanged(final Editable editable) {
        mSendComment.setEnabled(!TextUtils.isEmpty(mCommentInput.getText().toString()));
    }

    private String mPostId;

    public CommentActionBar(Context context) {
        this(context, null);
    }

    public CommentActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_comment_actionbar, this);
        if (!isInEditMode()) {
            ButterKnife.bind(this, this);
        }
    }

    @Override
    public CommentActionBarPresenter createPresenter() {
        return new CommentActionBarPresenter();
    }

    public void setPostId(final String postId) {
        mPostId = postId;
    }

    @Override
    public void showCreateCommentForm() {
        mProgressBar.setVisibility(View.GONE);
        mSendComment.setVisibility(View.VISIBLE);
        mCommentInput.setText(R.string.empty);
        mCommentInput.setEnabled(true);
    }

    @Override
    public void showLoading() {
        mSendComment.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mCommentInput.setText(R.string.empty);
        mCommentInput.setEnabled(false);
    }

    @Override
    public void showError() {
        Toaster.showToast(getContext(), R.string.error_common_message);
    }
}
