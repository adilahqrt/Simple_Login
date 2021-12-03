package com.example.simplelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private SharedPreferences pref;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.inputEmailLog);
        editPassword = findViewById(R.id.inputPassLog);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("typeConnection", "login");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actionSignUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void actionLogin(View view) {
        boolean isInputValid = false;

        if (editEmail.getText().toString().isEmpty()) {
            editEmail.setError(getString(R.string.notNull));
            editEmail.requestFocus();
            isInputValid = false;
        } else
            isInputValid = true;

        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError(getString(R.string.notNull));
            editPassword.requestFocus();
            isInputValid = false;
        } else
            isInputValid = true;

        if (isInputValid) {
            User user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPassword.getText().toString());

            loginUsingRetrofit(user.getEmail(), user.getPassword());
        }
    }

    public void loginUsingRetrofit(String email, String password) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.lightlance));
        progressDialog.setMessage(getString(R.string.isWaiting));
        progressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://192.168.1.9/lightlance/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<UserResponse> call = client.login(email, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getCode() == 401) {
                        new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.warning)
                                .setMessage(response.body().getStatus()).setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        editPassword.setText("");
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    } else {
                        //code 400
                        new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.isWaiting)
                                .setMessage(response.body().getStatus()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LoginActivity.this,
                                        RegisterActivity.class);
                                intent.putExtra(GlobalVariable.TYPE_CONN, GlobalVariable.RETROFIT);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Status : Error!", Toast.LENGTH_SHORT).show();
                }

                Log.e(TAG, "Error : " + response.message());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.getMessage());
                Toast.makeText(LoginActivity.this, "Error : " + t.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}