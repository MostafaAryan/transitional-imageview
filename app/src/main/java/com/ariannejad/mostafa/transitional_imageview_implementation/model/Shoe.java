package com.ariannejad.mostafa.transitional_imageview_implementation.model;

import java.util.ArrayList;

/**
 * Created by Mostafa Aryan Nejad on 8/11/17.
 */

public class Shoe {

    private String Title;
    private String imageUrl;

    public Shoe(String title, String imageUrl) {
        Title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
