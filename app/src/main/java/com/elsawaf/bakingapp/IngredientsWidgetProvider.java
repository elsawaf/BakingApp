package com.elsawaf.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.elsawaf.bakingapp.activities.ChooseRecipeActivity;
import com.elsawaf.bakingapp.network.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    // to indicate that no recipe is added until now
    public static final int NO_RECIPE_ID = -1;
    public static final String TAG = "elsawafApp";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,  int recipeID) {

        RemoteViews views;
        if (recipeID == NO_RECIPE_ID) {
            views = getEmptyRecipeRemoteView(context);
        }
        else {
            views = getIngredientListRemoteView(context, recipeID);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getEmptyRecipeRemoteView(Context context) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        Intent chooseRecipeIntent = new Intent(context, ChooseRecipeActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context,
                0, chooseRecipeIntent, 0);
        views.setOnClickPendingIntent(R.id.btnAddRecipeAppWidget, recipePendingIntent);

        return views;
    }

    private static RemoteViews getIngredientListRemoteView(Context context, int recipeId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(Constants.KEY_RECIPE_ID, recipeId);
        views.setRemoteAdapter(R.id.listViewWidget, intent);

        Intent chooseRecipeIntent = new Intent(context, ChooseRecipeActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context,
                0, chooseRecipeIntent, 0);
        views.setOnClickPendingIntent(R.id.btnChangeRecipe, recipePendingIntent);

        views.setEmptyView(R.id.listViewWidget, R.id.tvEmptyView);
        Log.d(TAG, "getIngredientListRemoteView: ");
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateRecipeWidgets(context, appWidgetManager, appWidgetIds, NO_RECIPE_ID);
    }

    public static void updateRecipeWidgets (Context context, AppWidgetManager appWidgetManager,
                                            int[] appWidgetIds, int recipeId) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,  recipeId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

