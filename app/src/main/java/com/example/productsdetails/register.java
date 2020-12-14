package com.example.productsdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText regtxtfullname,regtxtAge,regtxtEmail,regtxtPass;
    Button btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regtxtAge = (EditText) findViewById(R.id.editTextUserAge);
        regtxtEmail = (EditText) findViewById(R.id.editTextTextPersonName);
        regtxtPass = (EditText) findViewById(R.id.editTextTextPassword);
        regtxtfullname = (EditText) findViewById(R.id.editTextFullName);
        progressBar = (ProgressBar) findViewById(R.id.proBarRegister);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String email = regtxtEmail.getText().toString().trim();
        final String password = regtxtPass.getText().toString().trim();
        final String name = regtxtfullname.getText().toString().trim();
        final String age = regtxtAge.getText().toString().trim();

        if (name.isEmpty()) {

            regtxtPass.setError("Full name is required");
            regtxtPass.requestFocus();
            return;
        }
     else   if (age.isEmpty()) {

            regtxtPass.setError("Age is required");
            regtxtPass.requestFocus();
            return;
        }

    else    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regtxtEmail.setError("Provide valid email");
            regtxtEmail.requestFocus();
            return;
        }

      else  if (password.isEmpty()) {

            regtxtPass.setError("Password is required");
            regtxtPass.requestFocus();
            return;
        }
      else  if (password.length() < 6) {
            regtxtPass.setError("Minimum length should be 6 characters");
            regtxtPass.requestFocus();
            return;
        }

        else {
            RetrofitInterface mApiService = this.getInterfaceService();

            Call<String> responseBodyCall = mApiService.Signup(name,age,email,password);

            responseBodyCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if(response.body().contains("Success")){

                        Toast.makeText(register.this, "Successfully Signup", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(register.this, "Faild !", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(register.this, t.toString(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private RetrofitInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.64:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitInterface mInterfaceService = retrofit.create(RetrofitInterface.class);
        return mInterfaceService;
    }

}