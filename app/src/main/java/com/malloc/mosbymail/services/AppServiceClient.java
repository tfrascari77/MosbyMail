package com.malloc.mosbymail.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.malloc.mosbymail.Constants;

public class AppServiceClient {

    private final Context mContext;

    public AppServiceClient(final Context context) {
        mContext = context;
    }

    public void createPost(final String title, final Uri postImageUri) {
        final Intent intent = new Intent(mContext, AppService.class);
        intent.putExtra(Constants.EXTRA_SERVICE_ACTION, Constants.SERVICE_ACTION_CREATE_POST);
        intent.putExtra(Constants.EXTRA_FILE_URI, postImageUri);
        intent.putExtra(Constants.EXTRA_POST_TITLE, title);
        mContext.startService(intent);
    }

    public void likePost(final String postId) {
        final Intent intent = new Intent(mContext, AppService.class);
        intent.putExtra(Constants.EXTRA_SERVICE_ACTION, Constants.SERVICE_ACTION_LIKE_POST);
        intent.putExtra(Constants.EXTRA_POST_ID, postId);
        mContext.startService(intent);
    }
}
