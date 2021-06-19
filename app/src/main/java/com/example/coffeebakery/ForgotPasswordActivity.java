package com.example.coffeebakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.example.coffeebakery.LoginActivity.mAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button btnxacnhan;
    ImageButton imgbackqmk;
    EditText edtusernameqmk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Anhxa();

        //Sự kiện chuyển trang khi nhấn vào nút mũi tên quay về
        imgbackqmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Nút Xác nhận đặt lại mật khẩu
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(edtusernameqmk.getText());
                mAuth.sendPasswordResetEmail(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ForgotPasswordActivity.this, "Hãy kiểm tra email để đặt lại mật khẩu !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void Anhxa(){
        btnxacnhan = (Button) findViewById(R.id.btn_Xacnhan);
        imgbackqmk = (ImageButton) findViewById(R.id.btnBackQMK);
        edtusernameqmk = findViewById(R.id.edt_usernameQMK);
    }
}