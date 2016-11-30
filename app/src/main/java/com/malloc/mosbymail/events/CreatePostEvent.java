package com.malloc.mosbymail.events;

public class CreatePostEvent {

    private boolean mSuccess;

    public void setSuccess(final boolean success) {
        mSuccess = success;
    }

    public boolean isSuccess() {
        return mSuccess;
    }
}
