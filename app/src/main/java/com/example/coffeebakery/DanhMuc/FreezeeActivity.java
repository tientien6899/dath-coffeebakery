package com.example.coffeebakery.DanhMuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.coffeebakery.Product.Product;
import com.example.coffeebakery.Product.ProductAdapter;
import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coffeebakery.HomeActivity.mData;

public class FreezeeActivity extends AppCompatActivity {

    private RecyclerView danhsach;
    private ProductAdapter adapter;
    private ArrayList<Product> productArrayList;
    private SearchView timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freezee);

        AnhXa();

        productArrayList = new ArrayList<Product>();
        mData = FirebaseDatabase.getInstance().getReference();
        danhsach.setLayoutManager(new LinearLayoutManager(this));

        mData.child("Đá xay").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product p = snap.getValue(Product.class);
                    productArrayList.add(p);
                }
                adapter = new ProductAdapter(productArrayList,FreezeeActivity.this);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(FreezeeActivity.this);
                danhsach.setAdapter(adapter);
                danhsach.setLayoutManager(linearLayoutManager1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    }

    private void AnhXa() {
        danhsach = findViewById(R.id.rcv_danhsachdaxay);
        timkiem = findViewById(R.id.searchViewFreezee);
    }

    private void search(String s){
        ArrayList<Product> list = new ArrayList<>();
        for(Product obj : productArrayList){
            if(obj.getTensp().toLowerCase().contains(s.toLowerCase())){
                list.add(obj);
            }
        }
        ProductAdapter adapter1 = new ProductAdapter(list,FreezeeActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FreezeeActivity.this);
        danhsach.setAdapter(adapter1);
        danhsach.setLayoutManager(linearLayoutManager);
    }
}