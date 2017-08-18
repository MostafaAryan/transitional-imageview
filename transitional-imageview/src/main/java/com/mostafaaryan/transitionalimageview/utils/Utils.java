package com.mostafaaryan.transitionalimageview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mostafaaryan.transitionalimageview.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mostafa Aryan Nejad on 8/7/17.
 */

public class Utils {

    public static Bundle captureValues(Context c, View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt(c.getResources().getString(R.string.view_location_left), screenLocation[0]);
        b.putInt(c.getResources().getString(R.string.view_location_top), screenLocation[1]);
        b.putInt(c.getResources().getString(R.string.view_width), view.getWidth());
        b.putInt(c.getResources().getString(R.string.view_height), view.getHeight());
        return b;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static boolean isValidString(String s) {
        return s != null && !s.isEmpty() && !s.trim().isEmpty() && !s.equals("null");
    }

    public static void log(String s) {
        Log.d("tiv-test" , s);
    }


}
