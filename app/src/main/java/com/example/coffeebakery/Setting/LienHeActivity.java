package com.example.coffeebakery.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeebakery.HomeActivity;
import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakery.HomeActivity.mData;

public class LienHeActivity extends AppCompatActivity {

    TextView ten, sdt, mail, diachi, ghichu;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        AnhXa();


        mData.child("ThongTinCH").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tench = snapshot.child("tencuahang").getValue().toString();
                String sdtch = snapshot.child("sdt").getValue().toString();
                String mailch = snapshot.child("email").getValue().toString();
                String dcch = snapshot.child("diachi").getValue().toString();
                String gcch = snapshot.child("ghichu").getValue().toString();

                ten.setText(tench);
                sdt.setText(sdtch);
                mail.setText(mailch);
                diachi.setText(dcch);
                ghichu.setText(gcch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        ten = findViewById(R.id.lienhe_tencuahang);
        sdt = findViewById(R.id.lienhe_sdtcuahang);
        mail = findViewById(R.id.lienhe_emailcuahang);
        diachi = findViewById(R.id.lienhe_diachicuahang);
        ghichu = findViewById(R.id.lienhe_ghichucuahang);
        back = findViewById(R.id.img_Backlienhe);
    }
}