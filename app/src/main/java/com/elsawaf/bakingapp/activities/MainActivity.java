package com.elsawaf.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.adapters.RecipeAdapter;
import com.elsawaf.bakingapp.model.Recipe;
import com.elsawaf.bakingapp.network.RecipeWebservice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewRecipes)
    RecyclerView recipeRecyclerView;

    RecipeAdapter adapter;
    List<Recipe> recipeList;

    public static final int MAIN_ACTIVITY = 1;
    public static final int CHOOSE_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addTheListToUI(MAIN_ACTIVITY);
    }

    protected void addTheListToUI(int whichActivity) {
        adapter = new RecipeAdapter(new ArrayList<>(), this, whichActivity);
        recipeRecyclerView.setAdapter(adapter);

        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        makeRecipesCall();
    }

    private void makeRecipesCall () {
        Call<List<Recipe>> call = RecipeWebservice.getRetrofitClient(this).getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response != null) {
                    recipeList = response.body();
                    adapter.setRecipeList(recipeList);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failure " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
