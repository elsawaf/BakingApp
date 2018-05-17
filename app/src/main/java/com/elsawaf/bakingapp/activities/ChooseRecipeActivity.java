package com.elsawaf.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.widget.Toast;

import com.elsawaf.bakingapp.IngredientsWidgetProvider;
import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.adapters.RecipeAdapter;

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
        Toast.makeText(this, "update the widget", Toast.LENGTH_SHORT).show();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        //Now update all widgets
        IngredientsWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds, pos);

        finish();
    }
}
