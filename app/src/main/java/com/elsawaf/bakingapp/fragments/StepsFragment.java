package com.elsawaf.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.elsawaf.bakingapp.activities.StepDetailsActivity;
import com.elsawaf.bakingapp.adapters.StepsAdapter;
import com.elsawaf.bakingapp.model.Step;
import com.elsawaf.bakingapp.network.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment implements StepsAdapter.OnStepClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    OnStepClickFromFragment mCallback;

    StepsAdapter adapter;
    private ArrayList<Step> StepArrayList;

    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, rootView);

        StepArrayList = new ArrayList<>();
        adapter = new StepsAdapter(StepArrayList, getContext(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    public void setStepArrayList(ArrayList<Step> StepArrayList) {
        adapter.setStepList(StepArrayList);
    }

    @Override
    public void onStepSelected(int pos) {
        mCallback.onRecipeChoice(pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception

        try {
            mCallback = (OnStepClickFromFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickFromFragment");
        }
    }

    public interface OnStepClickFromFragment{
        void onRecipeChoice(int pos);
    }
}
