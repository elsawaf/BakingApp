package com.elsawaf.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsawaf.bakingapp.R;
import com.elsawaf.bakingapp.model.Recipe;
import com.elsawaf.bakingapp.model.Step;
import com.elsawaf.bakingapp.network.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    private static final String KEY_VIDEO_POSITION = "videoPos";
    @BindView(R.id.tvStepDescription)
    TextView stepDescriptionTV;
    @BindView(R.id.ivNoVideo)
    ImageView noVideoIV;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.ivStepThumbnail)
    ImageView stepThumbIV;
    private SimpleExoPlayer mExoPlayer;

    Step step;

    public static StepDetailsFragment newInstance(Step step){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_STEP_DETAILS, step);

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setArguments(bundle);

        return stepDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        step = getArguments().getParcelable(Constants.KEY_STEP_DETAILS);

        stepDescriptionTV.setText(step.getDescription());

        // if the step has video show it .. else show the default image
        String url = step.getVideoURL();
        // no video Url
        if (TextUtils.isEmpty(url)) {
            mPlayerView.setVisibility(View.INVISIBLE);
            noVideoIV.setVisibility(View.VISIBLE);
        }
        else {
            // Initialize the player.
            initializePlayer(Uri.parse(url));
        }

        String imageUrl = step.getThumbnailURL();
        // if image path is empty then hide image view
        if (TextUtils.isEmpty(imageUrl)){
            stepThumbIV.setVisibility(View.GONE);
        }
        // else .. then show image or error image if not loaded
        else {
            Picasso.with(getContext()).load(imageUrl).fit().centerCrop()
                    .error(R.drawable.image_not_available)
                    .into(stepThumbIV);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // we need to save the video current position state if the video is playing ready state
        if (mExoPlayer.getPlaybackState() == ExoPlayer.STATE_READY) {
            long currentPos = mExoPlayer.getCurrentPosition();
            outState.putLong(KEY_VIDEO_POSITION, currentPos);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // restore the video position after any configuration change
        if (savedInstanceState != null) {
            long restorePos = savedInstanceState.getLong(KEY_VIDEO_POSITION, 0);
            if (mExoPlayer != null && mExoPlayer.getPlayWhenReady()) {
                mExoPlayer.seekTo(restorePos);
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
