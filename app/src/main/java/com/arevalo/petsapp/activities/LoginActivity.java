package com.arevalo.petsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arevalo.petsapp.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, goRegisterButton;
    final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.button_iniciarSesion);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        goRegisterButton = findViewById(R.id.btn_goRegister);
        goRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }
}
