package com.malloc.mosbymail.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.malloc.mosbymail.Constants;

public class Firebase {

    public static DatabaseReference getPostUserLikeRef(final String postId) {
        return FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_LIKES).child(postId).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static DatabaseReference getPostLikeRef(final String postId) {
        return FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_LIKES).child(postId);
    }

    public static DatabaseReference getPostCommentsRef(final String postId) {
        return FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_COMMENTS).child(postId);
    }

    public static Query getPostQuery() {
        return getQuery(Constants.FIREBASE_DB_POSTS).orderByChild("creationDate");
    }

    public static Query getCommentsQuery(final String postId) {
        return getQuery(Constants.FIREBASE_DB_COMMENTS, postId);
    }

    private static Query getQuery(final String child) {
        return FirebaseDatabase.getInstance().getReference().child(child);
    }

    private static Query getQuery(final String child, final String id) {
        return FirebaseDatabase.getInstance().getReference().child(child).child(id);
    }
}
