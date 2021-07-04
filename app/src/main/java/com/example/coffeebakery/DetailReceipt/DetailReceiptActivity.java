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
import com.example.coffeebakery.Receipt.ReceiptsActivity;
import com.example.coffeebakery.Setting.ListAddress.ListAddressActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.LoginActivity.gmail;
import static com.example.coffeebakery.LoginActivity.uid;

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
        data.child("Đơn hàng").child("Thông tin").child(uid).addValueEventListener(new ValueEventListener() {
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

        data.child("Đơn hàng").child("Chi tiết").child(uid).child(md).addListenerForSingleValueEvent(new ValueEventListener() {
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

        tongcong.setText(tt);
    }

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
    }

    public void back(View view) {
        Intent intent = new Intent(DetailReceiptActivity.this, ReceiptsActivity.class);
        startActivity(intent);
    }
}