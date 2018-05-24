package com.elsawaf.bakingapp.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeWebservice {

    public static RecipeInterface getRetrofitClient(final Context context){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(OkHttpProvider.getOkHttpInstance(context)).build();

        RecipeInterface client = retrofit.create(RecipeInterface.class);

        return client;
    }
}
