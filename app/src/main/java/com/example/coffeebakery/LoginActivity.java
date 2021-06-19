package com.example.coffeebakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakery.Product.ProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtusernamedn, edtpassworddn;
    Button btndangnhap;
    TextView txtdangky, txtquenmatkhau;

    public static FirebaseAuth mAuth;
    public static String gmail = "";
    public static String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        Anhxa();
        edtusernamedn.requestFocus();

        //Không được để trống tên đăng nhập
        edtusernamedn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtusernamedn.setError("Hãy điền tên đăng nhập.");
                } else {
                    edtusernamedn.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Không được để trống mật khẩu
        edtpassworddn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtpassworddn.setError("Hãy điền mật khẩu.");
                } else {
                    edtpassworddn.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Sự kiện chuyển trang khi nhấp vào nút Đăng ký
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Sự kiện chuyển trang khi nhấp vào nút Quên mật khẩu
        txtquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        //Nút Đăng nhập
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamelogin = edtusernamedn.getText().toString().trim() + "@gmail.com";
                String passwordlogin = edtpassworddn.getText().toString().trim();
                Dangnhap(usernamelogin, passwordlogin);
            }
        });
    }

    //Hàm đăng nhập bằng Email + Password
    private void Dangnhap(final String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            uid = currentUser.getUid();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Chuyển qua Activity GetOTP cho điện thoại

    public void Anhxa(){
        edtusernamedn = findViewById(R.id.edt_usernameDN);
        edtpassworddn = findViewById(R.id.edt_passwordDN);
        btndangnhap = (Button) findViewById(R.id.btn_Dangnhap);
        txtdangky = (TextView) findViewById(R.id.txt_Dangky);
        txtquenmatkhau = (TextView) findViewById(R.id.txt_QuenMK);
    }
}