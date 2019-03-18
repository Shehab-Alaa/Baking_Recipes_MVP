package com.example.dell.bakingapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dell.bakingapp.model.Ingredient;

@Database(entities = {Ingredient.class} , version = 2)
abstract public class IngredientRoomDB extends RoomDatabase {

    public abstract IngredientDAO getMyDAO();

}
