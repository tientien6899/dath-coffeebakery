package com.example.coffeebakery.Receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeebakery.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.LoginActivity.gmail;

public class ReceiptFragment extends Fragment {
    RecyclerView recyclerView;
    ReceiptAdapter adapter;
    ArrayList<Receipt> listReceipt;

    public ReceiptFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_receipt, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcv_dsdonhang);
        listReceipt = new ArrayList<Receipt>();

        mData.child("DonHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_nguoidung = data.child("nguoidung").getValue().toString();
                    if(temp_nguoidung.contains(gmail)){
                        String temp_madon = data.child("madon").getValue().toString();
                        String temp_ngaydat = data.child("ngaydat").getValue().toString();
                        String temp_tongtien = data.child("tongtien").getValue().toString();
                        String temp_trangthai = data.child("trangthai").getValue().toString();

                        listReceipt.add(new Receipt(temp_madon,temp_ngaydat,temp_trangthai,temp_tongtien,temp_nguoidung));
                    }
                }
                adapter = new ReceiptAdapter(listReceipt,v.getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}