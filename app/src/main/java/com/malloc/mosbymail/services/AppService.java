package com.malloc.mosbymail.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.events.CreatePostEvent;
import com.malloc.mosbymail.models.Post;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class AppService extends IntentService {

    private final static String TAG = AppService.class.getSimpleName();

    private final EventBus mEventBus = EventBus.getDefault();

    public AppService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }

        switch (action) {
            case Constants.SERVICE_ACTION_CREATE_POST:
                onCreatePost(intent);
        }
    }

    private void onCreatePost(final Intent intent) {
        if (!intent.hasExtra(Constants.EXTRA_FILE_URI) || !intent.hasExtra(Constants.EXTRA_POST_TITLE)) {
            return;
        }
        final String postTitle = intent.getStringExtra(Constants.EXTRA_POST_TITLE);
        final Uri imageUri = intent.getParcelableExtra(Constants.EXTRA_FILE_URI);
        final StorageReference baseStorage = FirebaseStorage.getInstance().getReferenceFromUrl(Constants.FIREBASE_STORAGE_URL);
        final StorageReference imagePostRef = baseStorage.child(Constants.FIREBASE_STORAGE_IMAGES).child(imageUri.getLastPathSegment());
        final UploadTask uploadTask = imagePostRef.putFile(imageUri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                uploadTask.removeOnCompleteListener(this);
                final CreatePostEvent createPostEvent = new CreatePostEvent();
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error while uploading file - " + task.getException());
                    createPostEvent.setSuccess(false);
                } else {
                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser == null) {
                        createPostEvent.setSuccess(false);
                    } else {
                        final DatabaseReference postsDbRef = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_POSTS);
                        final String key = postsDbRef.push().getKey();
                        final Post post = new Post(currentUser.getUid(), currentUser.getDisplayName(), postTitle, task.getResult().getDownloadUrl().toString(), System.currentTimeMillis() * -1);
                        final Map<String, Object> postValues = post.toMap();
                        postsDbRef.child(key).updateChildren(postValues);
                        createPostEvent.setSuccess(true);
                    }
                }
                mEventBus.post(createPostEvent);
            }
        });

    }
}
