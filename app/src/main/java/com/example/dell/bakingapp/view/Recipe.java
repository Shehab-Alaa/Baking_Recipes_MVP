package com.example.dell.bakingapp.view;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.dell.bakingapp.IngredientsListAdapter;
import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.StepsListAdapter;
import com.example.dell.bakingapp.model.BakingRecipe;
import com.example.dell.bakingapp.model.Ingredient;
import com.example.dell.bakingapp.model.Step;

import java.util.List;

public class Recipe extends AppCompatActivity {

    private BakingRecipe bakingRecipe;
    private List<Ingredient> recipeIngredients = null;
    private List<Step> recipeSteps = null;

    private RecyclerView rvIngredients;
    private IngredientsListAdapter ingredientsListAdapter;

    private RecyclerView rvSteps;
    private StepsListAdapter stepsListAdapter;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().hide();

       bakingRecipe = (BakingRecipe) getIntent().getExtras().getSerializable("choosenRecipe");
       recipeIngredients = bakingRecipe.getIngredients();
       recipeSteps = bakingRecipe.getSteps();

       collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
       collapsingToolbarLayout.setTitleEnabled(true);
       collapsingToolbarLayout.setTitle(bakingRecipe.getName());

       recipeImage = findViewById(R.id.recipeImage);
       recipeImage.setImageResource(getRecipeImageId(getIntent().getExtras().getInt("recipeImage")));

       rvIngredients = findViewById(R.id.rvIngredients);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       rvIngredients.setLayoutManager(layoutManager);
       rvIngredients.setHasFixedSize(true);
       ingredientsListAdapter = new IngredientsListAdapter(this , recipeIngredients);
       rvIngredients.setAdapter(ingredientsListAdapter);

       rvSteps = findViewById(R.id.rvSteps);
       LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
       rvSteps.setLayoutManager(layoutManager1);
       rvSteps.setHasFixedSize(true);
       stepsListAdapter = new StepsListAdapter(this , recipeSteps);
       rvSteps.setAdapter(stepsListAdapter);

    }

    private int getRecipeImageId(int recipeImage)
    {
        switch (recipeImage)
        {
            case 0:
                return R.mipmap.nutellapie;
            case 1:
                return R.mipmap.brownies;
            case 2:
                return R.mipmap.yellowcake;
            case 3:
                return R.mipmap.cheesecake;
            default:
                return R.mipmap.nutellapie;
        }
    }
}
