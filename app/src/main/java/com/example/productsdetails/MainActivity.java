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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView admregister, admforgotPass;
    EditText admEmail, admPass;
    Button adminLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admforgotPass = (TextView) findViewById(R.id.admForgot);

        admregister = (TextView) findViewById(R.id.admRegister);
        admregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });

        admEmail = (EditText) findViewById(R.id.txtAdminEmail);
        admPass = (EditText) findViewById(R.id.txtAdminPassword);
        progressBar = (ProgressBar) findViewById(R.id.admLogBar);
        adminLogin = (Button) findViewById(R.id.btnAdminLogin);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = admEmail.getText().toString().trim();
                String password = admPass.getText().toString().trim();

                if (email.isEmpty()) {
                    admEmail.setError("Email is required");
                    admEmail.requestFocus();
                    return;
                }

              else  if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    admEmail.setError("Provide valid email");
                    admEmail.requestFocus();
                    return;
                }

            else    if (password.isEmpty()) {
                    admPass.setError("Password is required");
                    admPass.requestFocus();
                    return;
                }

             else   if (password.length() < 6) {
                    admPass.setError("Minimum length should be 6 characters");
                    admPass.requestFocus();
                    return;
                } else {

                    RetrofitInterface mApiService = this.getInterfaceService();

                    Call<String> responseBodyCall = mApiService.Login(email, password);

                    responseBodyCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if (response.body().contains("Success")) {
                                startActivity(new Intent(MainActivity.this, home.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Faild ! check your username and password ", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
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

        });

    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}