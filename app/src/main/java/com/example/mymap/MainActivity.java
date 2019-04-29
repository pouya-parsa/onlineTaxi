package com.example.mymap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymap.listeners.onMapClick;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;

    Location userCurrentlocation;

    LocationManager locationManager;
    LocationListener locationListener = null;

    userDatasource userDS;

    onMapClick onMapClick;


    ViewManager viewManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewManager = new ViewManager(this);
        viewManager.showMapPanel();

        //FirebaseApp.initializeApp(this);


        TextView balance_txt = findViewById(R.id.balance);
        TextView fullname_txt = findViewById(R.id.fullname);

        userDS = new userDatasource("pouya", balance_txt, fullname_txt);


    }


    private void goToLocation(double latitude, double longitude, int zoom) {
        CameraUpdate update =
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(latitude, longitude),
                        zoom);
        googleMap.animateCamera(update);
    }


    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getApplicationContext(), "request permissions ...", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            this.setMapListener();
        }

        onMapClick = new onMapClick(googleMap, this);
        googleMap.setOnMapClickListener(onMapClick);
    }


    public void goToUserLocation(View view) {
        if(userCurrentlocation != null) {
            googleMap.setMyLocationEnabled(true);
            Toast.makeText(getApplicationContext(), "در حال انتقال به جایی که هستید", Toast.LENGTH_SHORT).show();
            goToLocation(userCurrentlocation.getLatitude(), userCurrentlocation.getLongitude(), 15);

        } else {
            Toast.makeText(getApplicationContext(), "قادر به پیدا کردن مکانتان نیستیم لطفا دوباره سعی کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                    this.setMapListener();
                    goToLocation(userCurrentlocation.getLatitude(), userCurrentlocation.getLongitude(), 15);
                }
                return;
            }
        }
    }

    private void setMapListener() {
        if(locationManager == null) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    userCurrentlocation = location;
//                    Toast.makeText(getApplicationContext(), "مکان فعلی شما تغییر کرد", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    public void deleteMarkers(View view) {
        this.onMapClick.deleteMarkers();
    }

    public void arrived(View view) throws IOException {

        TextView balance_txt = findViewById(R.id.balance);

        TextView price = findViewById(R.id.price);

        if(Double.valueOf(price.getText().toString()) > Double.valueOf(balance_txt.getText().toString())) {
            Toast.makeText(getApplicationContext(), "اعتبار شما کافی نیست", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userDS.updateBalance("pouya",
                    Double.valueOf(price.getText().toString()));

            viewManager.arrivedToDestiny();
        }


    }

    public void saveNumber(View view) {
        Toast.makeText(getApplicationContext(), "شماره ی راننده در مخاطبین شما ذخیره شد", Toast.LENGTH_SHORT).show();
    }



}

//        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                MarkerOptions markerOptions1 = new MarkerOptions()
//                        .title("you clicked here")
//                        .position(latLng);
//
//                googleMap.addMarker(markerOptions1);
//            }
//        });


//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                Geocoder geocoder = new Geocoder(MainActivity.this);
//                List<Address> list = null;
//
//                try {
//                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//
//                MarkerOptions markerOptions1 = new MarkerOptions()
//                        .title("you clicked here")
//                        .position(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()))
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//                        .snippet(list.get(0).getCountryName()+ list.get(0).getLocality() + list.get(0).getSubLocality() + list.get(0).getPostalCode())
//                        .draggable(true);
//
//                googleMap.addMarker(markerOptions1);
//
//
//            }
//        });

//        MarkerOptions markerOptions = new MarkerOptions()
//                .title("pouyaaaa")
//                .position(new LatLng(35.701149, 51.389372))
//                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
//                .snippet("helloooo");
//
//
//        googleMap.addMarker(markerOptions);


//        LatLng latLng = new LatLng(35.701149, 51.389372);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//
//        googleMap.animateCamera(update);


//    private void drawLine() {
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .add(new LatLng(35.701149, 51.389372))
//                .add(new LatLng(35.700830, 51.376831));
//
//        Polyline line = googleMap.addPolyline(polylineOptions);
//    }