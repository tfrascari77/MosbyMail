package com.malloc.mosbymail.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.malloc.mosbymail.Constants;
import com.malloc.mosbymail.events.CreatePostEvent;
import com.malloc.mosbymail.models.Comment;
import com.malloc.mosbymail.models.Post;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class AppService extends Service {

    private final static String TAG = AppService.class.getSimpleName();

    private final EventBus mEventBus = EventBus.getDefault();
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(final Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            final Bundle data = msg.getData();
            final String action = data.getString(Constants.EXTRA_SERVICE_ACTION);
            if (TextUtils.isEmpty(action)) {
                stopSelf(msg.arg1);
                return;
            }

            switch (action) {
                case Constants.SERVICE_ACTION_CREATE_POST:
                    onCreatePost(data);
                    break;
                case Constants.SERVICE_ACTION_LIKE_POST:
                    onLikePost(data);
                    break;
                case Constants.SERVICE_ACTION_COMMENT_POST:
                    onCommentPost(data);
                    break;
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        final HandlerThread thread = new HandlerThread(AppService.class.getSimpleName(), Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = startId;
            msg.setData(intent.getExtras());
            mServiceHandler.sendMessage(msg);
        }
        return START_STICKY;
    }

    private void onCreatePost(final Bundle data) {
        if (!data.containsKey(Constants.EXTRA_FILE_URI) || !data.containsKey(Constants.EXTRA_POST_TITLE)) {
            return;
        }
        final String postTitle = data.getString(Constants.EXTRA_POST_TITLE);
        final Uri imageUri = data.getParcelable(Constants.EXTRA_FILE_URI);
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

    private void onLikePost(final Bundle data) {
        if (!data.containsKey(Constants.EXTRA_POST_ID)) {
            return;
        }

        final String postId = data.getString(Constants.EXTRA_POST_ID);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return;
        }

        final DatabaseReference likeRefs = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_LIKES).child(postId).child(currentUser.getUid());
        likeRefs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    likeRefs.removeValue();
                } else {
                    likeRefs.setValue(ServerValue.TIMESTAMP);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void onCommentPost(final Bundle data) {
        if (!data.containsKey(Constants.EXTRA_POST_ID) || !data.containsKey(Constants.EXTRA_COMMENT_TEXT)) {
            return;
        }

        final String postId = data.getString(Constants.EXTRA_POST_ID);
        final String text = data.getString(Constants.EXTRA_COMMENT_TEXT);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return;
        }

        final Comment comment = new Comment(currentUser.getUid(), currentUser.getDisplayName(), text, System.currentTimeMillis() * -1);
        final DatabaseReference postCommentsRefs = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DB_COMMENTS).child(postId);
        final String key = postCommentsRefs.push().getKey();
        final Map<String, Object> commentValues = comment.toMap();

        postCommentsRefs.child(key).updateChildren(commentValues);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
