package com.elsawaf.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.elsawaf.bakingapp.model.Ingredient;
import com.elsawaf.bakingapp.network.Constants;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    public static final String TAG = "elsawafApp";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int id = intent.getIntExtra(Constants.KEY_RECIPE_ID, -1);
        Log.d(TAG, "onGetViewFactory: " + id);
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<Ingredient> ingredientList;
        private Context context;

        public ListRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged: ");
            if (ingredientList != null) ingredientList.clear();
            ingredientList = Ingredient.listAll(Ingredient.class);

        }

        @Override
        public void onDestroy() {
            ingredientList = null;

        }

        @Override
        public int getCount() {
            if (ingredientList == null) return 0;
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Ingredient ingredient = ingredientList.get(position);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_item);
            views.setTextViewText(R.id.tvIngredientName, ingredient.getName());
            views.setTextViewText(R.id.tvIngredientQuantity, "" + ingredient.getQuantity());
            views.setTextViewText(R.id.tvIngredientMeasure, ingredient.getMeasure());


            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
