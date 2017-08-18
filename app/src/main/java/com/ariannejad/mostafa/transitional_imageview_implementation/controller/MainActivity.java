package com.ariannejad.mostafa.transitional_imageview_implementation.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ariannejad.mostafa.transitional_imageview_implementation.R;
import com.mostafaaryan.transitionalimageview.TransitionalImageView;
import com.mostafaaryan.transitionalimageview.model.TransitionalImage;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private String imageUrl = "https://image.freepik.com/free-icon/android-logo_318-54237.jpg";
    TransitionalImageView tiv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tiv = (TransitionalImageView) findViewById(R.id.sample_image);

        loadImage();

    }

    private void loadImage() {

        /*
        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DisplayImageOptions dio = new DisplayImageOptions.Builder()
                        .cacheInMemory(false).build();
                final Bitmap bmp = imageLoader.loadImageSync(imageUrl, dio);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tiv.setImage(bmp);
                    }
                });

            }
        });*/


        /* Glide.with(this).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                tiv.setImage(resource);
            }
        }); */

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap b = Picasso.with(MainActivity.this).load(imageUrl).get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            tiv.setImage(b);
                            TransitionalImage transitionalImage = new TransitionalImage.Builder()
                                    .duration(500)
                                    .backgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                                    /*.image(R.drawable.sample_image)*/
                                    .image(b)
                                    .create();
                            tiv.setTransitionalImage(transitionalImage);
                        }
                    });

                } catch (IOException e) {e.printStackTrace();}
            }
        });


    }


    public void onClickShoes(View view) {
        startActivity(new Intent(this, ShoeListActivity.class));
    }



}
