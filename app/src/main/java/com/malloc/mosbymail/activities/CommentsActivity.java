package com.malloc.mosbymail.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.adapters.CommentAdapter;
import com.malloc.mosbymail.models.Comment;
import com.malloc.mosbymail.presenters.CommentsPresenter;
import com.malloc.mosbymail.services.AppServiceClient;
import com.malloc.mosbymail.utils.Input;
import com.malloc.mosbymail.views.CommentsView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CommentsActivity extends MvpLceActivity<SwipeRefreshLayout, List<Comment>, CommentsView, CommentsPresenter> implements CommentsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecycler;

    @BindView(R.id.input_comment)
    MaterialEditText mCommentInput;

    @BindView(R.id.send_comment)
    ImageView mSendComment;

    @OnClick(R.id.send_comment)
    public void onSendCommentPressed() {
        final String commentText = mCommentInput.getText().toString();
        mCommentInput.setText(R.string.empty);
        Input.hideKeyboard(this);
        mAppServiceClient.commentPost(commentText, getPostId());
    }

    @OnTextChanged(value = R.id.input_comment, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCommentInputTextChanged(final Editable editable) {
        mSendComment.setEnabled(!TextUtils.isEmpty(mCommentInput.getText().toString()));
    }

    private AppServiceClient mAppServiceClient;
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        mAppServiceClient = new AppServiceClient(this);

        setupActionBar();
        setupRecycler();
    }

    private void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecycler() {
        contentView.setOnRefreshListener(this);
        mAdapter = new CommentAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_comments_loading);
    }

    @NonNull
    @Override
    public CommentsPresenter createPresenter() {
        return new CommentsPresenter(getPostId());
    }

    @Override
    public void setData(List<Comment> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadComments(pullToRefresh);
    }

    private String getPostId() {
        return getIntent().getStringExtra(Constants.EXTRA_POST_ID);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
