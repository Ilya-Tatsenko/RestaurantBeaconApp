package com.example.restaurantbeaconapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantbeaconapp.Menu;
import com.example.restaurantbeaconapp.Model.Category;
import com.example.restaurantbeaconapp.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    Context context;
    private final OnCategoryListener onCategoryListener;

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;

        try {
            this.onCategoryListener = ((OnCategoryListener)context);
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.chipsItem.setText(categories.get(position).getTitle());

        holder.chipsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryListener.onCurrentCategory(categories.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        Chip chipsItem;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chipsItem = itemView.findViewById(R.id.chipsItem);
        }
    }

    public interface OnCategoryListener {
        void onCurrentCategory(String category);
    }

}
