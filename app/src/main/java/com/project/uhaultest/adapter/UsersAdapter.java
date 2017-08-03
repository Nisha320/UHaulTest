package com.project.uhaultest.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.uhaultest.R;
import com.project.uhaultest.activity.PostActivity;
import com.project.uhaultest.model.user.Address;
import com.project.uhaultest.model.user.UserData;
import com.project.uhaultest.util.AppConstants;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    private List<UserData> userDataList;
    private Activity activity;
    private String addressFormat = "%s, %s, %s, %s";

    public UsersAdapter(Activity activity, List<UserData> userDataList) {
        this.activity = activity;
        this.userDataList = userDataList;
    }

    @Override
    public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersHolder holder, int position) {
        final UserData userData = userDataList.get(position);
        holder.userName.setText(userData.getUsername());

        final Address address = userData.getAddress();

        if(address != null) {
            holder.userAddress.setText(String.format(Locale.getDefault(), addressFormat, address.getStreet(), address.getSuite(),
                    address.getCity(), address.getZipcode()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), PostActivity.class);
                intent.putExtra(AppConstants.USER_ID, userData.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.userName)
        TextView userName;

        @Bind(R.id.userAddress)
        TextView userAddress;

        UsersHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
