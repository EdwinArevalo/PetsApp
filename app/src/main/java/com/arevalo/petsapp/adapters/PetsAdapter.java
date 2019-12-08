package com.arevalo.petsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.activities.PetDetailActivity;
import com.arevalo.petsapp.models.Pet;
import com.arevalo.petsapp.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    private List<Pet> pets;
    private String dueño;
    private String email;

    public PetsAdapter(String dueño, String email) {
        this.pets = new ArrayList<>();
        this.dueño = dueño;
        this.email = email;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, race, age;
        CircleImageView photo;
        ViewHolder(View itemView){
            super(itemView);
            photo = itemView.findViewById(R.id.petPhoto);
            name = itemView.findViewById(R.id.pet_name);
            race = itemView.findViewById(R.id.pet_raza);
            age = itemView.findViewById(R.id.pet_age);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Context context = holder.itemView.getContext();

        final Pet pet = this.pets.get(position);

        holder.name.setText("Nombre: "+pet.getPetname());
        holder.race.setText("Raza: "+pet.getPetrace());
        holder.age.setText("Edad: "+pet.getPetage());
        String url = ApiService.API_BASE_URL + "/pets/images/" + pet.getPetimage();
        Picasso.with(holder.itemView.getContext()).load(url).into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetDetailActivity.class);
                intent.putExtra("id", pet.getPetid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.pets.size();
    }
}
