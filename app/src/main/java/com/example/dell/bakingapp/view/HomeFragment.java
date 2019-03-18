package com.example.dell.bakingapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.model.BakingRecipe;
import com.example.dell.bakingapp.presenter.ApiServicePresenter;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends android.support.v4.app.Fragment implements IHomeFragment {

    List<BakingRecipe> bakingRecipes = null;
    android.support.v7.widget.CardView nutellaPieRecipe , browniesRecipe , yellowCakeRecipe , cheeseCakeRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home , container , false );

        ApiServicePresenter apiServicePresenter = new ApiServicePresenter(this);
        apiServicePresenter.getBakingRecipes();

        nutellaPieRecipe = view.findViewById(R.id.bakingRecipe_0);
        browniesRecipe = view.findViewById(R.id.bakingRecipe_1);
        yellowCakeRecipe = view.findViewById(R.id.bakingRecipe_2);
        cheeseCakeRecipe = view.findViewById(R.id.bakingRecipe_3);


        nutellaPieRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRecipesList())
                {
                    openRecipe(0);
                }
            }
        });

        browniesRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRecipesList())
                {
                    openRecipe(1);
                }
            }
        });

        yellowCakeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRecipesList())
                {
                    openRecipe(2);
                }
            }
        });

        cheeseCakeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRecipesList())
                {
                    openRecipe(3);
                }
            }
        });

        return view;
    }

    @Override
    public void listEquality(List<BakingRecipe> bakingRecipes) {
        this.bakingRecipes = bakingRecipes;

        for (BakingRecipe recipe : this.bakingRecipes)
        {
            Log.i("RecipeID"  , recipe.getId().toString());
            Log.i("RecipeName"  , recipe.getName());
            Log.i("RecipeImage"  , recipe.getImage());
            Log.i("IngredientQuantity"  , recipe.getIngredients().get(0).getQuantity().toString());
        }

    }

    private boolean checkRecipesList()
    {
        if (bakingRecipes != null)
            return true;
        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void openRecipe(int recipeIndex)
    {
        Intent intent = new Intent(getActivity() , Recipe.class);
        intent.putExtra("choosenRecipe" , bakingRecipes.get(recipeIndex));
        intent.putExtra("recipeImage" , recipeIndex);
        startActivity(intent);
    }
}
