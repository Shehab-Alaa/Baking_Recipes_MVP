package com.example.dell.bakingapp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.bakingapp.model.ApiClient;
import com.example.dell.bakingapp.model.ApiService;
import com.example.dell.bakingapp.model.BakingRecipe;
import com.example.dell.bakingapp.model.Ingredient;
import com.example.dell.bakingapp.view.IHomeFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServicePresenter implements IApiServicePresenter{

    IHomeFragment iHomeFragment;
    List<BakingRecipe>  bakingRecipes = new ArrayList<>();

    public ApiServicePresenter(IHomeFragment iHomeFragment)
    {
        this.iHomeFragment = iHomeFragment;
    }

    @Override
    public void getBakingRecipes() {

        final Call<List<BakingRecipe>> bakingRecipesCalls = ApiClient.getClient()
                .create(ApiService.class).getBakingRecipes();

        bakingRecipesCalls.enqueue(new Callback<List<BakingRecipe>>() {
            @Override
            public void onResponse(Call<List<BakingRecipe>> call, Response<List<BakingRecipe>> response) {
                bakingRecipes = response.body();
                iHomeFragment.listEquality(bakingRecipes);
            }

            @Override
            public void onFailure(Call<List<BakingRecipe>> call, Throwable t) {
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });
    }
}
