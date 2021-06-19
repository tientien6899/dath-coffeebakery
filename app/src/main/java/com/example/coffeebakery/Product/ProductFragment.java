package com.example.coffeebakery.Product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.coffeebakery.Home.TapChiAdapter;
import com.example.coffeebakery.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    ArrayList<Product> productArrayList;
    SearchView timkiem;
    private DatabaseReference mData;

    public ProductFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_product, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rcv_dssanpham);
        mData = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productArrayList = new ArrayList<Product>();
        mData.child("SanPham").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product p = snap.getValue(Product.class);
                    productArrayList.add(p);
                }
                adapter = new ProductAdapter(productArrayList,v.getContext());
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(v.getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        timkiem = v.findViewById(R.id.searchViewProduct);
        if(timkiem != null){
            timkiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
        return v;
    }

    private void search(String s){
        ArrayList<Product> list = new ArrayList<>();
        for(Product obj : productArrayList){
            if(obj.getTensp().toLowerCase().contains(s.toLowerCase())){
                list.add(obj);
            }
        }
        ProductAdapter adapter1 = new ProductAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


}