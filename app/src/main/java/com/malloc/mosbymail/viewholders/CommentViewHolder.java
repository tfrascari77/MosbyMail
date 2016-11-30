package com.malloc.mosbymail.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.malloc.mosbymail.R;
import com.malloc.mosbymail.models.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_author)
    TextView mAuthor;

    @BindView(R.id.comment_creation_date)
    TextView mCreationDate;

    @BindView(R.id.comment_text)
    TextView mText;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Context context, final Comment comment) {
        // TODO Use actual author when ready
        mAuthor.setText("@Malloc");
        //mAuthor.setText(context.getString(R.string.author_format, comment.authorName));

        mCreationDate.setText(DateUtils.getRelativeTimeSpanString(comment.creationDate * -1, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString());
        mText.setText(comment.text);
    }
}
