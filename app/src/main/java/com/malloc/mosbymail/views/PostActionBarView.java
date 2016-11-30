package com.malloc.mosbymail.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface PostActionBarView extends MvpView {

    // Update the user like state
    void onUserLikeUpdated(final boolean liked);

    // Update the post like count
    void onPostLikeCountUpdated(final long postLikeCount);

    // Update the post comments count
    void onPostCommentCountUpdated(final long postCommentCount);
}
