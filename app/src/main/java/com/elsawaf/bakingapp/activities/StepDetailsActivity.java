package com.elsawaf.bakingapp.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.fragments.StepDetailsFragment;
import com.elsawaf.bakingapp.model.Step;
import com.elsawaf.bakingapp.network.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Step step = getIntent().getParcelableExtra(Constants.KEY_STEP_DETAILS);

        // Create new fragment if the Activity is created for first time
        if (savedInstanceState == null) {
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stepDetailsPlaceholder, stepDetailsFragment)
                    .commit();

        }
    }
}
