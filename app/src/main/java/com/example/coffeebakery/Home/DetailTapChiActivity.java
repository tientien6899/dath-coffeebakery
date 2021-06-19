package com.example.coffeebakery.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.HomeActivity;
import com.example.coffeebakery.R;

public class DetailTapChiActivity extends AppCompatActivity {

    TextView tieude, ngaydang, noidung;
    ImageView hinhanh;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tap_chi);
        AnhXa();

        Intent intent = getIntent();
        String td = intent.getStringExtra("TIEUDE");
        String nd = intent.getStringExtra("NGAYDANG");
        String ndg = intent.getStringExtra("NOIDUNG");
        String img = intent.getStringExtra("HINHANH");

        tieude.setText(td);
        ngaydang.setText("Ngày đăng: " + nd);
        noidung.setText(ndg);
        Glide.with(this).load(img).into(hinhanh);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }


    private void AnhXa() {
        back = findViewById(R.id.img_BackDSSP);
        tieude = findViewById(R.id.thongtintapchi_tieude);
        ngaydang = findViewById(R.id.thongtintapchi_ngaydang);
        noidung = findViewById(R.id.thongtintapchi_noidung);
        hinhanh = findViewById(R.id.thongtintapchi_hinhanh);
    }
}