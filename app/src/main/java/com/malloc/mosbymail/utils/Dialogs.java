package com.malloc.mosbymail.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.malloc.mosbymail.R;

public class Dialogs {

    public static void showErrorDialog(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.error_dialog_title);
        builder.setMessage(R.string.error_dialog_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
