package com.elsawaf.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.activities.StepDetailsActivity;
import com.elsawaf.bakingapp.model.Step;
import com.elsawaf.bakingapp.network.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecipeViewHolder> {

    private List<Step> stepList;
    private Context context;
    private OnStepClickListener listener;

    public StepsAdapter(List<Step> stepList, Context context, OnStepClickListener listener) {
        this.stepList = stepList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.step_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.stepShortDescTV.setText(step.getShortDescription());
        holder.stepDescriptionTV.setText(step.getDescription());

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvStepShortDes)
        TextView stepShortDescTV;
        @BindView(R.id.tvStepDescription)
        TextView stepDescriptionTV;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                listener.onStepSelected(getAdapterPosition());
            });
        }
    }

    public interface OnStepClickListener {
        void onStepSelected(int pos);
    }
}
