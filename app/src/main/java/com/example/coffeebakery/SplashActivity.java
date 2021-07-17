package com.example.coffeebakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    private static DatabaseReference conn = FirebaseDatabase.getInstance().getReference(); // cu phap khai bao truy xuat firebase
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    public static String gmail = "";
    public static String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //lay dia chi MAC tren thiet bi de luu dang nhap
                WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE); // quan ly thiet bi wifi tren may
                WifiInfo info = manager.getConnectionInfo(); //lay ket noi
                String address = info.getMacAddress(); //lay dia chi MAC tren thiet bi

                //kiem tra luu dang nhap tai khoan
                conn.child("Ghi nhớ đăng nhập").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Remember re = snap.getValue(Remember.class);
                                if(address.contains(re.getMAC())){ //true neu ton tai, false neu khong co
                                    uid = re.getUserid();
                                    gmail = re.getTendangnhap();
                                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                }
                            }
                        }
                        else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}