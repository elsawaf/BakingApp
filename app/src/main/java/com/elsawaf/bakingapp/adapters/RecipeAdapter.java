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
import com.elsawaf.bakingapp.activities.MainActivity;
import com.elsawaf.bakingapp.activities.RecipeMasterActivity;
import com.elsawaf.bakingapp.model.Recipe;
import com.elsawaf.bakingapp.network.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;
    private int callingActivity;
    private OnChooseRecipeListener mCallback;

    /*This adapter is using to show Recipe List at the MainActivity
    * and at the ChooseRecipeActivity which update the ingredient widget*/

    public RecipeAdapter(List<Recipe> recipeList, Context context, int callingActivity) {
        this.recipeList = recipeList;
        this.context = context;
        this.callingActivity = callingActivity;
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
        if (callingActivity == MainActivity.CHOOSE_ACTIVITY) {
            holder.recipeClickTV.setText(R.string.title_add_to_widget);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public void setmCallback(OnChooseRecipeListener mCallback) {
        this.mCallback = mCallback;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRecipeName)
        TextView recipeNameTV;
        @BindView(R.id.tvRecipeClick)
        TextView recipeClickTV;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (callingActivity == MainActivity.MAIN_ACTIVITY) {
                    Intent intent = new Intent(context, RecipeMasterActivity.class);
                    intent.putExtra(Constants.KEY_RECIPE_DETAILS,
                            recipeList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
                else {
                    mCallback.onRecipeChoice(getAdapterPosition());
                }
            });
        }
    }

    public interface OnChooseRecipeListener{
        void onRecipeChoice(int pos);
    }

}
