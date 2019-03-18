package com.example.dell.bakingapp;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dell.bakingapp.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {

    @Insert
    void insertIngredient(Ingredient ingredient);

    @Query("select * from ingredients")
    List<Ingredient> getIngredients();

    @Query("DELETE FROM Ingredients WHERE id = :ingredientId")
    void deleteIngredient(int ingredientId);

}
