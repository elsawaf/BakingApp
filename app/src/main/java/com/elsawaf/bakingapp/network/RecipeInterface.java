package com.elsawaf.bakingapp.network;

import com.elsawaf.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}
