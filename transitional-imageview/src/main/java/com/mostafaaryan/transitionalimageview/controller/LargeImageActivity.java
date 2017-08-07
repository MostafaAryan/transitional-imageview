package com.mostafaaryan.transitionalimageview.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.mostafaaryan.transitionalimageview.R;
import com.mostafaaryan.transitionalimageview.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class LargeImageActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION = 500;

    View background;
    ImageView largeImage;

    Bundle startValues;
    Bundle endValues;
    float scaleX;
    float scaleY;
    int deltaX;
    int deltaY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        background = findViewById(R.id.background);
        largeImage = (ImageView) findViewById(R.id.large_image);


        extractViewInfo(getIntent());

        int imageResId = getIntent().getIntExtra(getString(R.string.image_res_id), 0);
        String imageUrl = getIntent().getStringExtra(getString(R.string.image_url));
        if(Utils.isValidString(imageUrl)) {
            Log.d("test", " loading url into large image");
            Picasso.with(this).load(imageUrl).into(largeImage, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("test", " url loaded");
                    onUiReady();
                }

                @Override
                public void onError() {
                    finish();
                }
            });
        } else if(imageResId != 0) {
            largeImage.setImageResource(imageResId);
            onUiReady();
        } else {
            finish();
        }



        /* Picasso.with(this).load(getIntent().getStringExtra(getString(R.string.image_res_id))).into(largeImage, new Callback() {
            @Override
            public void onSuccess() {
                onUiReady();
            }

            @Override
            public void onError() {
            }
        }); */


    }


    private void extractViewInfo(Intent i) {
        startValues = i.getBundleExtra(getString(R.string.view_info));
    }

    private void onUiReady() {
        largeImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                largeImage.getViewTreeObserver().removeOnPreDrawListener(this);
                prepareScene();
                runEnterAnimation();
                return true;
            }
        });
    }

    private void prepareScene() {
        endValues = Utils.captureValues(this, largeImage);
        if (startValues != null && endValues != null) {
            // calculate the scale and positoin deltas
            scaleX = scaleDelta(startValues, endValues, getString(R.string.view_width));
            scaleY = scaleDelta(startValues, endValues, getString(R.string.view_height));
            deltaX = translationDelta(startValues, endValues, getString(R.string.view_location_left));
            deltaY = translationDelta(startValues, endValues, getString(R.string.view_location_top));

            //fix the scaling effect
            deltaX = (int) (deltaX - ((largeImage.getWidth() - (largeImage.getWidth() * scaleX)) / 2));
            deltaY = (int) (deltaY - ((largeImage.getHeight() - (largeImage.getHeight() * scaleY)) / 2));

            // scale and reposition the image
            largeImage.setScaleX(scaleX);
            largeImage.setScaleY(scaleY);
            largeImage.setTranslationX(deltaX);
            largeImage.setTranslationY(deltaY);
            background.setAlpha(0.0f);

        }
    }

    @Override
    public void onBackPressed() {
        runExitAnimation();
    }

    private void runEnterAnimation() {
        background.setVisibility(View.VISIBLE);
        largeImage.setVisibility(View.VISIBLE);
        // finally, run the animation
        background.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(1.0f)
                .start();
        largeImage.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private void runExitAnimation() {
        background.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(0.0f)
                .start();
        largeImage.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleX(scaleX)
                .scaleY(scaleY)
                .translationX(deltaX)
                .translationY(deltaY)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }).start();
    }

    private float scaleDelta(@NonNull Bundle startValues, @NonNull Bundle endValues, @NonNull String propertyName) {
        int startValue = startValues.getInt(propertyName);
        int endValue = endValues.getInt(propertyName);
        float delta = (float) startValue / endValue;
        return delta;
    }

    private int translationDelta(@NonNull Bundle startValues, @NonNull Bundle endValues, @NonNull String propertyName) {
        int startValue = startValues.getInt(propertyName);
        int endValue = endValues.getInt(propertyName);
        int delta = startValue - endValue;
        return delta;
    }


    public void onClickBackground(View v) {
        runExitAnimation();
    }


}
