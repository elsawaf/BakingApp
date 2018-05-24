package com.elsawaf.bakingapp.network;

import android.content.Context;

import com.elsawaf.bakingapp.BuildConfig;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeWebservice {

    public static RecipeInterface getRetrofitClient(final Context context){
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();





        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(OkHttpProvider.getOkHttpInstance(context)).build();

        RecipeInterface client = retrofit.create(RecipeInterface.class);

        return client;
    }
}
