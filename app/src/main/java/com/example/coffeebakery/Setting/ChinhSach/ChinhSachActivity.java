package com.example.coffeebakery.Setting.ChinhSach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChinhSachActivity extends AppCompatActivity {

    RecyclerView danhsach;
    ChinhSachAdapter adapter;
    ArrayList<ChinhSach> arrayList;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sach);
        AnhXa();
        danhsach.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<ChinhSach>();

        mData.child("ChinhSach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    ChinhSach cs = snap.getValue(ChinhSach.class);
                    arrayList.add(cs);
                }
                adapter = new ChinhSachAdapter(arrayList, ChinhSachActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChinhSachActivity.this);
                danhsach.setAdapter(adapter);
                danhsach.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AnhXa() {
        danhsach = findViewById(R.id.list_book);
    }
}