package com.example.dell.bakingapp.view;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.w3c.dom.Text;

public class RecipeStepDetails extends AppCompatActivity {

    private Step recipeStep;

    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer = null;

    private TextView recipeShortDescription , recipeDescription;

    private boolean isPlaying = false;
    private long currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);

        recipeStep = (Step) getIntent().getExtras().getSerializable("RecipeStepDetails");

        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
              setFullScreenApp();
        }

        else {
            getSupportActionBar().setTitle("Step " + (recipeStep.getId() + 1));


            recipeShortDescription = findViewById(R.id.recipeShortDescription);
            recipeDescription = findViewById(R.id.recipetDescription);

            recipeShortDescription.setText(recipeStep.getShortDescription());
            recipeDescription.setText(recipeStep.getDescription());
        }


        exoPlayerView = findViewById(R.id.exo_player_view);

        if (recipeStep.getVideoURL().equals(""))
            Toast.makeText(this, "this video is NOT FOUND", Toast.LENGTH_SHORT).show();

        else {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                Uri videoUri = Uri.parse(recipeStep.getVideoURL());

                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

                exoPlayerView.setPlayer(exoPlayer);

                if (savedInstanceState != null)
                {
                    isPlaying = savedInstanceState.getBoolean("isPlaying");
                }
                if (isPlaying) {
                    currentPosition = savedInstanceState.getLong("currentPosition");
                    exoPlayer.seekTo(currentPosition);
                }

                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null){
        exoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exoPlayer != null) {
        exoPlayer.release();
        }
    }

    private void setFullScreenApp()
    {
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });

        }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (exoPlayer != null)
        {
            savedInstanceState.putBoolean("isPlaying" , true);
            savedInstanceState.putLong("currentPosition" , exoPlayer.getCurrentPosition());
            savedInstanceState.putInt("playbackState" , exoPlayer.getPlaybackState());
        }
        else
        {
            savedInstanceState.putBoolean("isPlaying" , false);
        }
    }



}
