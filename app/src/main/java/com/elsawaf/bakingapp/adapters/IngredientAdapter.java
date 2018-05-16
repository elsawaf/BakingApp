package com.elsawaf.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.RecipeViewHolder> {

    private List<Ingredient> ingredientList;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ingredient_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientNameTV.setText(ingredient.getName());
        holder.ingredientQuantityTV.setText("" + ingredient.getQuantity());
        holder.ingredientMeasureTV.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIngredientName)
        TextView ingredientNameTV;
        @BindView(R.id.tvIngredientQuantity)
        TextView ingredientQuantityTV;
        @BindView(R.id.tvIngredientMeasure)
        TextView ingredientMeasureTV;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
