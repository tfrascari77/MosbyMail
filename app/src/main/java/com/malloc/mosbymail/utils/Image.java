package com.malloc.mosbymail.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.malloc.mosbymail.R;

public class Image {

    public static void loadPostThumbnail(final Activity activity, final ImageView imageView, final Uri imageUri) {
        final int imageSize = activity.getResources().getDimensionPixelSize(R.dimen.post_image_size);
        Glide.with(activity)
                .load(imageUri)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    public static void loadPostImage(final Activity activity, final ImageView imageView, final Uri imageUri) {
        Glide.with(activity)
                .load(imageUri)
                .into(imageView);
    }
}
