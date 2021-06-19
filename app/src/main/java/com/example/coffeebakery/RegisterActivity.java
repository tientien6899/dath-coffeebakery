package com.example.coffeebakery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.coffeebakery.LoginActivity.gmail;
import static com.example.coffeebakery.LoginActivity.uid;

public class RegisterActivity extends AppCompatActivity {
    Button btndangky;
    EditText edtusernamedk, edtpassworddk, edtrepeatpass;
    TextView txtdangnhap;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        Anhxa();

        //Không được để trống username
        edtusernamedk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtusernamedk.setError("Không được để trống tên người dùng.");
                } else {
                    edtusernamedk.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Không được để trống Password
        edtpassworddk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtpassworddk.setError("Không được để trống mật khẩu.");
                } else {
                    edtpassworddk.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Không được để trống Nhập lại mật khẩu
        edtrepeatpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtrepeatpass.setError("Không được để trống nhập lại mật khẩu.");
                } else {
                    edtrepeatpass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Sự kiện chuyển trang khi nhấp vào nút Đăng nhập
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Nút Đăng ký
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Strpass = edtpassworddk.getText().toString();
                String Strrepeatpass = edtrepeatpass.getText().toString();
                String Stremail = edtusernamedk.getText().toString() + "@gmail.com";

                if(Stremail.contains("@gmail.com"))
                {
                    if (Strpass.equals(Strrepeatpass)) {
                        Dangky(Stremail, Strpass);
                        Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Vui lòng nhập đúng định dạng Gmail!")
                            .setPositiveButton("Nhập lại", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    edtusernamedk.requestFocus();
                                }
                            }).show();
                }
            }
        });
    }

    //Hàm đăng ký tài khoản bằng username + password
    private void Dangky(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thất bại.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Anhxa() {
        btndangky = (Button) findViewById(R.id.btn_Dangky);
        edtusernamedk = findViewById(R.id.edt_usernameDK);
        edtpassworddk = findViewById(R.id.edt_passwordDK);
        edtrepeatpass = findViewById(R.id.edt_repeatpass);
        txtdangnhap = findViewById(R.id.txt_Dangnhap);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            uid = currentUser.getUid();
        }
    }
}