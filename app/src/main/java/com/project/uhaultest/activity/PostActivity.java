package com.project.uhaultest.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.uhaultest.R;
import com.project.uhaultest.adapter.PostsAdapter;
import com.project.uhaultest.apiclient.BaseURL;
import com.project.uhaultest.apiclient.UHaulAPI;
import com.project.uhaultest.model.posts.CreateResponse;
import com.project.uhaultest.model.posts.Post;
import com.project.uhaultest.util.AppConstants;
import com.project.uhaultest.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class for displaying posts of each user.
 * Also, takes care of creating new posts, update/delete posts.
 * Arranging the Posts to ascending/descending orders.
 */
public class PostActivity extends AppCompatActivity implements PostsAdapter.ItemClickListener {

    @Bind(R.id.allPosts)
    RecyclerView allPosts;

    @Bind(R.id.errorMsg)
    TextView errorMsg;

    @Bind(R.id.createPost)
    Button createPost;

    private int userId;
    private List<Post> postList;
    private int position;
    private UHaulAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        allPosts.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allPosts.getContext(),
                layoutManager.getOrientation());
        allPosts.addItemDecoration(dividerItemDecoration);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra(AppConstants.USER_ID, 1);
        }

        api = BaseURL.getAPI();

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(false, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        prepareViewPostsCall();
    }

    /**
     * Method to set response to UI
     *
     * @param postList List of posts
     */
    private void setUI(List<Post> postList) {
        if (postList != null && postList.size() > 0) {
            errorMsg.setVisibility(View.GONE);
            allPosts.setVisibility(View.VISIBLE);

            this.postList = postList;

            PostsAdapter adapter = new PostsAdapter(this, postList);
            allPosts.setAdapter(adapter);
        }
    }

    /**
     * Dialog for creating a new post and updating existing post
     *
     * @param isUpdate boolean
     * @param postId   post id
     */
    private void showDialog(final boolean isUpdate, final int postId) {
        final Dialog dialog = new Dialog(PostActivity.this);
        if (isUpdate) {
            dialog.setTitle("Update Post");
        } else {
            dialog.setTitle(R.string.create_post);
        }

        dialog.setContentView(R.layout.create_post_layout);
        dialog.show();

        final EditText postTitle = (EditText) dialog.findViewById(R.id.postTitle);
        final EditText postBody = (EditText) dialog.findViewById(R.id.postBody);

        Button create = (Button) dialog.findViewById(R.id.createPost);

        if (isUpdate) {
            create.setText("Update Post");
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Post post = new Post();
                post.setUserId(userId);
                post.setTitle(postTitle.getText().toString());
                post.setBody(postBody.getText().toString());

                if (isUpdate) {
                    prepareUpdatePostCall(post, postId);
                } else {
                    prepareCreatePostCall(post);
                }
            }
        });
    }

    /**
     * Method for Get posts API call
     */
    private void prepareViewPostsCall() {
        Call<List<Post>> call = api.viewAllPosts(userId);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setUI(response.body());
                } else {
                    errorMsg.setVisibility(View.VISIBLE);
                    allPosts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method for Create post API call
     *
     * @param post post object
     */
    private void prepareCreatePostCall(Post post) {
        Call<CreateResponse> call = api.createPost(post);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getPostId() + " post is created", Toast.LENGTH_SHORT).show();
                    prepareViewPostsCall();
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method for Update Post API call
     *
     * @param post   post object
     * @param postId post id
     */
    private void prepareUpdatePostCall(Post post, final int postId) {
        Call<CreateResponse> call = api.updatePost(postId, post);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "PostId :" + postId + " is updated", Toast.LENGTH_SHORT).show();
                    prepareViewPostsCall();
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method for delete Post API call
     *
     * @param postId post id
     */
    private void prepareDeletePostCall(final int postId) {
        Call<CreateResponse> call = api.deletePost(postId);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "PostId :" + postId + " is deleted", Toast.LENGTH_SHORT).show();
                    prepareViewPostsCall();
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update) {
            Post post = postList.get(position);
            showDialog(true, post.getPostId());
        } else if (item.getItemId() == R.id.delete) {
            Post post = postList.get(position);
            prepareDeletePostCall(post.getPostId());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.asc) {
            setUI(Util.ascendingOrderPosts(postList));
        } else if (item.getItemId() == R.id.des) {
            setUI(Util.descendingOrderPosts(postList));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getPostPosition(int position) {
        this.position = position;
    }
}
