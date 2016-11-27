package com.malloc.mosbymail.utils;

import android.content.Context;
import android.os.Environment;

import com.malloc.mosbymail.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

    public static File createImageFile(final Context context) throws IOException {
        final String timestamp = SimpleDateFormat.getDateTimeInstance().format(new Date());
        final String filename = context.getString(R.string.image_filename_format, timestamp);
        final File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(filename, context.getString(R.string.image_filename_ext), storageDir);
    }
}
