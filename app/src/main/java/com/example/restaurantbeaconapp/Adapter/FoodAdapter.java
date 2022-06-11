package com.example.restaurantbeaconapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantbeaconapp.Model.Category;
import com.example.restaurantbeaconapp.Model.Food;
import com.example.restaurantbeaconapp.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    List<Food> foods;
    Context context;

    public FoodAdapter(List<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View foodItem = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(foodItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
       int id = context.getResources()
               .getIdentifier("ic_" + foods.get(position).getImg(), "drawable", context.getPackageName());
       holder.img.setImageResource(id);

       holder.name.setText(foods.get(position).getName());
       holder.buttonPrice.setText(foods.get(position).getPrice());
       holder.weight.setText(foods.get(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, weight;
        Button buttonPrice;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            weight = itemView.findViewById(R.id.weight);
            buttonPrice = itemView.findViewById(R.id.buttonWithPrice);
        }
    }

}
