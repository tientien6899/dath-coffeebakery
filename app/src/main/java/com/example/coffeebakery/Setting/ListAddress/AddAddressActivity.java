package com.example.coffeebakery.Setting.ListAddress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.uid;
public class AddAddressActivity extends AppCompatActivity {

    EditText hoten, sdt, sonha;
    TextView luu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        AnhXa();

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.child("Sổ địa chỉ").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String temp_hoten = hoten.getText().toString();
                        String temp_sdt = sdt.getText().toString();
                        String temp_sonha = sonha.getText().toString();
                        Address ad = new Address(uid,temp_hoten,temp_sdt,temp_sonha);

                        mData.child("Sổ địa chỉ").child(uid).setValue(ad);
                        Intent intent = new Intent(AddAddressActivity.this, ListAddressActivity.class);
                        Toast.makeText(AddAddressActivity.this, "Thêm mới sổ địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void AnhXa() {
        hoten = findViewById(R.id.edt_hoten);
        sdt = findViewById(R.id.edt_sdt);
        sonha = findViewById(R.id.edt_sonha);
        luu = findViewById(R.id.txt_luu);
    }
}