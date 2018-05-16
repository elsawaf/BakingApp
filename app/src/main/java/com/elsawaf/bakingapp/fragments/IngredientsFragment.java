package com.elsawaf.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.adapters.IngredientAdapter;
import com.elsawaf.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    IngredientAdapter adapter;
    private ArrayList<Ingredient> ingredientArrayList;

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ButterKnife.bind(this, rootView);

        ingredientArrayList = new ArrayList<>();
        adapter = new IngredientAdapter(ingredientArrayList, getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        adapter.setIngredientList(ingredientArrayList);
    }
}
