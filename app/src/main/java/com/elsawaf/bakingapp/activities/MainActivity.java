package com.elsawaf.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new RecipeAdapter(new ArrayList<>(), this);
        recipeRecyclerView.setAdapter(adapter);

        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        Call<List<Recipe>> call = RecipeWebservice.getRetrofitClient(this).getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response != null) {
                    adapter.setRecipeList(response.body());
//                    Toast.makeText(MainActivity.this, response.body().get(0).getName(), Toast.LENGTH_SHORT).show();
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
