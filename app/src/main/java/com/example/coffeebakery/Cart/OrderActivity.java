package com.example.coffeebakery.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.Product.Product;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.Receipt;
import com.example.coffeebakery.Receipt.ReceiptsActivity;
import com.example.coffeebakery.Setting.ListAddress.Address;
import com.example.coffeebakery.Setting.ListAddress.AddressAdapter;
import com.example.coffeebakery.Setting.ListAddress.ListAddressActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.coffeebakery.Cart.CartFragment.tamtinhdonhang;
import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.DetailProductActivity.slmon;
import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.SplashActivity.uid;

public class OrderActivity extends AppCompatActivity {
    RecyclerView donhang;
    OrderAdapter adapter;
    ArrayList<Cart> listCart;
    TextView thaydoi, hoten, sdt, sonha;
    public static int tongSL = 0;
    public static int stt = 0;
    TextView tongsl, tamtinh, phigiaohang, tongthanhtien;
    Button xacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Context context = this.getApplicationContext();
        listCart = new ArrayList<Cart>();
        AnhXa();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            hoten.clearComposingText();
            sdt.clearComposingText();
            sonha.clearComposingText();
            String str_hoten = bundle.getString("HOTEN");
            String str_sdt = bundle.getString("SDT");
            String str_sonha = bundle.getString("SONHA");
            hoten.setText(str_hoten);
            sdt.setText(str_sdt);
            sonha.setText(str_sonha);
        }else {
            mData.child("Kh??ch h??ng").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        String userid = snap.child("uid").getValue().toString();
                        if(userid.contains(uid)){
                            String temp_hoten = snap.child("hoten").getValue().toString();
                            String temp_sdt = snap.child("sdt").getValue().toString();
                            String temp_sonha = snap.child("sonha").getValue().toString();
                            hoten.setText(temp_hoten);
                            sdt.setText(temp_sdt);
                            sonha.setText(temp_sonha);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, ListAddressActivity.class);
                startActivity(intent);
            }
        });


        mData.child("Gi??? h??ng").child(uid).child("Cart"+STT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_gh = data.child("giohang").getValue().toString();
                    if(temp_gh.contains("Cart"+STT)){
                        Cart ca = data.getValue(Cart.class);
                        listCart.add(ca);
                    }
                }
                adapter = new OrderAdapter(listCart,context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                donhang.setAdapter(adapter);
                donhang.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mData.child("Gi??? h??ng").child(uid).child("Cart"+STT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int a = 0, b = 0;
                for(DataSnapshot data : snapshot.getChildren()){
                    String ma = data.child("giohang").getValue().toString();
                    if(ma.contains("Cart"+STT)){
                        String sl = data.child("soluong").getValue().toString();
                        String gia = data.child("tongtien").getValue().toString();
                        String temp_gia = gia.replace(".","");
                        a += Integer.parseInt(sl);
                        b += Integer.parseInt(temp_gia);

                    }
                }
                tongsl.setText(a+"");
                if (b >= 1000000) {
                    b = b / 1000000;
                    tamtinh.setText(b + ".000.000");
                } else if (b >= 1000) {
                    b = b / 1000;
                    tamtinh.setText(b + ".000");
                }
                String temp_giaohang = phigiaohang.getText().toString();
                int temp_phigiaohang = Integer.parseInt(temp_giaohang.replace(".",""));

                String temp_tamtinh = tamtinh.getText().toString();
                int temp_tongtamtinh = Integer.parseInt(temp_tamtinh.replace(".",""));
                int total = temp_tongtamtinh + temp_phigiaohang;
                if (total >= 1000000) {
                    total = temp_tongtamtinh / 1000000;
                    tamtinh.setText(total + ".000.000 ");
                } else if (total >= 1000) {
                    total = total / 1000;
                    tongthanhtien.setText(total + ".000 ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        xacnhan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Ti???n h??nh ?????t c??c m??n ???? ch???n!")
                    .setCancelable(true)
                    .setNegativeButton("X??c nh???n", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mData.child("????n h??ng").child("Th??ng tin").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String string_stt = "Cart"+STT;
                                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                                    Calendar calendar = Calendar.getInstance();
                                    String ngay = dateformat.format(calendar.getTime());
                                    String trangthai = "??ang x??? l??";

                                    Receipt re = new Receipt(string_stt,ngay,trangthai,tongthanhtien.getText().toString().trim(),
                                            uid,hoten.getText().toString(),sdt.getText().toString(),sonha.getText().toString(),
                                            phigiaohang.getText().toString(), tamtinh.getText().toString(), "");

                                    tamtinhdonhang = 0;
                                    //t???o ????n h??ng
                                    mData.child("????n h??ng").child("Th??ng tin").child(re.getMadon()).setValue(re);

                                    //t???o gi??? h??ng
                                    mData.child("Gi??? h??ng").child(uid).child("Cart"+STT).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren()){
                                            String gh = data.child("giohang").getValue().toString();
                                            if(gh.contains("Cart"+STT)){
                                                Cart ca = data.getValue(Cart.class);

                                                mData.child("S???n Ph???m").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot snap : snapshot.getChildren()){
                                                            Product pro = snap.getValue(Product.class);
                                                            if(pro.getMasp().contains(ca.getMa())){
                                                                int temp_lmua = pro.getLuotMua();
                                                                pro.setLuotMua(temp_lmua + Integer.parseInt(ca.getSoluong()));
                                                                mData.child("S???n Ph???m").child(pro.getMasp()).setValue(pro);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                mData.child("????n h??ng").child("Chi ti???t").child(ca.getGiohang()).child(ca.getSttgiohang()).setValue(ca);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                    });
                                    mData.child("Gi??? h??ng").child(uid).child("Cart"+STT).removeValue();
                                    slmon = 1;
                                    mData.child("L?????t Order").child("STT").setValue(STT+1);
                                    donhang.removeAllViews();
                                    Intent it = new Intent(OrderActivity.this, ReceiptsActivity.class);
                                    startActivity(it);
                                    Toast.makeText(v.getContext(), "???? ti???n h??nh ?????t h??ng!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    })
                    .setPositiveButton("Quay l???i", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        }
    });
    }

    private void AnhXa() {
        donhang = findViewById(R.id.rcv_danhsachdonhang);
        thaydoi = findViewById(R.id.txt_thaydoi);
        hoten = findViewById(R.id.txt_hoten_donhang);
        sdt = findViewById(R.id.txt_sdt_donhang);
        sonha = findViewById(R.id.txt_sonha_donhang);
        tongsl = (TextView) findViewById(R.id.txt_slspdonhang);
        tamtinh = (TextView) findViewById(R.id.txt_tamtinhdonhang);
        tongthanhtien = findViewById(R.id.txt_tongdonhang);
        phigiaohang = findViewById(R.id.txxt_phigiaohang);
        xacnhan = findViewById(R.id.btn_xacnhandonhang);
    }
}