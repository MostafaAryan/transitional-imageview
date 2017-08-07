package com.mostafaaryan.transitionalimageview;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.mostafaaryan.transitionalimageview.controller.LargeImageActivity;
import com.mostafaaryan.transitionalimageview.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by Mostafa Aryan Nejad on 8/7/17.
 */

public class TransitionalImageView extends AppCompatImageView {

    private int imageResId = 0;
    private String imageUrl = "";


    public TransitionalImageView(Context context) {
        super(context);
    }

    public TransitionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransitionalImageView);
        imageUrl = a.getString(R.styleable.TransitionalImageView_img_url);
        imageResId = a.getResourceId(R.styleable.TransitionalImageView_res_id, 0);
        a.recycle();

        if(Utils.isValidString(imageUrl)) {
            Picasso.with(context).load(imageUrl).into(this);
        } else if (imageResId != 0) setImageResource(imageResId);

        setOnImageClickListener();
    }

    public TransitionalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setOnImageClickListener() {

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), LargeImageActivity.class);
                intent.putExtra(getResources().getString(R.string.image_res_id), imageResId);
                intent.putExtra(getResources().getString(R.string.image_url), imageUrl);
                intent.putExtra(getResources().getString(R.string.view_info),
                        Utils.captureValues(getContext(), TransitionalImageView.this));

                getContext().startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(0, 0);

            }
        });

    }


}
