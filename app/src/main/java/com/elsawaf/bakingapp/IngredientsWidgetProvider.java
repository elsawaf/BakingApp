package com.elsawaf.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.elsawaf.bakingapp.activities.ChooseRecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeID) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.btnAddRecipeAppWidget, "" + recipeID);

        Intent chooseRecipeIntent = new Intent(context, ChooseRecipeActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context,
                0, chooseRecipeIntent, 0);
        views.setOnClickPendingIntent(R.id.btnAddRecipeAppWidget, recipePendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateRecipeWidgets(context, appWidgetManager, appWidgetIds, 0);
    }

    public static void updateRecipeWidgets (Context context, AppWidgetManager appWidgetManager,
                                            int[] appWidgetIds, int recipeID) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeID);
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

