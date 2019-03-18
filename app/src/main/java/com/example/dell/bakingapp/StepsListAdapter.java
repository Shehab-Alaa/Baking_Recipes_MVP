package com.example.dell.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.bakingapp.model.Ingredient;
import com.example.dell.bakingapp.model.Step;
import com.example.dell.bakingapp.view.RecipeStepDetails;

import java.util.ArrayList;
import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder> {

    Context context;
    List<Step> steps = new ArrayList<>();

    public StepsListAdapter(Context context , List<Step> steps)
    {
        this.context = context;
        this.steps = steps;
    }

    @Override
    public StepsListAdapter.StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item ,parent , false);
        StepsListAdapter.StepsViewHolder stepsViewHolder = new StepsListAdapter.StepsViewHolder(view);
        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsListAdapter.StepsViewHolder holder, final int position) {
        holder.onBind(steps.get(position));

        holder.stepItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , RecipeStepDetails.class);
                intent.putExtra("RecipeStepDetails" , steps.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {

        private TextView stepId;
        public android.support.v7.widget.CardView stepItem;

        public StepsViewHolder(View itemView) {
            super(itemView);

            stepId = itemView.findViewById(R.id.stepId);
            stepItem = itemView.findViewById(R.id.stepItem);
        }

        public void onBind(Step step)
        {
           stepId.setText( "Step " + (step.getId() + 1));
        }
    }
}

