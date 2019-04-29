package com.example.mymap;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewManager {

    AppCompatActivity activity;

    public ViewManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showRequestPanel() {
        ConstraintLayout pricePanel = activity.findViewById(R.id.requestPanel);
        pricePanel.setVisibility(View.VISIBLE);


        ImageView priceHolder = activity.findViewById(R.id.priceHolder);
        priceHolder.setVisibility(View.VISIBLE);

        TextView toomanLabel = activity.findViewById(R.id.toomanLabel);
        toomanLabel.setVisibility(View.VISIBLE);

        TextView price = activity.findViewById(R.id.price);
        price.setVisibility(View.VISIBLE);

        ConstraintLayout mapPanel = activity.findViewById(R.id.mapPanel);
        mapPanel.setVisibility(View.GONE);

        ImageView arrivedIcon = activity.findViewById(R.id.arrivedIcon);
        arrivedIcon.setVisibility(View.GONE);

        ConstraintLayout arrivedPanel = activity.findViewById(R.id.arrivedToDestiny);
        arrivedPanel.setVisibility(View.GONE);

    }

    public void showMapPanel() {
        ConstraintLayout pricePanel = activity.findViewById(R.id.requestPanel);
        pricePanel.setVisibility(View.GONE);

        TextView price = activity.findViewById(R.id.price);
        price.setVisibility(View.GONE);

        ImageView priceHolder = activity.findViewById(R.id.priceHolder);
        priceHolder.setVisibility(View.GONE);

        TextView toomanLabel = activity.findViewById(R.id.toomanLabel);
        toomanLabel.setVisibility(View.GONE);

        ConstraintLayout mapPanel = activity.findViewById(R.id.mapPanel);
        mapPanel.setVisibility(View.VISIBLE);

        ImageView arrivedIcon = activity.findViewById(R.id.arrivedIcon);
        arrivedIcon.setVisibility(View.GONE);

        ConstraintLayout arrivedPanel = activity.findViewById(R.id.arrivedToDestiny);
        arrivedPanel.setVisibility(View.GONE);

    }

    public void arrivedToDestiny() {
        //request Panel
        ConstraintLayout pricePanel = activity.findViewById(R.id.requestPanel);
        pricePanel.setVisibility(View.GONE);

        TextView price = activity.findViewById(R.id.price);
        price.setVisibility(View.GONE);

        ImageView priceHolder = activity.findViewById(R.id.priceHolder);
        priceHolder.setVisibility(View.GONE);

        TextView toomanLabel = activity.findViewById(R.id.toomanLabel);
        toomanLabel.setVisibility(View.GONE);


        //map Panel
        ConstraintLayout mapPanel = activity.findViewById(R.id.mapPanel);
        mapPanel.setVisibility(View.GONE);



        //arived Panel
        ConstraintLayout arrivedPanel = activity.findViewById(R.id.arrivedToDestiny);
        arrivedPanel.setVisibility(View.VISIBLE);

        ImageView arrivedIcon = activity.findViewById(R.id.arrivedIcon);
        arrivedIcon.setVisibility(View.VISIBLE);


    }
}
