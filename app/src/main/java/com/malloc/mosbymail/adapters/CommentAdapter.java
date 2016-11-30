package com.malloc.mosbymail.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malloc.mosbymail.R;
import com.malloc.mosbymail.models.Comment;
import com.malloc.mosbymail.viewholders.CommentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private final Context mContext;
    private List<Comment> mData = new ArrayList<>();

    public CommentAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(mContext, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(final List<Comment> data) {
        if (data == null) {
            return;
        }
        mData = data;
        notifyDataSetChanged();
    }
}
