package com.example.dell.bakingapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET ("baking.json")
    Call<List<BakingRecipe>> getBakingRecipes();
}
