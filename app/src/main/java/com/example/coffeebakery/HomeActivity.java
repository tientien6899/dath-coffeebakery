package com.example.coffeebakery;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.coffeebakery.Cart.CartFragment;
import com.example.coffeebakery.Home.HomeFragment;
import com.example.coffeebakery.Product.ProductFragment;
import com.example.coffeebakery.Setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakery.SplashActivity.uid;
public class HomeActivity extends AppCompatActivity {

    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createNotificationChannel();

        mData.child("Thông báo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot data : snapshot.getChildren()){
                        ThongBao noti = data.getValue(ThongBao.class);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this, "CHANNEL_ID")
                                .setSmallIcon(R.drawable.ic_outline_notifications_none_24)
                                .setContentTitle(noti.tieude)
                                .setContentText(noti.noidung)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HomeActivity.this);
                        managerCompat.notify(1, builder.build());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bnv = findViewById(R.id.menu_bottom_navigation);
        openFragment(new HomeFragment());
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.trangchu:
                        openFragment(new HomeFragment());
                        return true;
                    case R.id.sanpham:
                        openFragment(new ProductFragment());
                        return true;
                    case R.id.giohang:
                        openFragment(new CartFragment());
                        return true;
//                    case R.id.hoadon:
//                        openFragment(new ReceiptFragment());
//                        return true;
                    case R.id.caidat:
                        openFragment(new SettingFragment());
                        return true;
                }
                return false;
            }
        });

    }

    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linearlayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}