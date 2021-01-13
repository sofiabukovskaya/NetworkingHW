package com.example.networkinghw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networkinghw.R;
import com.example.networkinghw.data.ApiClient;
import com.example.networkinghw.data.LoginRequest;
import com.example.networkinghw.data.LoginResponse;
import com.example.networkinghw.model.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
   Button btnLogin;
   EditText edEmail, edPassword;
   TextView noAccount;
   SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonSignIn);
        edEmail = findViewById(R.id.editTextTextEmailAddress);
        edPassword = findViewById(R.id.editTextTextPassword);
        noAccount = findViewById(R.id.DontHave);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edPassword.getText().toString()) || TextUtils.isEmpty(edEmail.getText().toString())){
                    Toast.makeText(LoginActivity.this,"All inputs are required", Toast.LENGTH_LONG).show();
                } else {
                    LoginResponse loginResponse = new LoginResponse();
                    LoginRequest loginRequest = new LoginRequest();
                    loginResponse.getToken();
                    loginRequest.setEmail(edEmail.getText().toString());
                    loginRequest.setPassword(edPassword.getText().toString());
                    loginUser(loginRequest);
                    getToken(loginResponse);
                }
            }
        });
    }
      private static String token;

    public void loginUser(LoginRequest loginRequest) {
        Call<LoginResponse> loginResponseCall = ApiClient.getService().emailUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
           if(response.isSuccessful()) {
               LoginResponse loginResponse = response.body();
               token = response.body().getToken();
               startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("user",loginResponse));
               finish();

           } else {
               Toast.makeText(LoginActivity.this,"Please, try again", Toast.LENGTH_LONG).show();
           }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Please, try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getToken(LoginResponse loginResponse){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().getToken(token);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putString(token, token.toString());
                    ed.commit();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}