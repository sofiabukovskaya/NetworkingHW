package com.example.networkinghw.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.networkinghw.R;
import com.example.networkinghw.data.ApiClient;
import com.example.networkinghw.data.ChannelAdapter;
import com.example.networkinghw.data.LoginResponse;
import com.example.networkinghw.model.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements ChannelAdapter.OnChannelClickListener {

    public static final String EXTRA_TITLE ="title";
    private RecyclerView mRecyclerView;
    private ChannelAdapter mChannelAdapter;
    private ArrayList<Channel> mChannelList;
    private RequestQueue mRequestQueue;
       LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChannelList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Intent intent = getIntent();
        if(intent.getExtras()!= null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("user");

            Log.e("TAG", "-------" + loginResponse.getEmail());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()) {
             case R.id.item:
                 item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                          LoginResponse loginResponse = new LoginResponse();
                          loginResponse.logOutUser();
                          logOutUser(loginResponse);
                         return true;
                     }
                 });

                 return true;

         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }
    private static String token;

    public void logOutUser(LoginResponse loginResponse){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().getToken(token);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    token = response.body().logOutUser();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseJSON(){
        String url = "https://api.screenlife.com/api/get-channels";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String titleName = hit.getString("title");

                                mChannelList.add(new Channel(titleName));
                            }

                            mChannelAdapter = new ChannelAdapter(MainActivity.this, mChannelList);
                            mRecyclerView.setAdapter(mChannelAdapter);
                            mChannelAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onChannelClick(int position) {
        Intent detailIntent = new Intent(MainActivity.this, ChannelDetailActivity.class);
        Channel clickedItem = mChannelList.get(position);
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        startActivity(detailIntent);
    }
}