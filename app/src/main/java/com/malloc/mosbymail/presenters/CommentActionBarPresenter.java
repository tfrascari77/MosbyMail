package com.malloc.mosbymail.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.models.Comment;
import com.malloc.mosbymail.views.CommentActionBarView;

import java.util.Map;

public class CommentActionBarPresenter extends MvpBasePresenter<CommentActionBarView> {

    public void doCreateComment(final String commentText, final String postId) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // TODO handle error
            return;
        }

        final Comment comment = new Comment(currentUser.getUid(), currentUser.getDisplayName(), commentText, System.currentTimeMillis() * -1);
        final DatabaseReference postCommentsRefs = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_COMMENTS).child(postId);
        final String key = postCommentsRefs.push().getKey();
        final Map<String, Object> commentValues = comment.toMap();
        postCommentsRefs.child(key).updateChildren(commentValues);

        if (isViewAttached()) {
            getView().showCreateCommentForm();
        }
    }
}
