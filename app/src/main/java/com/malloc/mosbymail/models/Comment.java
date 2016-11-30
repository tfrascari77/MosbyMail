package com.malloc.mosbymail.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Comment {

    public String authorId;
    public String authorName;
    public String text;
    public long creationDate;

    public Comment() {

    }

    public Comment(String authorId, String authorName, String text, long creationDate) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.text = text;
        this.creationDate = creationDate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> rv = new HashMap<>();
        rv.put("authorId", authorId);
        rv.put("authorName", authorName);
        rv.put("text", text);
        rv.put("creationDate", creationDate);
        return rv;
    }
}
