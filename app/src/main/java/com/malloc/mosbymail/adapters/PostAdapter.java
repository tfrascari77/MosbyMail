package com.malloc.mosbymail.adapters;

import android.app.Activity;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.models.Post;
import com.malloc.mosbymail.viewholders.PostViewHolder;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostViewHolder> {

    private final Activity mActivity;

    public PostAdapter(final Activity activity, final Query ref) {
        super(Post.class, R.layout.list_item_post, PostViewHolder.class, ref);
        mActivity = activity;
    }

    @Override
    protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
        viewHolder.bind(mActivity, model, getRef(position).getKey());
    }
}
