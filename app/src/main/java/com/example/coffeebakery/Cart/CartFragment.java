package com.example.coffeebakery.Cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.SplashActivity.uid;

public class CartFragment extends Fragment {
    private DatabaseReference myData;
    RecyclerView recyclerView;
    CartAdapter adapter;
    TextView tongsl, tamtinh;
    Button btn_datmon;
    public static int tongSL = 0;
    public static int stt = 0;
    public static int tamtinhdonhang = 0;
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
                tamtinhdonhang = b;
                tongsl.setText(a+"");
                if (b >= 1000000) {
                    tamtinhdonhang = tamtinhdonhang / 1000000;
                    tamtinh.setText(tamtinhdonhang + ".000.000");
                } else if (tamtinhdonhang >= 1000) {
                    tamtinhdonhang = tamtinhdonhang / 1000;
                    tamtinh.setText(tamtinhdonhang + ".000");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_datmon = (Button) v.findViewById(R.id.btn_THDH);
        btn_datmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(),OrderActivity.class);
                v.getContext().startActivity(intent);
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