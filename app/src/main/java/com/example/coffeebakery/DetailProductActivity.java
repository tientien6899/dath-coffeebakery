package com.example.coffeebakery;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.Cart.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.coffeebakery.Cart.CartFragment.tongSL;
import static com.example.coffeebakery.LoginActivity.uid;

public class DetailProductActivity extends AppCompatActivity {

    TextView txt_detailtensp, txt_detailgiasp, txt_detailmotasp, txt_soluongsp, tongsl, tongtien, tensp;
    Button btn_tang, btn_giam, btn_datmon;
    RadioButton rb_nho, rb_vua, rb_lon;
    EditText edt_ghichu;
    ImageView img_detailhinhsp;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myData = database.getReference();
    public static int slmon = 1;
    public static int STT = 0;
    static int temp = 0;
    String kichthuoc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        AnhXa();
        Intent intent = getIntent();
        Glide.with(DetailProductActivity.this).load(intent.getStringExtra("LINK")).into(img_detailhinhsp);
        String link = intent.getStringExtra("LINK");
        txt_detailtensp.setText(intent.getStringExtra("TENSP"));

        int temp_giasp = Integer.parseInt(intent.getStringExtra("GIAS"));
        if(temp_giasp >= 1000000){
            temp_giasp = temp_giasp / 1000000;
            txt_detailgiasp.setText(temp_giasp + ".000.000 ");
        } else if(temp_giasp >= 1000){
            temp_giasp = temp_giasp / 1000;
            txt_detailgiasp.setText(temp_giasp + ".000 ");
        }

//        txt_detailgiasp.setText(intent.getStringExtra("GIAS"));
        txt_detailmotasp.setText(intent.getStringExtra("MOTA"));
        tongtien.setText(txt_detailgiasp.getText().toString().trim());
        tensp.setText(intent.getStringExtra("TENSP"));
        String masp = intent.getStringExtra("MASP");
        String danhmuc = intent.getStringExtra("DANHMUC");

        if(danhmuc.contains("Thức ăn")){
            rb_vua.setEnabled(false);
            rb_lon.setEnabled(false);
        }

        //Thiết lập tổng tiền
        final int s = Integer.parseInt(intent.getStringExtra("GIAS"));
        final int m = Integer.parseInt(intent.getStringExtra("GIAM"));
        final int l = Integer.parseInt(intent.getStringExtra("GIAL"));

        //Thiết lập chọn size
        rb_nho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int sl = Integer.parseInt(txt_soluongsp.getText().toString().trim());
                    int temp_ssl = s * sl;
                    if(temp_ssl >= 1000000){
                        temp_ssl = temp_ssl / 1000000;
                        tongtien.setText(temp_ssl + ".000.000 ");
                    } else if(temp_ssl >= 1000){
                        temp_ssl = temp_ssl / 1000;
                        tongtien.setText(temp_ssl + ".000 ");
                    }
                    int temp_s = s;
                    if(temp_s >= 1000000){
                        temp_s = temp_s / 1000000;
                        txt_detailgiasp.setText(temp_s + ".000.000 ");
                    } else if(temp_s >= 1000){
                        temp_s = temp_s / 1000;
                        txt_detailgiasp.setText(temp_s + ".000 ");
                    }
                    kichthuoc = "Nhỏ (S)";
                }
            }
        });

        rb_vua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int sl = Integer.parseInt(txt_soluongsp.getText().toString().trim());
                    int temp_msl = m * sl;
                    if(temp_msl >= 1000000){
                        temp_msl = temp_msl / 1000000;
                        tongtien.setText(temp_msl + ".000.000 ");
                    } else if(temp_msl >= 1000){
                        temp_msl = temp_msl / 1000;
                        tongtien.setText(temp_msl + ".000 ");
                    }
                    int temp_m = m;
                    if(temp_m >= 1000000){
                        temp_m = temp_m / 1000000;
                        txt_detailgiasp.setText(temp_m + ".000.000 ");
                    } else if(temp_m >= 1000){
                        temp_m = temp_m / 1000;
                        txt_detailgiasp.setText(temp_m + ".000 ");
                    }
                    kichthuoc = "Vừa (M)";
                }
            }
        });

        rb_lon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int sl = Integer.parseInt(txt_soluongsp.getText().toString().trim());
                    tongtien.setText(String.valueOf(l * sl));
                    txt_detailgiasp.setText(l + "");
                    int temp_lsl = l * sl;
                    if(temp_lsl >= 1000000){
                        temp_lsl = temp_lsl / 1000000;
                        tongtien.setText(temp_lsl + ".000.000 ");
                    } else if(temp_lsl >= 1000){
                        temp_lsl = temp_lsl / 1000;
                        tongtien.setText(temp_lsl + ".000 ");
                    }
                    int temp_l = l;
                    if(temp_l >= 1000000){
                        temp_l = temp_l / 1000000;
                        txt_detailgiasp.setText(temp_l + ".000.000 ");
                    } else if(temp_l >= 1000){
                        temp_l = temp_l / 1000;
                        txt_detailgiasp.setText(temp_l + ".000 ");
                    }
                    kichthuoc = "Lớn (L)";
                }
            }
        });
        final int[] sluong = {Integer.parseInt(txt_soluongsp.getText().toString())};

         //Nút giảm số lượng
        btn_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sluong[0] -= 1;

                if (sluong[0] == 1) {
                    btn_giam.setVisibility(View.INVISIBLE);
                }
                tongSL = tongSL - Integer.parseInt(txt_soluongsp.getText().toString().trim()) + sluong[0];
                txt_soluongsp.setText(String.valueOf(sluong[0]));
            }
        });

        //Nút tăng số lượng
        btn_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sluong[0] > 0){
                    btn_giam.setVisibility(View.VISIBLE);
                }
                sluong[0] += 1;
                tongSL = tongSL - Integer.parseInt(txt_soluongsp.getText().toString().trim()) + sluong[0];
                txt_soluongsp.setText(String.valueOf(sluong[0]));
            }
        });

        txt_soluongsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                int ab = Integer.parseInt(String.valueOf(cs));
                if(rb_nho.isChecked())
                {
                    temp = Integer.parseInt(String.valueOf(s)) * ab;
                    if(temp >= 1000000){
                        temp = temp / 1000000;
                        tongtien.setText(temp + ".000.000 ");
                    }else if(temp >= 1000){
                        temp = temp / 1000;
                        tongtien.setText(temp + ".000 ");
                    }
                }

                if(rb_vua.isChecked()){
                    temp = Integer.parseInt(String.valueOf(m)) * ab;
                    if(temp >= 1000000){
                        temp = temp / 1000000;
                        tongtien.setText(temp + ".000.000 ");
                    }else if(temp >= 1000){
                        temp = temp / 1000;
                        tongtien.setText(temp + ".000 ");
                    }
                }
                if(rb_lon.isChecked()) {
                    temp = Integer.parseInt(String.valueOf(l)) * ab;
                    if(temp >= 1000000){
                        temp = temp / 1000000;
                        tongtien.setText(temp + ".000.000 ");
                    }else if(temp >= 1000){
                        temp = temp / 1000;
                        tongtien.setText(temp + ".000 ");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Thiết lập ĐẶT MÓN
        btn_datmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STT = 1;
                if(kichthuoc == ""){
                    kichthuoc = "Nhỏ (S)";
                }
                Cart cart = new Cart(String.valueOf(slmon),
                        "Cart" + STT,
                        masp,
                        tensp.getText().toString().trim(),
                        txt_detailgiasp.getText().toString().trim(),
                        txt_soluongsp.getText().toString().trim(),
                        link,
                        tongtien.getText().toString().trim(),
                        kichthuoc,
                        edt_ghichu.getText().toString().trim(),
                        uid);
                    myData.child("Giỏ hàng").child(uid).child("Cart" + STT).child(cart.getSttgiohang()).setValue(cart);
                    tongSL += Integer.parseInt(cart.getSoluong());
                    slmon++;
                    Toast.makeText(DetailProductActivity.this, "Thêm sản phẩm vào giỏ hàng thành công !", Toast.LENGTH_SHORT).show();
                }

        });

    }

    private void AnhXa() {
        txt_detailtensp = (TextView) findViewById(R.id.txt_DetailTenSP);
        txt_detailgiasp = (TextView) findViewById(R.id.txt_DetailGiaSP);
        txt_detailmotasp = (TextView) findViewById(R.id.txt_DetailMotaSP);
        txt_soluongsp = (TextView) findViewById(R.id.txt_Soluong);
        tongtien = (TextView) findViewById(R.id.Tongtien);
        tensp = (TextView) findViewById(R.id.Tensp);
        btn_tang = (Button) findViewById(R.id.btn_TangSL);
        btn_giam = (Button) findViewById(R.id.btn_GiamSL);
        btn_datmon = (Button) findViewById(R.id.btn_Datmon);
        rb_nho = (RadioButton) findViewById(R.id.rb_Nho);
        rb_vua = (RadioButton) findViewById(R.id.rb_Vua);
        rb_lon = (RadioButton) findViewById(R.id.rb_Lon);
        edt_ghichu = (EditText) findViewById(R.id.edt_Ghichu);
        img_detailhinhsp = (ImageView) findViewById(R.id.img_DetailHinhSP);
    }
}