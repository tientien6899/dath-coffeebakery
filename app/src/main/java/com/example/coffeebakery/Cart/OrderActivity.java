package com.example.coffeebakery.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.Product.Product;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Setting.ListAddress.Address;
import com.example.coffeebakery.Setting.ListAddress.AddressAdapter;
import com.example.coffeebakery.Setting.ListAddress.ListAddressActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.uid;

public class OrderActivity extends AppCompatActivity {
    RecyclerView donhang;
    OrderAdapter adapter;
    ArrayList<Cart> listCart;
    TextView thaydoi, hoten, sdt, sonha;
    public static int tongSL = 0;
    public static int stt = 0;
    TextView tongsl, tamtinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Context context = this.getApplicationContext();
        AnhXa();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            hoten.clearComposingText();
            sdt.clearComposingText();
            sonha.clearComposingText();
            String str_hoten = bundle.getString("HOTEN");
            String str_sdt = bundle.getString("SDT");
            String str_sonha = bundle.getString("SONHA");
            hoten.setText(str_hoten);
            sdt.setText(str_sdt);
            sonha.setText(str_sonha);
        }else {
            mData.child("Khách hàng").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        String userid = snap.child("uid").getValue().toString();
                        if(userid.contains(uid)){
                            String temp_hoten = snap.child("hoten").getValue().toString();
                            String temp_sdt = snap.child("sdt").getValue().toString();
                            String temp_sonha = snap.child("sonha").getValue().toString();
                            hoten.setText(temp_hoten);
                            sdt.setText(temp_sdt);
                            sonha.setText(temp_sonha);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, ListAddressActivity.class);
                startActivity(intent);
            }
        });

        listCart = new ArrayList<Cart>();
        mData.child("Giỏ hàng").child(uid).child("Cart"+STT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_gh = data.child("giohang").getValue().toString();
                    if(temp_gh.contains("Cart"+STT)){
                        String temp_sttgh = data.child("sttgiohang").getValue().toString();
                        String temp_ma = data.child("ma").getValue().toString();
                        String temp_ten = data.child("ten").getValue().toString();
                        String temp_gia = data.child("gia").getValue().toString();
                        String temp_soluong = data.child("soluong").getValue().toString();
                        String temp_hinhanh = data.child("hinhanh").getValue().toString();
                        String temp_tongtien = data.child("tongtien").getValue().toString();
                        String temp_kichthuoc = data.child("kichthuoc").getValue().toString();
                        String temp_ghichu = data.child("ghichu").getValue().toString();
                        String temp_nguoidung = data.child("nguoidung").getValue().toString();

                        listCart.add(new Cart(temp_sttgh,temp_gh,temp_ma,temp_ten,temp_gia,temp_soluong,
                                temp_hinhanh,temp_tongtien,temp_kichthuoc,temp_ghichu,temp_nguoidung));
                    }
                }
                adapter = new OrderAdapter(listCart,context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                donhang.setAdapter(adapter);
                donhang.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mData.child("Giỏ hàng").child(uid).child("Cart"+STT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int a = 0, b = 0;
                for(DataSnapshot data : snapshot.getChildren()){
                    String ma = data.child("giohang").getValue().toString();
                    if(ma.contains("Cart"+STT)){
                        String sl = data.child("soluong").getValue().toString();
                        String gia = data.child("tongtien").getValue().toString();
                        String temp_gia = gia.replace(".","");
                        a += Integer.parseInt(sl);
                        b += Integer.parseInt(temp_gia);
                    }
                }
                tongsl.setText(a+"");
                tamtinh.setText(b+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AnhXa() {
        donhang = findViewById(R.id.rcv_danhsachdonhang);
        thaydoi = findViewById(R.id.txt_thaydoi);
        hoten = findViewById(R.id.txt_hoten_donhang);
        sdt = findViewById(R.id.txt_sdt_donhang);
        sonha = findViewById(R.id.txt_sonha_donhang);
        tongsl = (TextView) findViewById(R.id.txt_slspdonhang);
        tamtinh = (TextView) findViewById(R.id.txt_tongdonhang);
    }
}