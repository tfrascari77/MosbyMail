package com.malloc.mosbymail.viewholders;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.models.Post;
import com.malloc.mosbymail.services.AppServiceClient;
import com.malloc.mosbymail.utils.Firebase;
import com.malloc.mosbymail.utils.Image;
import com.malloc.mosbymail.widgets.PostActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.post_image)
    ImageView mImage;

    @BindView(R.id.post_title)
    TextView mTitle;

    @BindView(R.id.post_author)
    TextView mAuthor;

    @BindView(R.id.post_creation_date)
    TextView mCreationDate;

    @BindView(R.id.post_actionbar)
    PostActionBar mActionBar;

    private Activity mActivity;
    private AppServiceClient mAppServiceClient;
    private String mPostId;

    private final PostActionBar.OnActionPressedListener mOnActionPressedListener = new PostActionBar.OnActionPressedListener() {
        @Override
        public void onLikePressed() {
            mAppServiceClient.likePost(mPostId);
        }

        @Override
        public void onCommentPressed() {
            // TODO
        }

        @Override
        public void onSharePressed() {
            // TODO
        }
    };

    private final ValueEventListener mPostUserLikeValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mActionBar.setLike(dataSnapshot.exists());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private final ValueEventListener mPostLikesValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mActionBar.setLikeCount(dataSnapshot.getChildrenCount());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
//
//    private final ValueEventListener mPostCommentsValueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            mActionBar.setCommentCount(dataSnapshot.getChildrenCount());
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    };

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAppServiceClient = new AppServiceClient(itemView.getContext());
    }

    public void bind(final Activity activity, final Post post, final String postId) {
        mActivity = activity;
        mPostId = postId;
        mTitle.setText(post.title);
        mAuthor.setText(activity.getString(R.string.author_format, post.authorName));
        mCreationDate.setText(DateUtils.getRelativeTimeSpanString(post.creationDate * -1, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString());

        Image.loadPostThumbnail(mActivity, mImage, Uri.parse(post.imagePath));

        mActionBar.setOnActionPressedListener(mOnActionPressedListener);


        Firebase.getPostUserLikeRef(postId).addValueEventListener(mPostUserLikeValueEventListener);
        Firebase.getPostLikeRef(postId).addValueEventListener(mPostLikesValueEventListener);
//        Client.getInstance().getPostCommentRef(postId).addValueEventListener(mPostCommentsValueEventListener);
    }

    public void unbind() {
        Firebase.getPostUserLikeRef(mPostId).removeEventListener(mPostUserLikeValueEventListener);
        Firebase.getPostLikeRef(mPostId).removeEventListener(mPostLikesValueEventListener);
//        Client.getInstance().getPostCommentRef(mPostId).removeEventListener(mPostCommentsValueEventListener);
    }
}
