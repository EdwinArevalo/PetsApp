package com.arevalo.petsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.models.User;
import com.arevalo.petsapp.services.ApiService;
import com.arevalo.petsapp.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterUserActivity extends AppCompatActivity {

    EditText usernameInput,emailInput,passwordInput;
    LinearLayout content;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        usernameInput = findViewById(R.id.inputNombreRegister);
        emailInput = findViewById(R.id.inputCorreoRegister);
        passwordInput = findViewById(R.id.inputContraseñaRegister);
        content = findViewById(R.id.contentRegisterUser);
        progressBar = findViewById(R.id.progressbarRegisterUser);

    }

    public void callRegister(View view) {
        RegisterUser();
    }

    public void RegisterUser(){
        progressBar.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);

        String names = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (names.isEmpty()){
            usernameInput.setError("Es obligatorio este campo");
            usernameInput.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailInput.setError("Es obligatorio este campo");
            emailInput.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordInput.setError("Es obligatorio este campo");
            passwordInput.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordInput.setError("La contraseñna debe tener 6 caracteres como mínimo");
            passwordInput.requestFocus();
            return;
        }



        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        final User user = new User(names, email,password);
        service.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(RegisterUserActivity.this,"Coded: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterUserActivity.this,"Usuario "+user.getUsernames()+" registrado correctamente",Toast.LENGTH_LONG).show();
                content.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
