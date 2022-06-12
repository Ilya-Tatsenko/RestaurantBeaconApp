package com.example.restaurantbeaconapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.restaurantbeaconapp.Adapter.CategoryAdapter;
import com.example.restaurantbeaconapp.Adapter.FoodAdapter;
import com.example.restaurantbeaconapp.Model.Category;
import com.example.restaurantbeaconapp.Model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu extends AppCompatActivity implements CategoryAdapter.OnCategoryListener {
    RecyclerView categoryRecyclerView;
    RecyclerView foodRecyclerView;
    CategoryAdapter categoryAdapter;
    @SuppressLint("StaticFieldLeak")
    static FoodAdapter foodAdapter;
    static List<Food> foodList = new ArrayList<>();
    static List<Food> fullFoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodList.clear();
                fullFoodList.clear();
                onBackPressed();
            }
        });

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1,  "Горячее"));
        categoryList.add(new Category(2,  "Салаты"));
        categoryList.add(new Category(3,  "Выпечка"));
        categoryList.add(new Category(4,  "Напитки"));

        setCategoryViewRecycler(categoryList);

        foodList.add(new Food(1, "example2","Котлеты рыбные с пюре из сельдерея","210 г", "110 р", "Горячее"));
        foodList.add(new Food(2, "example2","Котлеты рыбные с пюре из сельдерея","150 г", "390 р", "Горячее"));
        foodList.add(new Food(3, "example2","Котлеты рыбные с пюре из сельдерея","150 г", "390 р", "Горячее"));
        foodList.add(new Food(4, "example2","Котлеты рыбные с пюре из сельдерея","150 г", "390 р", "Напитки"));

        setFoodViewRecycler(foodList);
        fullFoodList.addAll(foodList);
    }

    private void setCategoryViewRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecyclerView = findViewById(R.id.chipsRecyclerView);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(categoryList,this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
    private void setFoodViewRecycler(List<Food> foodList) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, foodList.size()/2, RecyclerView.VERTICAL,false);

        foodRecyclerView = findViewById(R.id.foodsRecyclerView);
        foodRecyclerView.setLayoutManager(layoutManager);

        foodAdapter = new FoodAdapter(foodList,this);
        foodRecyclerView.setAdapter(foodAdapter);
    }

    /*
    @SuppressLint("NotifyDataSetChanged")
    public static void setCurrentCategory(String category) {
        List<Food> filterList = new ArrayList<>();

        foodList.clear();
        foodList.addAll(fullFoodList);

        for (Food f : foodList) {
            if (f.getCategory().equals(category))
                filterList.add(f);
        }

        foodList.clear();
        foodList.addAll(filterList);
        filterList.clear();


        foodAdapter.notifyDataSetChanged();
    }

     */

    @Override
    public void onCurrentCategory(String category) {
        List<Food> filterList = new ArrayList<>();

        foodList.clear();
        foodList.addAll(fullFoodList);

        for (Food f : foodList) {
            if (f.getCategory().equals(category))
                filterList.add(f);
        }

        foodList.clear();
        foodList.addAll(filterList);
        filterList.clear();


        foodAdapter.notifyDataSetChanged();
    }
}