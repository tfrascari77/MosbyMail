package com.malloc.mosbymail.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Input {

    public static void hideKeyboard(final Activity activity) {
        final View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        currentFocus.clearFocus();
    }

    public static void hideKeyboard(final Context context, final View view) {
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}
