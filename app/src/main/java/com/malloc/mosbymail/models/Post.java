package com.malloc.mosbymail.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    public String authorId;
    public String authorName;
    public String title;
    public String imagePath;
    public long creationDate;

    public Post() {

    }

    public Post(final String authorId, final String authorName, final String title, final String imagePath, final long creationDate) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.imagePath = imagePath;
        this.creationDate = creationDate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> rv = new HashMap<>();
        rv.put("authorId", authorId);
        rv.put("authorName", authorName);
        rv.put("title", title);
        rv.put("imagePath", imagePath);
        rv.put("creationDate", creationDate);
        return rv;
    }
}
