package com.example.coffeebakery.Setting;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakery.R;

public class DoiMatKhauActivity extends AppCompatActivity {

    EditText mkhientai, mkmoi, xacnhanmatkhaumoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        AnhXa();
    }

    private void AnhXa() {
        mkhientai = (EditText) findViewById(R.id.edt_MKhientai);
        mkmoi = (EditText) findViewById(R.id.edt_MKmoi);
        xacnhanmatkhaumoi = (EditText) findViewById(R.id.edt_xacnhanMKmoi);
    }
}