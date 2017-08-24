package com.mostafaaryan.transitionalimageview;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mostafaaryan.transitionalimageview.controller.LargeImageActivity;
import com.mostafaaryan.transitionalimageview.model.TransitionalImage;
import com.mostafaaryan.transitionalimageview.utils.Utils;


/**
 * Created by Mostafa Aryan Nejad on 8/7/17.
 */

public class TransitionalImageView extends AppCompatImageView {

    private int imageResId = -1;
    private byte[] imageByteArray;
    private TransitionalImage transitionalImage;


    public TransitionalImageView(Context context) {
        super(context);
    }

    public TransitionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransitionalImageView);
        imageResId = a.getResourceId(R.styleable.TransitionalImageView_res_id, -1);
        a.recycle();

        if (imageResId != -1) setImageResource(imageResId);

        setOnImageClickListener();
    }

    public TransitionalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTransitionalImage(TransitionalImage transitionalImage) {
        if(imageResId == -1) {
            if(transitionalImage.getImageByteArray() != null && transitionalImage.getImageByteArray().length > 0)
                this.setImageBitmap(Utils.byteArrayToBitmap(transitionalImage.getImageByteArray()));
            else if ((imageResId = transitionalImage.getImageResId()) != -1) {
                setImageResource(imageResId);
            }
        }

        this.transitionalImage = transitionalImage;
    }

    private void setOnImageClickListener() {

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageResId != -1) {
                    if(transitionalImage != null) transitionalImage.setImageResId(imageResId);
                    else transitionalImage = new TransitionalImage(imageResId);
                }

                Intent intent = new Intent(getContext(), LargeImageActivity.class);
                /*intent.putExtra(getResources().getString(R.string.image_res_id), imageResId);*/
                /*intent.putExtra(getResources().getString(R.string.image_byte_array), imageByteArray);*/
                intent.putExtra(getResources().getString(R.string.transitional_image), transitionalImage);
                intent.putExtra(getResources().getString(R.string.view_info),
                        Utils.captureValues(getContext(), TransitionalImageView.this));

                getContext().startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(0, 0);

            }
        });

    }


    /*public void setImage(Bitmap bitmap) {
        this.setImageBitmap(bitmap);
        imageByteArray = Utils.bitmapToByteArray(bitmap);
    }*/
}
