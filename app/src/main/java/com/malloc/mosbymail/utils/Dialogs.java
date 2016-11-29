package com.malloc.mosbymail.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.malloc.mosbymail.R;

public class Dialogs {

    public static ProgressDialog buildLogoutDialog(final Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.logout);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }
}
