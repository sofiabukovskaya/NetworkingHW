package com.example.networkinghw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.networkinghw.R;
import com.example.networkinghw.data.ApiClient;
import com.example.networkinghw.data.RegisterRequest;
import com.example.networkinghw.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
      Button btnSignUp;
      EditText edUsername, edEmail, edPassword, edConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = findViewById(R.id.buttonSignUp);
        edUsername = findViewById(R.id.editTextTextPersonName);
        edEmail = findViewById(R.id.editTextTextEmailAddressRegister);
        edPassword = findViewById(R.id.editTextTextPasswordRegister);
        edConfPassword = findViewById(R.id.editTextTextPasswordConfirm);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) || TextUtils.isEmpty(edConfPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"All inputs are required", Toast.LENGTH_LONG).show();
                } else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(edEmail.getText().toString());
                    registerRequest.setUsername(edUsername.getText().toString());
                    registerRequest.setPassword(edPassword.getText().toString());
                    registerUser(registerRequest);
                }
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
             if(response.isSuccessful()){
                 Toast.makeText(RegisterActivity.this,"Successful", Toast.LENGTH_LONG).show();
                 startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                 finish();

             } else  {
                 Toast.makeText(RegisterActivity.this,"Please, try again", Toast.LENGTH_LONG).show();
             }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Please, try again", Toast.LENGTH_LONG).show();
            }
        });
    }
}