package com.malloc.mosbymail.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.activities.CommentsActivity;
import com.malloc.mosbymail.activities.CreatePostActivity;
import com.malloc.mosbymail.activities.MainActivity;
import com.malloc.mosbymail.activities.LoginActivity;

public class Navigation {

    public final static int REQUEST_CODE_TAKE_PICTURE = 1;

    public static void startMain(final Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void startLogin(final Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static boolean startTakePicture(final Fragment fragment, final Uri photoUri) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        if (intent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            return true;
        }
        return false;
    }

    public static void startCreatePost(final Fragment fragment, final Uri photoUri) {
        final Intent intent = new Intent(fragment.getContext(), CreatePostActivity.class);
        intent.putExtra(Constants.EXTRA_FILE_URI, photoUri);
        fragment.startActivity(intent);
    }

    public static void startComments(final Activity activity, final String postId) {
        final Intent intent = new Intent(activity, CommentsActivity.class);
        intent.putExtra(Constants.EXTRA_POST_ID, postId);
        activity.startActivity(intent);
    }
}
