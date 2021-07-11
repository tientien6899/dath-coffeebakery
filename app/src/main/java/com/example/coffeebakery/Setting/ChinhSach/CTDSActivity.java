package com.example.coffeebakery.Setting.ChinhSach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CTDSActivity extends AppCompatActivity {

    TextView tieude, noidung;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctdsactivity);
        AnhXa();

        Intent intent = getIntent();
        String temp_tieude = intent.getStringExtra("TIEUDE");

        mData.child("ChinhSach").child(temp_tieude).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ChinhSach cs = snapshot.getValue(ChinhSach.class);
                tieude.setText(cs.getTieude());
                noidung.setText(cs.getNoidung());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AnhXa() {
        tieude = findViewById(R.id.txt_tieudechinhsach);
        noidung = findViewById(R.id.txt_noidungchinhsach);
    }
}