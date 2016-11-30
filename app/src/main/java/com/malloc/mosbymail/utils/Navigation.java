package com.malloc.mosbymail.utils;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.activities.CreatePostActivity;

public class Navigation {

    public final static int REQUEST_CODE_TAKE_PICTURE = 1;

    public static boolean startTakePicture(final Fragment fragment, final Uri photoUri) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        if (intent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            return true;
        }
        return false;
    }

    public static void startCreatePostActivity(final Fragment fragment, final Uri photoUri) {
        final Intent intent = new Intent(fragment.getContext(), CreatePostActivity.class);
        intent.putExtra(Constants.EXTRA_FILE_URI, photoUri);
        fragment.startActivity(intent);
    }
}
