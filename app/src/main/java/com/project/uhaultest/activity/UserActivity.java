package com.project.uhaultest.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.uhaultest.R;
import com.project.uhaultest.adapter.UsersAdapter;
import com.project.uhaultest.apiclient.BaseURL;
import com.project.uhaultest.apiclient.UHaulAPI;
import com.project.uhaultest.model.user.UserData;
import com.project.uhaultest.util.CustomProgressDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class to display list of users
 */
public class UserActivity extends AppCompatActivity {

    @Bind(R.id.allUsersRV)
    RecyclerView allUsersList;

    @Bind(R.id.errorMsg)
    TextView errorMsg;

    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        allUsersList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allUsersList.getContext(),
                layoutManager.getOrientation());
        allUsersList.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();

        prepareRetrofitCall();
    }

    /**
     * Method to make API call and get the response
     */
    private void prepareRetrofitCall() {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this, R.style.transparent_progress_dialog);
            progressDialog.show();
            progressDialog.updateLoadingText("Loading...");
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 7000);
        }
        UHaulAPI api = BaseURL.getAPI();
        Call<List<UserData>> call = api.loadAllUserDetails();
        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    setUI(response.body());
                } else {
                    errorMsg.setVisibility(View.VISIBLE);
                    allUsersList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to set UI
     */
    private void setUI(List<UserData> userList) {
        errorMsg.setVisibility(View.GONE);
        allUsersList.setVisibility(View.VISIBLE);

        UsersAdapter adapter = new UsersAdapter(this, userList);
        allUsersList.setAdapter(adapter);
    }
}
