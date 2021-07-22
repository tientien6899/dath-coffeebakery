package com.example.coffeebakery.DetailReceipt.helpers;

import com.example.coffeebakery.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;


public class GoogleMapHelper {

    private static final int ZOOM_LEVEL = 18;
    private static final int TILT_LEVEL = 25;

    //Hàm này để xử lý Camera nó zoome tới position
    public CameraUpdate buildCameraUpdate(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .tilt(TILT_LEVEL)
                .zoom(ZOOM_LEVEL)
                .build();
        return CameraUpdateFactory.newCameraPosition(cameraPosition);
    }


    //MarkerOption để đặt position nó có icon thay thể icon dấu targer màu đỏ
    public MarkerOptions getDriverMarkerOptions(LatLng position) {
        return  getMarkerOptions(position, R.drawable.ic_moto2);
    }



    public MarkerOptions getCurrentLocationMarker(LatLng position) {
        return getMarkerOptions(position, 0);
    }

    //Đoạn này sẽ cho kiểu flat vs sử dụng Bitmap
    private MarkerOptions getMarkerOptions(LatLng position, int resource) {
        BitmapDescriptor bitmapDescriptor;
        if (resource != 0)
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(resource);
        else
            bitmapDescriptor = BitmapDescriptorFactory.defaultMarker();
        return new MarkerOptions()
                .icon(bitmapDescriptor)
                .position(position)
                .flat(true);
    }
}
