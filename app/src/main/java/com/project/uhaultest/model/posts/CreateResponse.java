package com.project.uhaultest.model.posts;

import com.google.gson.annotations.SerializedName;

public class CreateResponse {

    @SerializedName("id")
    private int postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
