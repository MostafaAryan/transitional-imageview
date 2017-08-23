package com.ariannejad.mostafa.transitional_imageview_implementation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ariannejad.mostafa.transitional_imageview_implementation.R;
import com.ariannejad.mostafa.transitional_imageview_implementation.controller.MainActivity;
import com.ariannejad.mostafa.transitional_imageview_implementation.model.Shoe;
import com.mostafaaryan.transitionalimageview.TransitionalImageView;
import com.mostafaaryan.transitionalimageview.model.TransitionalImage;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mostafa Aryan Nejad on 8/11/17.
 */

public class ShoeAdapter extends RecyclerView.Adapter<ShoeAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Shoe> shoes = new ArrayList<>();

    public ShoeAdapter(Context context, ArrayList<Shoe> shoes) {
        mContext = context;
        this.shoes = shoes;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoe, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Shoe shoe = shoes.get(position);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{

                    final Bitmap bitmap = Picasso.with(mContext).load(shoe.getImageUrl()).get();
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TransitionalImage transitionalImage = new TransitionalImage.Builder()
                                    .duration(500)
                            /*.backgroundColor(ContextCompat.getColor(, R.color.colorAccent))*/
                                    /*.image(R.drawable.sample_image)*/
                                    .image(bitmap)
                                    .create();
                            holder.image.setTransitionalImage(transitionalImage);
                            bitmap.recycle();
                        }
                    });
                } catch (IOException e){e.printStackTrace();}
            }
        });


        holder.title.setText(shoe.getTitle());
        holder.sizes.setText("37,38,39,40");


    }

    @Override
    public int getItemCount() {
        return shoes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView sizes;
        public TransitionalImageView image;


        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.shoe_title);
            sizes = (TextView) itemView.findViewById(R.id.shoe_sizes);
            image = (TransitionalImageView) itemView.findViewById(R.id.shoe_image);

        }

    }


}
