package com.example.coffeebakery.Setting;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.HomeActivity;
import com.example.coffeebakery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.coffeebakery.SplashActivity.gmail;
import static com.example.coffeebakery.SplashActivity.uid;

public class ProfileActivity extends AppCompatActivity {

    EditText edthoten, edtsdt, edtsonhaduong;
    TextView txtusername;
    Button btndongy;
    ImageView avatar;
    DatabaseReference mData;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Context context = this.getApplicationContext();
        Anhxa();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        txtusername.setText(gmail);
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Khách hàng").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Profile p = snapshot.getValue(Profile.class);
                    edthoten.setText(p.getHoten());
                    edtsdt.setText(p.getSdt());
                    edtsonhaduong.setText(p.getSonha());
                    txtusername.setText(gmail);
                    Glide.with(context).load(p.getAvatar()).into(avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 22);
            }
        });

        
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = new Profile();
                p.hoten = edthoten.getText().toString().trim();
                p.sdt = edtsdt.getText().toString().trim();
                p.sonha = edtsonhaduong.getText().toString().trim();
                p.gmail = gmail;
                p.userid = uid;

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Xác nhận cập nhật thông tin cá nhân!");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(path != null){
                            StorageReference ref = storageReference.child("Image").child("Profile").child(p.getUID() + ".png");
                            final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
                            progressDialog.setTitle("Đang tải ...");
                            progressDialog.show();
                            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            p.avatar = "" + uri.toString();
                                            mData.child("Khách hàng").child(uid).setValue(p);
                                            mData.child("Sổ địa chỉ").child(uid).child(p.hoten).setValue(p);
                                        }
                                    });
                                    progressDialog.dismiss();

                                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                    Toast.makeText(ProfileActivity.this, "Cập nhật thông tin tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                        }
                                    });
                        }
                    }
                });

                builder.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = uri;
            avatar.setImageURI(uri);
            avatar.setBackgroundColor(Color.WHITE);
        }
    }


    public void Anhxa() {
        edthoten = findViewById(R.id.edt_hoten);
        edtsdt = findViewById(R.id.edt_sdt);
        edtsonhaduong = findViewById(R.id.edt_Diachi);
        avatar = findViewById(R.id.img_Avatar);
        txtusername = findViewById(R.id.txt_username);
        btndongy = findViewById(R.id.btn_Dongy);
    }
}