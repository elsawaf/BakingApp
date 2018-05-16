package com.elsawaf.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.activities.RecipeMasterActivity;
import com.elsawaf.bakingapp.model.Recipe;
import com.elsawaf.bakingapp.network.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeNameTV.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRecipeName)
        TextView recipeNameTV;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RecipeMasterActivity.class);
                intent.putExtra(Constants.KEY_RECIPE_DETAILS,
                        recipeList.get(getAdapterPosition()));
                context.startActivity(intent);
            });
        }
    }

}
