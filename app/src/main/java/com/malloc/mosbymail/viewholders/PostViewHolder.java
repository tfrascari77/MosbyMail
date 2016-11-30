package com.malloc.mosbymail.viewholders;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malloc.mosbymail.R;
import com.malloc.mosbymail.models.Post;
import com.malloc.mosbymail.services.AppServiceClient;
import com.malloc.mosbymail.utils.Image;
import com.malloc.mosbymail.utils.Navigation;
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
            Navigation.startComments(mActivity, mPostId);
        }

        @Override
        public void onSharePressed() {
            // TODO
        }
    };

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAppServiceClient = new AppServiceClient(itemView.getContext());
    }

    public void bind(final Activity activity, final Post post, final String postId) {
        mActivity = activity;
        mPostId = postId;
        mTitle.setText(post.title);

        // TODO Use actual author when ready
        mAuthor.setText(activity.getString(R.string.dummy_author));
        //mAuthor.setText(activity.getString(R.string.author_format, post.authorName));

        mCreationDate.setText(DateUtils.getRelativeTimeSpanString(post.creationDate * -1, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString());

        Image.loadPostThumbnail(mActivity, mImage, Uri.parse(post.imagePath));

        mActionBar.bind(postId, mOnActionPressedListener);
    }
}
