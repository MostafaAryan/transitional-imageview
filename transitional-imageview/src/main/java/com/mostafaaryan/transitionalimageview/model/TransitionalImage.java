package com.mostafaaryan.transitionalimageview.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.mostafaaryan.transitionalimageview.utils.Utils;

/**
 * Created by Mostafa Aryan Nejad on 8/9/17.
 */

public class TransitionalImage implements Parcelable {

    private int duration;
    private int backgroundColor;
    private int imageResId;
    private byte[] imageByteArray;


    public TransitionalImage(int duration, int backgroundColor, int imageResId, byte[] imageByteArray) {
        this.duration = duration;
        this.backgroundColor = backgroundColor;
        this.imageResId = imageResId;
        this.imageByteArray = imageByteArray;
    }

    public TransitionalImage(int imageResId) {
        this.imageResId = imageResId;
        this.duration = -1;
        this.backgroundColor = -1;
    }

    public TransitionalImage (Parcel in) {
        this.duration = in.readInt();
        this.backgroundColor= in.readInt();
        this.imageResId = in.readInt();
        int imageByteArrayLength = in.readInt();
        if(imageByteArrayLength > 0) {
            this.imageByteArray = new byte[imageByteArrayLength];
            in.readByteArray(this.imageByteArray);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(duration);
        parcel.writeInt(backgroundColor);
        parcel.writeInt(imageResId);
        if(imageByteArray != null) parcel.writeInt(imageByteArray.length);
        parcel.writeByteArray(imageByteArray);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public TransitionalImage createFromParcel(Parcel parcel) {
            return new TransitionalImage(parcel);
        }

        @Override
        public TransitionalImage[] newArray(int size) {
            return new TransitionalImage[size];
        }

    };

    public int getDuration() {
        return duration;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }


    public static class Builder {

        private int duration = -1;
        private int backgroundColor = -1;
        private int imageResId = -1;
        private byte[] imageByteArray;


        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder backgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder image(int imageResId) {
            this.imageResId = imageResId;
            return this;
        }

        public Builder image(Bitmap imageBitmap) {
            this.imageByteArray = Utils.bitmapToByteArray(imageBitmap);
            return this;
        }

        public TransitionalImage create() {
            return new TransitionalImage(duration, backgroundColor, imageResId, imageByteArray);
        }

    }

}
