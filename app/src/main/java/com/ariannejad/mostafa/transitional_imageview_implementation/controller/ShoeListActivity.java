package com.ariannejad.mostafa.transitional_imageview_implementation.controller;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ariannejad.mostafa.transitional_imageview_implementation.R;
import com.ariannejad.mostafa.transitional_imageview_implementation.adapter.ShoeAdapter;
import com.ariannejad.mostafa.transitional_imageview_implementation.model.Shoe;

import java.util.ArrayList;

public class ShoeListActivity extends AppCompatActivity {

    private RecyclerView shoeRecyclerView;
    private ArrayList<Shoe> shoes = new ArrayList<>();
    private ActionBar actionBar;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_list);

        shoeRecyclerView = (RecyclerView) findViewById(R.id.shoe_recycler_view);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        setOnOffsetChangedListener();
        collapsingToolbar.setTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setTitle("");


        populateList();

    }

    private void populateList() {
        shoes.add(new Shoe("Skechers Relaxed Fit Empire Game On Walking Shoe",
                "https://www.shoes.com/pm/skech/skech800828_42965_hd2.jpg"));

        shoes.add(new Shoe("Skechers After Burn Memory Fit Geardo High Top Trainer",
                "https://www.shoes.com//pm/skech/skech798492_42965_hd2.jpg"));

        shoes.add(new Shoe("New Balance Fresh Foam Zante v3 Running Shoe",
                "https://www.shoes.com/pi/newba/hd/newba805216_436896_hd.jpg"));

        for(int i = 0 ; i <= 5 ; i++ ) {
            shoes.addAll(shoes);
        }

        displayList();
    }

    private void displayList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new ShoeAdapter(this, shoes);
        shoeRecyclerView.setLayoutManager(layoutManager);
        shoeRecyclerView.setAdapter(adapter);
    }

    private void setOnOffsetChangedListener() {

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isDisplayed = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScroll = appBarLayout.getTotalScrollRange();

                if (totalScroll + verticalOffset == 0) {
                    if (actionBar != null) {
                        actionBar.setTitle("Sneakers");
                    }
                    isDisplayed = true;
                } else if (isDisplayed) {
                    if (actionBar != null)
                        actionBar.setTitle("");
                    isDisplayed = false;
                }
            }

        });

    }


}
