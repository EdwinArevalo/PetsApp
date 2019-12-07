package com.arevalo.petsapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.adapters.PetsAdapter;
import com.arevalo.petsapp.models.Pet;
import com.arevalo.petsapp.models.User;
import com.arevalo.petsapp.services.ApiService;
import com.arevalo.petsapp.services.ApiServiceGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView petsList;
    private ProgressBar progressBar;
    private TextView welcome;

    FloatingActionButton goRegisterPet;
    private static final int REQUEST_REGISTER = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goRegisterPet = findViewById(R.id.floatingActionButton);
        goRegisterPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterPetsActivity.class);
                startActivityForResult(intent,REQUEST_REGISTER);
            }
        });

        welcome = findViewById(R.id.bienvenido_ususario);
        petsList = findViewById(R.id.recyclerview);
        progressBar=findViewById(R.id.progressbar);
        petsList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        petsList.setLayoutManager(new LinearLayoutManager(this));
        petsList.setAdapter(new PetsAdapter());

        initialize();
    }

    private void initialize() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Long id = sp.getLong("id",0);

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call = service.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        User user = response.body();

                        welcome.setText("Bienbenido "+user.getUsernames());

                        List<Pet> pets = user.getPets();

                        PetsAdapter adapter = (PetsAdapter) petsList.getAdapter();
                        adapter.setPets(pets);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        petsList.setVisibility(View.VISIBLE);


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_logout:
                logout();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove("isLogged").commit();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REGISTER) {
            initialize();
        }
    }
}
