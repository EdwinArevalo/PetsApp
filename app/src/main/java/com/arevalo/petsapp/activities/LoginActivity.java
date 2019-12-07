package com.arevalo.petsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.models.User;
import com.arevalo.petsapp.services.ApiService;
import com.arevalo.petsapp.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, goRegisterButton;
    final int REQUEST_CODE = 100;

    EditText emailUser, passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.button_iniciarSesion);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
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

        emailUser = findViewById(R.id.inputCorreoLogin);
        passwordUser = findViewById(R.id.inputPasswordLogin);

        loadLoastUsername();
    }

    public void Login(){
        final String email = emailUser.getText().toString();
        final String password = passwordUser.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            passwordUser.setError("Ingrese sus datos");
            emailUser.requestFocus();
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        service.login(email,password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful()) {
                        User user = response.body();

                        if (user != null){
                            Toast.makeText(LoginActivity.this,"Usuario "+ user.getUsernames() + " logeado",Toast.LENGTH_SHORT).show();

                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            sp.edit()
                                    .putBoolean("isLogged",true)
                                    .putLong("id",user.getUserid())
                                    .putString("correo",user.getUseremail())
                                    .commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            return;
                        }

                        Toast.makeText(LoginActivity.this,"Datos incorrectos",Toast.LENGTH_SHORT).show();



                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void loadLoastUsername(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String usu = sp.getString("correo",null);
        if(usu !=null){
            emailUser.setText(usu);
            passwordUser.requestFocus();
        }
    }
}

