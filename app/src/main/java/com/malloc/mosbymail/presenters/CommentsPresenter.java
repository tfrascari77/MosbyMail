package com.malloc.mosbymail.presenters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.malloc.mosbymail.models.Comment;
import com.malloc.mosbymail.utils.Firebase;
import com.malloc.mosbymail.views.CommentsView;

import java.util.ArrayList;
import java.util.List;

public class CommentsPresenter extends MvpBasePresenter<CommentsView> {


    private final String mPostId;

    public CommentsPresenter(final String postId) {
        mPostId = postId;
    }

    public void loadComments(final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Comment> data = new ArrayList<>();
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    data.add(child.getValue(Comment.class));
                }
                if (isViewAttached()) {
                    getView().setData(data);
                    getView().showContent();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (isViewAttached()) {
                    getView().showError(databaseError.toException(), pullToRefresh);
                }
            }
        };

        Firebase.getCommentsQuery(mPostId).addValueEventListener(valueEventListener);
    }


}
