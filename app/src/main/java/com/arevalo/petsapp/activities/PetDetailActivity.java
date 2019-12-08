package com.arevalo.petsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.adapters.PetsAdapter;
import com.arevalo.petsapp.models.Pet;
import com.arevalo.petsapp.models.User;
import com.arevalo.petsapp.services.ApiService;
import com.arevalo.petsapp.services.ApiServiceGenerator;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDetailActivity extends AppCompatActivity {

    private static final String TAG = PetDetailActivity.class.getSimpleName();

    private ImageView image, qrimage;

    private TextView name_text, race_text, age_text, owner_text, email_text;

    private String nombre, raza, dueño, email;
    private int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        image = findViewById(R.id.image);
        qrimage = findViewById(R.id.qr_code);
        name_text = findViewById(R.id.name_text);
        race_text = findViewById(R.id.race_text);
        age_text = findViewById(R.id.age_text);
        owner_text = findViewById(R.id.owner_text);
        email_text = findViewById(R.id.email_text);

        initializePetData();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Long id = sp.getLong("id",0);



    }

    private void initializePetData() {
        final Long id = getIntent().getExtras().getLong("id");

        Log.d(TAG, String.valueOf(id));

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Pet> call = service.getPet(id);
        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        Pet pet = response.body();

                        String url = ApiService.API_BASE_URL + "/pets/images/" + pet.getPetimage();
                        Picasso.with(PetDetailActivity.this).load(url).into(image);
                        name_text.setText(pet.getPetname());
                        race_text.setText("Raza: " + pet.getPetrace());
                        age_text.setText("Edad: " + pet.getPetage());

                        QRGEncoder qrgEncoder = new QRGEncoder(
                                String.valueOf(pet.getPetid()), null,
                                QRGContents.Type.TEXT,
                                500);
                        try {
                            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                            qrimage.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            Log.v(TAG, e.toString());
                        }

                        initializeOwnerData(pet.getPetuser());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(PetDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {

            }
        });

    }

    private void initializeOwnerData(Long petid) {
        ApiService service1 = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call1 = service1.getUser(petid);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call1, Response<User> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        User user = response.body();

                        owner_text.setText("Dueño: " + user.getUsernames());
                        email_text.setText("Email: " + user.getUseremail());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(PetDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<User> call1, Throwable t) {

            }
        });
    }

}
