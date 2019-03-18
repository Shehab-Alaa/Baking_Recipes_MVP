package com.example.dell.bakingapp;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ToBuyAdapter extends RecyclerView.Adapter<ToBuyAdapter.ToBuyViewHolder> {

    Context context;
    List<Ingredient> toBuyList = new ArrayList<>();
    public static List<Ingredient> clearIngredientsHolder = new ArrayList<>();

    public ToBuyAdapter(Context context , List<Ingredient> toBuyList)
    {
        this.context = context;
        this.toBuyList = toBuyList;
    }

    @Override
    public ToBuyAdapter.ToBuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item ,parent , false);
        ToBuyAdapter.ToBuyViewHolder toBuyViewHolder = new ToBuyAdapter.ToBuyViewHolder(view);


        return toBuyViewHolder;
    }

    @Override
    public void onBindViewHolder(final ToBuyAdapter.ToBuyViewHolder holder, final int position) {
        holder.onBind(toBuyList.get(position));

        holder.cbToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbToBuy.isChecked())
                {
                    clearIngredientsHolder.add(toBuyList.get(position));
                }
                else
                {
                    clearIngredientsHolder.remove(toBuyList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return toBuyList.size();
    }

    public class ToBuyViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientQuantity , ingredientType ;
        private android.support.v7.widget.CardView ingredientItem;
        public CheckBox cbToBuy;

        public ToBuyViewHolder(View itemView) {
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
            cbToBuy.setChecked(false);
        }

    }

    public static void restoreDataHolder()
    {
        clearIngredientsHolder.clear();
        clearIngredientsHolder = new ArrayList<>();
    }
}

