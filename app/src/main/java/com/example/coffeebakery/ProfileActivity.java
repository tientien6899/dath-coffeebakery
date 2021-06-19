package com.example.coffeebakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakery.LoginActivity.gmail;

public class ProfileActivity extends AppCompatActivity {

    EditText edthoten, edtsdt, edtsonhaduong;
    TextView txtusername;
    Button btndongy;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Anhxa();

        txtusername.setText(gmail);
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child("KHACHHANG").child(gmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Profile p = snapshot.getValue(Profile.class);
                    edthoten.setText(p.getHoten());
                    edtsdt.setText(p.getSdt());
                    edtsonhaduong.setText(p.getSonha());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = new Profile();
                p.hoten = edthoten.getText().toString().trim();
                p.sdt = edtsdt.getText().toString().trim();
                p.sonha = edtsonhaduong.getText().toString().trim();
                p.gmail = gmail;

                mData.child("KHACHHANG").child(p.getGmail()).setValue(p);

                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Anhxa() {
        edthoten = findViewById(R.id.edt_hoten);
        edtsdt = findViewById(R.id.edt_sdt);
        edtsonhaduong = findViewById(R.id.edt_Diachi);
        txtusername = findViewById(R.id.txt_username);
        btndongy = findViewById(R.id.btn_Dongy);
    }
}