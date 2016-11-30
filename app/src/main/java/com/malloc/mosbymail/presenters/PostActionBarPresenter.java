package com.malloc.mosbymail.presenters;

import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.utils.Firebase;
import com.malloc.mosbymail.views.PostActionBarView;

public class PostActionBarPresenter extends MvpBasePresenter<PostActionBarView> {

    private final ValueEventListener mPostUserLikeValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isViewAttached()) {
                getView().onUserLikeUpdated(dataSnapshot.exists());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private final ValueEventListener mPostLikesValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isViewAttached()) {
                getView().onPostLikeCountUpdated(dataSnapshot.getChildrenCount());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private final ValueEventListener mPostCommentsValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isViewAttached()) {
                getView().onPostCommentCountUpdated(dataSnapshot.getChildrenCount());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public PostActionBarPresenter(final String postId) {
        mPostId = postId;
    }

    private DatabaseReference mUserLikeRef, mLikesRef, mCommentsRef;
    private boolean mIsListening = false;
    private String mPostId;


    public void doStartListening(final String postId) {
        mPostId = postId;
        doStartListening();
    }

    private void doStartListening() {
        if (mIsListening || TextUtils.isEmpty(mPostId)) {
            return;
        }
        mIsListening = true;
        mUserLikeRef = Firebase.getPostUserLikeRef(mPostId);
        mLikesRef = Firebase.getPostLikeRef(mPostId);
        mCommentsRef = Firebase.getPostCommentsRef(mPostId);

        mUserLikeRef.addValueEventListener(mPostUserLikeValueEventListener);
        mLikesRef.addValueEventListener(mPostLikesValueEventListener);
        mCommentsRef.addValueEventListener(mPostCommentsValueEventListener);
    }

    public void doStopListening() {
        if (!mIsListening) {
            return;
        }
        mIsListening = false;
        if (mUserLikeRef != null) {
            mUserLikeRef.removeEventListener(mPostUserLikeValueEventListener);
        }
        if (mLikesRef != null) {
            mLikesRef.removeEventListener(mPostLikesValueEventListener);
        }
        if (mCommentsRef != null) {
            mCommentsRef.removeEventListener(mPostCommentsValueEventListener);
        }
    }

    @Override
    public void attachView(PostActionBarView view) {
        super.attachView(view);
        doStartListening(mPostId);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        doStopListening();
    }
}
