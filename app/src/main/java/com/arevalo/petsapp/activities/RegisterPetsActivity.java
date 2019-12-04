package com.arevalo.petsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.arevalo.petsapp.R;

public class RegisterPetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pets);
    }

    public void callRegisterPet(View view) {
        finish();
    }
}
