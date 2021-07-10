package com.example.coffeebakery.DetailReceipt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.DetailReceipt.helpers.FirebaseEventListenerHelper;
import com.example.coffeebakery.DetailReceipt.helpers.GoogleMapHelper;
import com.example.coffeebakery.DetailReceipt.helpers.MarkerAnimationHelper;
import com.example.coffeebakery.DetailReceipt.helpers.UiHelper;
import com.example.coffeebakery.DetailReceipt.interfaces.FirebaseDriverListener;
import com.example.coffeebakery.DetailReceipt.interfaces.LatLngInterpolator;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.ReceiptsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import static com.example.coffeebakery.SplashActivity.uid;
import static com.example.coffeebakery.SplashActivity.gmail;
public class DetailReceiptActivity extends AppCompatActivity implements FirebaseDriverListener {

    TextView ten_kh, sdt_kh, diachi, madon, ngaydat, thanhtien, tongmon, tongcong, phigh, trochuyen;
    RecyclerView recyclerView;
    DetailReveiptAdapter adapter;
    ArrayList<DetailReceipt> listchitiet;
    public static int tongslmon = 0;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2161;
    private static final String ONLINE_DRIVERS = "Tài Xế";
    private final GoogleMapHelper googleMapHelper = new GoogleMapHelper();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(ONLINE_DRIVERS);
    private GoogleMap googleMap;
    private LocationRequest locationRequest;
    private UiHelper uiHelper;
    private FirebaseEventListenerHelper firebaseEventListenerHelper;
    private FusedLocationProviderClient locationProviderClient;
    private Marker currentLocationMarker;
    private TextView totalOnlineDrivers;
    private boolean locationFlag = true;
    private DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location == null) return;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (locationFlag) {
                locationFlag = false;
                animateCamera(latLng);
            }
            showOrAnimateUserMarker(latLng);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_receipt);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.supportMap);
        uiHelper = new UiHelper(this);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> this.googleMap = googleMap);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable()) {
            Toast.makeText(this, "Play Services chưa được cài đặt!", Toast.LENGTH_SHORT).show();
            finish();
        } else requestLocationUpdates();
        totalOnlineDrivers = findViewById(R.id.totalOnlineDrivers);
        firebaseEventListenerHelper = new FirebaseEventListenerHelper(this);
        databaseReference.addChildEventListener(firebaseEventListenerHelper);

        AnhXa();

        Intent intent = getIntent();
        String md = intent.getStringExtra("MADON");
        String nd = intent.getStringExtra("NGAYDAT");
        String tt = intent.getStringExtra("TONGTIEN");
        String mgh = intent.getStringExtra("MAGIOHANG");
        String pgh = intent.getStringExtra("SHIP");
        String temp_tamtinh = intent.getStringExtra("TAMTINH");
        madon.setText(md);
        ngaydat.setText(nd);
        thanhtien.setText(tt);
        phigh.setText(pgh);
        thanhtien.setText(temp_tamtinh);

        listchitiet = new ArrayList<DetailReceipt>();
        data.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String userid = snap.child("nguoidung").getValue().toString();
                    String tep_md = snap.child("madon").getValue().toString();
                    if(userid.contains(uid) && tep_md.contains(md)){
                        String hoten = snap.child("hoten").getValue().toString();
                        String sdt = snap.child("sdt").getValue().toString();
                        String sonha = snap.child("sonha").getValue().toString();
                        ten_kh.setText(hoten);
                        sdt_kh.setText(sdt);
                        diachi.setText(sonha);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data.child("Đơn hàng").child("Chi tiết").child(md).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String giohang = snap.child("giohang").getValue().toString();
                    if(giohang.contains(mgh)){
                        String link = snap.child("hinhanh").getValue().toString();
                        String sl = snap.child("soluong").getValue().toString();
                        String ten = snap.child("ten").getValue().toString();
                        String gia = snap.child("tongtien").getValue().toString();
                        String temp_gia = gia.replace(".","");
                        tongslmon += Integer.parseInt(sl);
                        listchitiet.add(new DetailReceipt(link,sl,ten,temp_gia));
                    }
                    tongmon.setText(String.valueOf(tongslmon));
                    adapter = new DetailReveiptAdapter(listchitiet, DetailReceiptActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailReceiptActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //su kien chuyen sang trang tro chuyen
        trochuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gan su kien

            }
        });

        tongcong.setText(tt);
    }

    private void animateCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = googleMapHelper.buildCameraUpdate(latLng);
        googleMap.animateCamera(cameraUpdate);
    }

    private void showOrAnimateUserMarker(LatLng latLng){
        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(googleMapHelper.getCurrentLocationMarker(latLng));
        else
            MarkerAnimationHelper.animateMarkerToGB(
                    currentLocationMarker,
                    new LatLng(latLng.latitude,
                            latLng.longitude),
                    new LatLngInterpolator.Spherical());
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        if (!uiHelper.isHaveLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        if (uiHelper.isLocationProviderEnabled())
            uiHelper.showPositiveDialogWithListener(this, getResources().getString(R.string.need_location), getResources().getString(R.string.location_content), () -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)), "Turn On", false);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            int value = grantResults[0];
            if (value == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                finish();
            } else if (value == PackageManager.PERMISSION_GRANTED) requestLocationUpdates();
        }
    }

    @Override
    public void onDriverAdded(Driver driver) {
        MarkerOptions markerOptions = googleMapHelper.getDriverMarkerOptions(new LatLng(driver.getLat(), driver.getLng()));
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(driver.getDriverId());
        MarkerCollection.insertMarker(marker);
        totalOnlineDrivers.setText(getResources()
                .getString(R.string.total_online_drivers)
                .concat(" ")
                .concat(String
                        .valueOf(MarkerCollection
                                .allMarkers()
                                .size())));
    }

    @Override
    public void onDriverRemoved(Driver driver) {
        MarkerCollection.removeMarker(driver.getDriverId());
        totalOnlineDrivers.setText(getResources()
                .getString(R.string.total_online_drivers)
                .concat(" ")
                .concat(String
                        .valueOf(MarkerCollection
                                .allMarkers()
                                .size())));
    }

    @Override
    public void onDriverUpdated(Driver driver) {
        Marker marker = MarkerCollection.getMarker(driver.getDriverId());
        assert marker != null;

        MarkerAnimationHelper.animateMarkerToGB(marker, new LatLng(driver.getLat(), driver.getLng()), new LatLngInterpolator.Spherical());
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_receipt);
//
//    }

    private void AnhXa() {
        ten_kh = (TextView) findViewById(R.id.txt_Tenkhachhang);
        sdt_kh = (TextView) findViewById(R.id.txt_SDTkhachhang);
        diachi = (TextView) findViewById(R.id.txt_Diachigiaohang);
        madon = (TextView) findViewById(R.id.txt_Madonhang);
        ngaydat = (TextView) findViewById(R.id.txt_Thoigiandathang);
        thanhtien = (TextView) findViewById(R.id.txt_chitiettamtinh);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_Chitietdonhang);
        tongmon = (TextView) findViewById(R.id.txt_Soluongmon);
        tongcong = (TextView) findViewById(R.id.txt_Tongcong);
        phigh = (TextView) findViewById(R.id.txt_chitietphigiaohang);
        trochuyen = findViewById(R.id.txt_trochuyentaixe);
    }

    public void back(View view) {
        Intent intent = new Intent(DetailReceiptActivity.this, ReceiptsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(firebaseEventListenerHelper);
        locationProviderClient.removeLocationUpdates(locationCallback);
        MarkerCollection.clearMarkers();
        currentLocationMarker = null;
    }
}