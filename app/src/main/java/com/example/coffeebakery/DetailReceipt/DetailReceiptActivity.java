package com.example.coffeebakery.DetailReceipt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coffeebakery.HomeActivity;
import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.LoginActivity.gmail;

public class DetailReceiptActivity extends AppCompatActivity {

    TextView ten_kh, sdt_kh, diachi, madon, ngaydat, thanhtien, tongmon, tongcong, phigh;
    RecyclerView recyclerView;
    DetailReveiptAdapter adapter;
    ArrayList<DetailReceipt> listchitiet;
    public static int tongslmon = 0;
    private DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_receipt);
        AnhXa();
        listchitiet = new ArrayList<DetailReceipt>();
        data.child("KHACHHANG").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String mail = snap.child("gmail").getValue().toString();
                    if(mail.contains(gmail)){
                        String hoten = snap.child("hoten").getValue().toString();
                        String sdt = snap.child("sdt").getValue().toString();
                        String sonha = snap.child("sonha").getValue().toString();
                        String phuong = snap.child("phuong").getValue().toString();
                        String quan = snap.child("quan").getValue().toString();
                        String tp = snap.child("thanhpho").getValue().toString();
                        ten_kh.setText(hoten);
                        sdt_kh.setText(sdt);
                        diachi.setText(sonha + ", " + phuong + ", " + quan + ", " + tp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        String md = intent.getStringExtra("MADON");
        String nd = intent.getStringExtra("NGAYDAT");
        String tt = intent.getStringExtra("TONGTIEN");
        String mgh = intent.getStringExtra("MAGIOHANG");

        madon.setText(md);
        ngaydat.setText(nd);
        thanhtien.setText(tt);

        data.child("GioHang").child(gmail).child(md).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String giohang = snap.child("giohang").getValue().toString();
                    if(giohang.contains(mgh)){
                        String link = snap.child("hinhanh").getValue().toString();
                        String sl = snap.child("soluong").getValue().toString();
                        String ten = snap.child("ten").getValue().toString();
                        String gia = snap.child("tongtien").getValue().toString();
                        String temp_gia = gia.substring(0,gia.length()-2);
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

        tongcong.setText(String.valueOf(Integer.parseInt(thanhtien.getText().toString()) + Integer.parseInt(phigh.getText().toString())));
    }

    private void AnhXa() {
        ten_kh = (TextView) findViewById(R.id.txt_Tenkhachhang);
        sdt_kh = (TextView) findViewById(R.id.txt_SDTkhachhang);
        diachi = (TextView) findViewById(R.id.txt_Diachigiaohang);
        madon = (TextView) findViewById(R.id.txt_Madonhang);
        ngaydat = (TextView) findViewById(R.id.txt_Thoigiandathang);
        thanhtien = (TextView) findViewById(R.id.txt_Thanhtien);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_Chitietdonhang);
        tongmon = (TextView) findViewById(R.id.txt_Soluongmon);
        tongcong = (TextView) findViewById(R.id.txt_Tongcong);
        phigh = (TextView) findViewById(R.id.txt_Phigiaohang);
    }

    public void back(View view) {
        Intent intent = new Intent(DetailReceiptActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}