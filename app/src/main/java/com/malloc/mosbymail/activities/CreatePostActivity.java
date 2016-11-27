package com.malloc.mosbymail.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.R;
import com.malloc.mosbymail.services.AppService;
import com.malloc.mosbymail.utils.Image;
import com.malloc.mosbymail.utils.Toaster;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePostActivity extends AppCompatActivity  {

    private final static String TAG = CreatePostActivity.class.getSimpleName();

    @BindView(R.id.submit_post)
    ActionProcessButton mSubmit;

    @BindView(R.id.post_title)
    MaterialEditText mTitle;

    @BindView(R.id.post_preview)
    ImageView mPreview;

    private Uri mImageUri;

    @OnClick(R.id.submit_post) void onClick(final View v) {
        final String title = mTitle.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mTitle.setError(getString(R.string.field_required));
            return;
        }

        final Intent intent = new Intent(this, AppService.class);
        intent.putExtra(Constants.EXTRA_FILE_URI, mImageUri);
        intent.putExtra(Constants.EXTRA_POST_TITLE, title);
        startService(intent);

        Toaster.showToast(this, R.string.creating_post);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_FILE_URI)) {
            mImageUri = intent.getParcelableExtra(Constants.EXTRA_FILE_URI);
        } else {
            Log.e(TAG, "Error : CreatePostActivity requires an EXTRA_FILE_URI");
            finish();
        }
        Image.loadPostThumbnail(this, mPreview, mImageUri);
    }
}
