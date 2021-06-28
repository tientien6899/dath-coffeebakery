package com.example.coffeebakery.Receipt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coffeebakery.R;
import com.example.coffeebakery.Setting.ListAddress.AddAddressActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.gmail;
import static com.example.coffeebakery.LoginActivity.uid;

public class ReceiptsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ReceiptAdapter adapter;
    ArrayList<Receipt> listReceipt;
    TextView addlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        Context context = this.getApplicationContext();
        AnhXa();
        listReceipt = new ArrayList<Receipt>();
        mData.child("Đơn hàng").child("Thông tin").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_nguoidung = data.child("nguoidung").getValue().toString();
                    if(temp_nguoidung.contains(uid)){
                        String temp_madon = data.child("madon").getValue().toString();
                        String temp_ngaydat = data.child("ngaydat").getValue().toString();
                        String temp_tongtien = data.child("tongtien").getValue().toString();
                        String temp_trangthai = data.child("trangthai").getValue().toString();

                        listReceipt.add(new Receipt(temp_madon,temp_ngaydat,temp_trangthai,temp_tongtien,temp_nguoidung));
                    }
                }
                adapter = new ReceiptAdapter(listReceipt,context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        addlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ReceiptsActivity.this, AddAddressActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void AnhXa() {
        recyclerView = findViewById(R.id.rcv_dsdonhang);
        addlist = findViewById(R.id.txt_addaddress);
    }
}