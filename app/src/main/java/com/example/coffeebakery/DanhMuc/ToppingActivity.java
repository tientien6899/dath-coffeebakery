package com.example.coffeebakery.DanhMuc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.coffeebakery.R;

public class ToppingActivity extends AppCompatActivity {

    private RecyclerView danhsach;
    private SearchView timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topping);

        AnhXa();
    }

    private void AnhXa() {
        danhsach = (RecyclerView) findViewById(R.id.rcv_danhsachtopping);
        timkiem = (SearchView) findViewById(R.id.searchViewTopping);
    }
}