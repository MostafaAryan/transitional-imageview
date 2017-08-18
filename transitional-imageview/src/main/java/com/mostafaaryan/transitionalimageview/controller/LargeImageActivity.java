package com.mostafaaryan.transitionalimageview.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.mostafaaryan.transitionalimageview.R;
import com.mostafaaryan.transitionalimageview.model.TransitionalImage;
import com.mostafaaryan.transitionalimageview.utils.Utils;


public class LargeImageActivity extends AppCompatActivity {

    View background;
    ImageView largeImage;
    TransitionalImage transitionalImage;

    Bundle startValues;
    Bundle endValues;
    float scaleX;
    float scaleY;
    int deltaX;
    int deltaY;
    int animationDuration = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        background = findViewById(R.id.background);
        largeImage = (ImageView) findViewById(R.id.large_image);

        extractViewInfo(getIntent());

        /*int imageResId = getIntent().getIntExtra(getString(R.string.image_res_id), 0);*/
        /*byte[] byteArray = getIntent().getByteArrayExtra(getString(R.string.image_byte_array));*/
        transitionalImage = getIntent().getExtras().getParcelable(getString(R.string.transitional_image));

        if(transitionalImage != null) retrieveImage();

        
    }

    private void retrieveImage() {

        byte[] byteArray = transitionalImage.getImageByteArray();
        
        if(transitionalImage.getImageResId() != -1) {
            largeImage.setImageResource(transitionalImage.getImageResId());
            setProperties();
        } else if(byteArray.length > 0) {
            largeImage.setImageBitmap(Utils.byteArrayToBitmap(byteArray));
            setProperties();
        } else finish();
        
    }

    private void extractViewInfo(Intent i) {
        startValues = i.getBundleExtra(getString(R.string.view_info));
    }

    private void setProperties(){
        if(transitionalImage.getDuration() != -1)
            animationDuration = transitionalImage.getDuration();

        if(transitionalImage.getBackgroundColor() != -1)
            background.setBackgroundColor(transitionalImage.getBackgroundColor());

        onUiReady();
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
                .setDuration(animationDuration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(1.0f)
                .start();
        largeImage.animate()
                .setDuration(animationDuration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0)
                .start();
    }

    private void runExitAnimation() {
        background.animate()
                .setDuration(animationDuration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(0.0f)
                .start();
        largeImage.animate()
                .setDuration(animationDuration)
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
        return (float) startValue / endValue;
    }

    private int translationDelta(@NonNull Bundle startValues, @NonNull Bundle endValues, @NonNull String propertyName) {
        int startValue = startValues.getInt(propertyName);
        int endValue = endValues.getInt(propertyName);
        return startValue - endValue;
    }


    public void onClickBackground(View v) {
        runExitAnimation();
    }


}
