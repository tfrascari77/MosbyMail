package com.malloc.mosbymail.utils;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    private static Toast mToast;

    public static void showToast(final Context context, final String message) {
        maybeCancelToast();
        mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void showToast(final Context context, final int messageResId) {
        maybeCancelToast();
        mToast = Toast.makeText(context, messageResId, Toast.LENGTH_LONG);
        mToast.show();
    }

    private static void maybeCancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
