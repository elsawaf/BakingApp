package com.elsawaf.bakingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elsawaf.bakingapp.fragments.IngredientsFragment;
import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.fragments.StepDetailsFragment;
import com.elsawaf.bakingapp.fragments.StepsFragment;
import com.elsawaf.bakingapp.model.Ingredient;
import com.elsawaf.bakingapp.model.Recipe;
import com.elsawaf.bakingapp.model.Step;
import com.elsawaf.bakingapp.network.Constants;

import java.util.ArrayList;

public class RecipeMasterActivity extends AppCompatActivity implements StepsFragment.OnStepClickFromFragment {

    private boolean isTwoPane;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);

        recipe = getIntent().getParcelableExtra(Constants.KEY_RECIPE_DETAILS);

        IngredientsFragment ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.ingredientsFragment);
        ingredientsFragment.setIngredientArrayList((ArrayList<Ingredient>) recipe.getIngredientList());


        StepsFragment stepsFragment = (StepsFragment) getSupportFragmentManager().findFragmentById(R.id.stepsFragment);
        stepsFragment.setStepArrayList((ArrayList<Step>) recipe.getStepList());

        if (findViewById(R.id.layoutTowPane) != null) {
            isTwoPane = true;

            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe.getStepList().get(0));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stepDetailsPlaceholder, stepDetailsFragment)
                    .commit();
        }
        else {
            isTwoPane =false;
        }

    }

    @Override
    public void onRecipeChoice(int pos) {
        if (isTwoPane) {
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe.getStepList().get(pos));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.stepDetailsPlaceholder, stepDetailsFragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(Constants.KEY_STEP_DETAILS, recipe.getStepList().get(pos));
            startActivity(intent);
        }
    }
}
