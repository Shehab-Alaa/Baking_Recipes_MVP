package com.example.dell.bakingapp;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.bakingapp.model.Ingredient;
import com.example.dell.bakingapp.view.ToBuyFragment;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {

    Context context;
    List<Ingredient> ingredients = new ArrayList<>();
    IngredientRoomDB ingredientRoomDB;

    public IngredientsListAdapter(Context context , List<Ingredient> ingredients)
    {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public IngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item ,parent , false);
        IngredientsViewHolder ingredientsViewHolder = new IngredientsViewHolder(view);

        ingredientRoomDB = Room.databaseBuilder(context , IngredientRoomDB.class , "mIngredients")
                .allowMainThreadQueries().build();


        return ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(final IngredientsListAdapter.IngredientsViewHolder holder, final int position) {
         holder.onBind(ingredients.get(position));

         holder.cbToBuy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (holder.cbToBuy.isChecked())
                 {
                     ingredientRoomDB.getMyDAO().insertIngredient(ingredients.get(position));
                     ToBuyFragment.onAddItemToBuyList(ingredients.get(position));
                     Toast.makeText(context, "added to Buy", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     ingredientRoomDB.getMyDAO().deleteIngredient(ingredients.get(position).getId());
                     ToBuyFragment.onRemoveItemFromBuyList(ingredients.get(position));
                     Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientQuantity , ingredientType ;
        private android.support.v7.widget.CardView ingredientItem;
        public CheckBox cbToBuy;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientQuantity = itemView.findViewById(R.id.ingredientQuantity);
            ingredientType = itemView.findViewById(R.id.ingredientType);
            ingredientItem = itemView.findViewById(R.id.ingredientItem);
            cbToBuy = itemView.findViewById(R.id.checkboxToBuy);
        }

        public void onBind(Ingredient ingredient)
        {
           ingredientQuantity.setText(String.valueOf(ingredient.getQuantity().intValue()) + ingredient.getMeasure());
           ingredientType.setText(ingredient.getIngredient());
        }
    }
}
