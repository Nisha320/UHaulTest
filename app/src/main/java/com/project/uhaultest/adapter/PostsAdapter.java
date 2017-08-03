package com.project.uhaultest.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.uhaultest.R;
import com.project.uhaultest.activity.PostActivity;
import com.project.uhaultest.model.posts.Post;
import com.project.uhaultest.model.user.Address;
import com.project.uhaultest.model.user.UserData;
import com.project.uhaultest.util.AppConstants;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.UsersHolder> {

    private List<Post> postList;
    private ItemClickListener itemClickListener;

    public PostsAdapter(ItemClickListener itemClickListener, List<Post> postList) {
        this.itemClickListener = itemClickListener;
        this.postList = postList;
    }

    @Override
    public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(final UsersHolder holder, final int position) {
        final Post post = postList.get(position);
        holder.postTitle.setText(post.getTitle());

        holder.postBody.setText(Html.fromHtml(post.getBody()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.getPostPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        @Bind(R.id.postTitle)
        TextView postTitle;

        @Bind(R.id.postBody)
        TextView postBody;

        UsersHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, R.id.update, 0, "Update Post");
            menu.add(0, R.id.delete, 0, "Delete Post");
        }
    }

    public interface ItemClickListener {
        void getPostPosition(int position);
    }
}
