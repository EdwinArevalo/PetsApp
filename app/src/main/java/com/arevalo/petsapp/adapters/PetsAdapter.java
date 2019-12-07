package com.arevalo.petsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arevalo.petsapp.R;
import com.arevalo.petsapp.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    private List<Pet> pets;

    public PetsAdapter() {
        this.pets = new ArrayList<>();
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, race, age;
        ViewHolder(View itemView){
            super(itemView);
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
        Pet pet = this.pets.get(position);
        holder.name.setText("Nombre: "+pet.getPetname());
        holder.race.setText("Raza: "+pet.getPetrace());
        holder.age.setText("Edad: "+pet.getPetage());

    }

    @Override
    public int getItemCount() {
        return this.pets.size();
    }
}
