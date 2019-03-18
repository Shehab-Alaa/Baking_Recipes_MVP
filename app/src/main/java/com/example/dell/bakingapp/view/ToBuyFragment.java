package com.example.dell.bakingapp.view;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.bakingapp.IngredientRoomDB;
import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.ToBuyAdapter;
import com.example.dell.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ToBuyFragment extends android.support.v4.app.Fragment{

    private static List<Ingredient> toBuyList = new ArrayList<>();
    private static ToBuyAdapter toBuyAdapter;
    private RecyclerView rvToBuy;
    private TextView clearToBuyIngredients;
    private IngredientRoomDB ingredientRoomDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tobuy, container , false);

        clearToBuyIngredients = view.findViewById(R.id.clearToBuyIngredients);

        ingredientRoomDB = Room.databaseBuilder(getActivity() , IngredientRoomDB.class , "mIngredients")
                .allowMainThreadQueries().build();

        clearToBuyIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ToBuyAdapter.clearIngredientsHolder.size() == 0)
                {
                    Toast.makeText(getActivity(), "nothing is selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (Ingredient ingredient : ToBuyAdapter.clearIngredientsHolder)
                    {
                        ingredientRoomDB.getMyDAO().deleteIngredient(ingredient.getId());
                        onRemoveItemFromBuyList(ingredient);
                    }
                    ToBuyAdapter.restoreDataHolder();
                    Toast.makeText(getActivity(), "cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getToBuyListIngredients();

        rvToBuy = view.findViewById(R.id.rvToBuyIngredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvToBuy.setLayoutManager(layoutManager);
        rvToBuy.setHasFixedSize(true);
        toBuyAdapter = new ToBuyAdapter(getActivity() , toBuyList);
        rvToBuy.setAdapter(toBuyAdapter);


        return view;
    }

    private void getToBuyListIngredients()
    {
        toBuyList = ingredientRoomDB.getMyDAO().getIngredients();
    }

    public static void onAddItemToBuyList(Ingredient ingredient)
    {
       toBuyList.add(ingredient);
       toBuyAdapter.notifyDataSetChanged();
    }

    public static void onRemoveItemFromBuyList(Ingredient ingredient)
    {
        int indexHolder = toBuyList.indexOf(ingredient);
        toBuyList.remove(ingredient);
        toBuyAdapter.notifyItemRemoved(indexHolder);
        toBuyAdapter.notifyDataSetChanged();
    }
}
