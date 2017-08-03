package com.project.uhaultest.apiclient;

import com.project.uhaultest.model.posts.CreateResponse;
import com.project.uhaultest.model.posts.Post;
import com.project.uhaultest.model.user.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UHaulAPI {

    @GET("/users")
    Call<List<UserData>> loadAllUserDetails();

    @GET("/posts")
    Call<List<Post>> viewAllPosts(@Query("userId") int userId);

    @POST("/posts")
    Call<CreateResponse> createPost(@Body Post post);

    @PUT("/posts/{postId}")
    Call<CreateResponse> updatePost(@Path("postId") int postId, @Body Post post);

    @DELETE("/posts/{postId}")
    Call<CreateResponse> deletePost(@Path("postId") int postId);
}
