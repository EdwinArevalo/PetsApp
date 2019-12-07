package com.arevalo.petsapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.models.Pet;
import com.arevalo.petsapp.services.ApiService;
import com.arevalo.petsapp.services.ApiServiceGenerator;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPetsActivity extends AppCompatActivity {

    public static final String TAG = RegisterPetsActivity.class.getSimpleName();

    private CircleImageView photoPet;
    private EditText namePet, racePet, agePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pets);

        photoPet = findViewById(R.id.inputPhotoPet);
        namePet = findViewById(R.id.inputNamePet);
        racePet = findViewById(R.id.inputRacePet);
        agePet = findViewById(R.id.inputAgePet);

    }

    private static final int REQUEST_CAMERA = 100;

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = scaleBitmapDown(bitmap, 800);
                photoPet.setImageBitmap(bitmap);
            }
        }
    }

    public void callRegisterPet(View view) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Long idUser = sp.getLong("id",0);

        String name = namePet.getText().toString();
        String race = racePet.getText().toString();
        String age = agePet.getText().toString();
        if (name.isEmpty() || race.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Pet> call;

        if(bitmap == null){
            call = service.createPet(name, race, age,idUser);
        }else {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("image", "photo.jpg", requestFile);

            RequestBody namePart = RequestBody.create(MultipartBody.FORM, name);
            RequestBody racePart = RequestBody.create(MultipartBody.FORM, race);
            RequestBody agePart = RequestBody.create(MultipartBody.FORM, age);
            RequestBody idUserPart = RequestBody.create(MultipartBody.FORM, String.valueOf(idUser));

            call = service.createPet(namePart, racePart, agePart,idUserPart, photoPart);
        }

        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                try{
                    if(response.isSuccessful()){

                        Pet pet = response.body();
                        Log.d(TAG, "pet: " + pet);
                        Toast.makeText(RegisterPetsActivity.this, "Mascota registrada", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        finish();
                    }else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                }catch (Throwable t){
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegisterPetsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {

            }
        });

    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }
}
