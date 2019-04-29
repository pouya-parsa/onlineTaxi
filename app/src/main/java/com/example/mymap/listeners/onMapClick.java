package com.example.mymap.listeners;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymap.R;
import com.example.mymap.ViewManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class onMapClick implements GoogleMap.OnMapClickListener {

    private  GoogleMap googleMap;
    private Marker start;
    private Marker destination;
    private AppCompatActivity activity;

    public onMapClick(GoogleMap googleMap, AppCompatActivity activity) {
        this.googleMap = googleMap;
        this.activity = activity;
    }

    @Override
    public void onMapClick(LatLng latLng) {
       if(start == null) {
           start =  googleMap.addMarker(
                   new MarkerOptions()
                           .position(latLng)
                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.mymarker))
           );


           destination = null;
       } else if(destination == null) {
           destination = googleMap.addMarker(
                   new MarkerOptions()
                   .position(latLng)
                   .icon(BitmapDescriptorFactory.fromResource(R.drawable.mymarker_end))
           );
           CameraUpdate update =
                   CameraUpdateFactory.newLatLngZoom(new LatLng(
                           (start.getPosition().latitude + destination.getPosition().latitude) / 2,
                           (start.getPosition().longitude + destination.getPosition().longitude) / 2
                   ), 12);
           googleMap.animateCamera(update);

           TextView balance_txt = activity.findViewById(R.id.balance);


           TextView price = activity.findViewById(R.id.price);
           price.setText(String.valueOf((int) (this.calculateBill())));

           ViewManager viewManager = new ViewManager(activity);
           viewManager.showRequestPanel();





       } else {
           destination.remove();
           start.remove();
           start = null;
           destination = null;
           start =  googleMap.addMarker(
                   new MarkerOptions()
                           .position(latLng)
                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.mymarker))
           );
           TextView price = activity.findViewById(R.id.price);
           price.setText("در انتظار انتخاب موقعیت");
       }

    }

    public double calculateBill() {
        double price = Math.floor(
                (Math.abs(start.getPosition().latitude - destination.getPosition().latitude) +
                Math.abs(start.getPosition().longitude - destination.getPosition().longitude)) * 121.5);

        price *= 1000;
        return price > 5500 ? price : 5500;
    }


    public void deleteMarkers() {
        start.remove();
        destination.remove();

        start = null;
        destination = null;

        ViewManager viewManager = new ViewManager(activity);
        viewManager.showMapPanel();
    }

}
