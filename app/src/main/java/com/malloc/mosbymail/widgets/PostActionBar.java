package com.malloc.mosbymail.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.malloc.mosbymail.R;
import com.malloc.mosbymail.utils.Format;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostActionBar extends FrameLayout {

    @BindView(R.id.post_like)
    TextView mLike;

    @BindView(R.id.post_comment)
    TextView mComment;

    @BindView(R.id.post_share)
    TextView mShare;

    private OnActionPressedListener mListener;

    @OnClick({R.id.post_like, R.id.post_comment, R.id.post_share})
    void onActionPressed(final View view) {
        if (mListener == null) {
            return;
        }
        switch(view.getId()) {
            case R.id.post_like:
                mListener.onLikePressed();
                break;
            case R.id.post_comment:
                mListener.onCommentPressed();
                break;
            case R.id.post_share:
                mListener.onSharePressed();
                break;
        }
    }

    public PostActionBar(Context context) {
        this(context, null);
    }

    public PostActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PostActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_post_actionbar, this);
        if (!isInEditMode()) {
            ButterKnife.bind(this, this);
        }
    }

    public void setOnActionPressedListener(final OnActionPressedListener listener) {
        mListener = listener;
    }

    public void setLike(final boolean liked) {
        mLike.setCompoundDrawablesWithIntrinsicBounds(0, liked ? R.drawable.selector_ic_unlike : R.drawable.selector_ic_like, 0, 0);
    }

    public void setLikeCount(final long likeCount) {
        Format.formatCounters(likeCount);
    }

    public void setCommentCount(final long commentCount) {
        Format.formatCounters(commentCount);
    }

    public interface OnActionPressedListener {
        void onLikePressed();
        void onCommentPressed();
        void onSharePressed();
    }

}
