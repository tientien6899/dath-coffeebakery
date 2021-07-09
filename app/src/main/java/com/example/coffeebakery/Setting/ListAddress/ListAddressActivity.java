package com.example.coffeebakery.Setting.ListAddress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.Receipt;
import com.example.coffeebakery.Receipt.ReceiptAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.uid;

public class ListAddressActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AddressAdapter adapter;
    ArrayList<Address> listAddress;
    Button addAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_address);
        Context context = this.getApplicationContext();
        AnhXa();
        listAddress = new ArrayList<Address>();
        mData.child("Sổ địa chỉ").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAddress.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_uid = data.child("uid").getValue().toString();
                    if(temp_uid.contains(uid)){
                        String temp_hoten = data.child("hoten").getValue().toString();
                        String temp_sdt = data.child("sdt").getValue().toString();
                        String temp_sonha = data.child("sonha").getValue().toString();
                        listAddress.add(new Address(uid,temp_hoten,temp_sdt,temp_sonha));
                    }
                }
                adapter = new AddressAdapter(listAddress,context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        recyclerView = findViewById(R.id.rcv_listaddress);
        addAddress = findViewById(R.id.btn_addaddress);
    }
}