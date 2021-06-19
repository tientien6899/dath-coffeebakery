package com.example.coffeebakery;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.coffeebakery.Cart.CartFragment;
import com.example.coffeebakery.Home.HomeFragment;
import com.example.coffeebakery.Product.ProductFragment;
import com.example.coffeebakery.Receipt.ReceiptFragment;
import com.example.coffeebakery.Setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}