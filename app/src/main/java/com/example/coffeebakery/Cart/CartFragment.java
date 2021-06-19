package com.example.coffeebakery.Cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.Receipt;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.DetailProductActivity.slmon;
import static com.example.coffeebakery.LoginActivity.gmail;
import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.uid;

public class CartFragment extends Fragment {
    private DatabaseReference myData;
    RecyclerView recyclerView;
    CartAdapter adapter;
    TextView tongsl, tamtinh;
    Button btn_datmon;
    public static int tongSL = 0;
    public static int stt = 0;
    public CartFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_cart, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rcv_dsgiohang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myData =  mData.child("Giỏ hàng").child(uid).child("Cart"+STT);
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(myData, new SnapshotParser<Cart>() {
                            @NonNull
                            @Override
                            public Cart parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Cart(snapshot.child("sttgiohang").getValue().toString(),
                                        snapshot.child("giohang").getValue().toString(),
                                        snapshot.child("ma").getValue().toString(),
                                        snapshot.child("ten").getValue().toString(),
                                        snapshot.child("gia").getValue().toString(),
                                        snapshot.child("soluong").getValue().toString(),
                                        snapshot.child("hinhanh").getValue().toString(),
                                        snapshot.child("tongtien").getValue().toString(),
                                        snapshot.child("kichthuoc").getValue().toString(),
                                        snapshot.child("ghichu").getValue().toString(),
                                        snapshot.child("nguoidung").getValue().toString());
                            }
                        })
                        .build();
        adapter = new CartAdapter(options);
        recyclerView.setAdapter(adapter);

        tongsl = (TextView) v.findViewById(R.id.txt_CartTongSL);
        tamtinh = (TextView) v.findViewById(R.id.txt_Tamtinh);

        mData.child("Giỏ hàng").child(uid).child("Cart"+STT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int a = 0, b = 0;
                String temp_gia = "";
                for(DataSnapshot data : snapshot.getChildren()){
                    String ma = data.child("giohang").getValue().toString();
                    if(ma.contains("Cart"+STT)){
                        String sl = data.child("soluong").getValue().toString();
                        String gia = data.child("tongtien").getValue().toString();
                        a += Integer.parseInt(sl);
                        b += Integer.parseInt(gia);
                    }
                }
                tongsl.setText(a+"");
                tamtinh.setText(b+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_datmon = (Button) v.findViewById(R.id.btn_THDH);
        btn_datmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Tiến hành đặt các món đã chọn!")
                        .setCancelable(true)
                        .setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mData.child("STT").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String string_stt = snapshot.child("stt").getValue().toString();
                                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                                        Calendar calendar = Calendar.getInstance();
                                        String ngay = dateformat.format(calendar.getTime());
                                        String trangthai = "Đang xử lý";
                                        Receipt re = new Receipt(string_stt,ngay,trangthai,tamtinh.getText().toString().trim(),gmail);
                                        //tạo đơn hàng
                                        mData.child("DonHang").child(re.getMadon()).setValue(re);

                                        //tạo giỏ hàng
                                        mData.child("Giỏ hàng").child(uid).child("Cart"+STT).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot data : snapshot.getChildren()){
                                                String gh = data.child("giohang").getValue().toString();
                                                if(gh.contains("Cart"+STT)){
                                                    String string_ghichu = data.child("ghichu").getValue().toString();
                                                    String string_gia = data.child("gia").getValue().toString();
                                                    String string_giohang = string_stt + "";
                                                    String string_hinhanh = data.child("hinhanh").getValue().toString();
                                                    String string_kichthuoc = data.child("kichthuoc").getValue().toString();
                                                    String string_ma = data.child("ma").getValue().toString();
                                                    String string_nguoidung = data.child("nguoidung").getValue().toString();
                                                    String string_soluong = data.child("soluong").getValue().toString();
                                                    String string_sttgiohang = data.child("sttgiohang").getValue().toString();
                                                    String string_ten = data.child("ten").getValue().toString();
                                                    String string_tongtien = data.child("tongtien").getValue().toString();

                                                    Cart c = new Cart(string_sttgiohang,string_giohang,string_ma,
                                                            string_ten,string_gia,string_soluong,string_hinhanh,
                                                            string_tongtien,string_kichthuoc,string_ghichu,string_nguoidung);
                                                    mData.child("GioHang").child(c.getNguoidung()).child(c.getGiohang()).child(c.getSttgiohang()).setValue(c);
                                                }
                                            }
                                            STT = 0;
                                            slmon = 1;
                                            mData.child("Giỏ hàng").child(uid).child("Cart1").removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                        });

                                        int temp = Integer.parseInt(string_stt) + 1;
                                        mData.child("STT").child("stt").setValue(String.valueOf(temp));
                                        recyclerView.removeAllViews();
                                        Toast.makeText(v.getContext(), "Đã tiến hành đặt hàng!", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}