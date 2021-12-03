package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText editEmail, editPassword, editFullname, editGender, editAddress, editTelp;
    private Button btnRegister;
    private String typeConn = "retrofit";
    private SharedPreferences pref;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.inputEmail);
        editPassword = findViewById(R.id.inputPass);
        editFullname = findViewById(R.id.inputFullname);
        editGender  = findViewById(R.id.inputGender);
        editAddress = findViewById(R.id.inputAddress);
        editTelp = findViewById(R.id.inputTelp);

        pref = getSharedPreferences(GlobalVariable.PREFERENCE_NAME, MODE_PRIVATE);
    }

    @Override
    public  void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void actionRegister(View view) {
        boolean isInputValid = false;

        if (editEmail.getText().toString().isEmpty()) {
            editEmail.setError(getString(R.string.notNull));
            editEmail.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (editFullname.getText().toString().isEmpty()) {
            editFullname.setError(getString(R.string.notNull));
            editFullname.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError(getString(R.string.notNull));
            editPassword.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (editGender.getText().toString().isEmpty()) {
            editGender.setError(getString(R.string.notNull));
            editGender.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (editAddress.getText().toString().isEmpty()) {
            editAddress.setError(getString(R.string.notNull));
            editAddress.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (editTelp.getText().toString().isEmpty()) {
            editTelp.setError(getString(R.string.notNull));
            editTelp.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (isInputValid) {
            User user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPassword.getText().toString());
            user.setFullname(editFullname.getText().toString());
            user.setGender(editGender.getText().toString());
            user.setAlamat(editAddress.getText().toString());
            user.setNo_telp(editTelp.getId());
        }
    }
}