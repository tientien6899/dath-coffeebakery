package com.example.coffeebakery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng vietnam = new LatLng(10.7985, 106.6855);// 14.0583° N, 108.2772° E
        map.addMarker(new MarkerOptions().position(vietnam).title("MEO COFFEE"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(vietnam,17));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}