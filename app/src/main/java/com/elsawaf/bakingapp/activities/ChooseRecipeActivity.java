package com.elsawaf.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;

import com.elsawaf.bakingapp.IngredientsWidgetProvider;
import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.adapters.RecipeAdapter;
import com.elsawaf.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.ButterKnife;

public class ChooseRecipeActivity extends MainActivity implements RecipeAdapter.OnChooseRecipeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addTheListToUI(CHOOSE_ACTIVITY);
        adapter.setmCallback(this);
    }

    @Override
    public void onRecipeChoice(int pos) {
        // get ingredient list by recipe position
        // then save the list to the database
        List<Ingredient> ingredientList = recipeList.get(pos).getIngredientList();
        // each time clear the database to delete last list
        Ingredient.deleteAll(Ingredient.class);
        Ingredient.saveInTx(ingredientList);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        //Trigger data update to handle the ListView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);
        //Now update all widgets
        IngredientsWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, pos);

        finish();
    }
}
