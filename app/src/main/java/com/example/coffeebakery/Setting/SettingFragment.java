package com.example.coffeebakery.Setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coffeebakery.CSKHActivity;
import com.example.coffeebakery.DevelopingActivity;
import com.example.coffeebakery.LoginActivity;
import com.example.coffeebakery.ProfileActivity;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.ReceiptsActivity;
import com.example.coffeebakery.Setting.ListAddress.ListAddressActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import static com.example.coffeebakery.HomeActivity.mData;
import static com.example.coffeebakery.SplashActivity.gmail;
import static com.example.coffeebakery.SplashActivity.uid;

public class SettingFragment extends Fragment {

    LinearLayout thongtintaikhoan, doimatkhau, sodiachi, thongtinthanhtoan, donhangcuatoi, danhsachyeuthich, vechungtoi, lienhecskh, chinhsachdieukhoan;
    Button dangxuat;
    FirebaseAuth mAuth;
    TextView hotenuser, mail;
    public SettingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        hotenuser = v.findViewById(R.id.txt_Hoten);
        mail = v.findViewById(R.id.txt_email);

        mData.child("Khách hàng").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    hotenuser.setText(snapshot.child("hoten").getValue().toString());
                    mail.setText(snapshot.child("gmail").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        thongtintaikhoan = v.findViewById(R.id.Setting_Thongtintk);
        thongtintaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        doimatkhau = v.findViewById(R.id.Setting_Doimatkhau);
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DoiMatKhauActivity.class);
                context.startActivity(intent);
            }
        });

        donhangcuatoi = v.findViewById(R.id.Setting_Donhangcuatoi);
        donhangcuatoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ReceiptsActivity.class);
                context.startActivity(intent);
            }
        });

        sodiachi = v.findViewById(R.id.Setting_Sodiachi);
        sodiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ListAddressActivity.class);
                context.startActivity(intent);
            }
        });

        vechungtoi = v.findViewById(R.id.Setting_Vechungtoi);
        vechungtoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AboutUsActivity.class);
                context.startActivity(intent);
            }
        });

        lienhecskh = v.findViewById(R.id.Setting_Lienhecskh);
        lienhecskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CSKHActivity.class);
                context.startActivity(intent);
            }
        });

        thongtinthanhtoan = v.findViewById(R.id.Setting_Thongtinthanhtoan);
        thongtinthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DevelopingActivity.class);
                context.startActivity(intent);
            }
        });

        danhsachyeuthich = v.findViewById(R.id.Setting_Dsyeuthich);
        danhsachyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DevelopingActivity.class);
                context.startActivity(intent);
            }
        });


        dangxuat = v.findViewById(R.id.btn_Dangxuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có chắc là muốn đăng xuất không?");
                builder.setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(v.getContext(), "Hẹn gặp lại bạn nhé!", Toast.LENGTH_SHORT).show();
                                mData.child("Ghi nhớ đăng nhập").child(uid).removeValue();
                                uid = "";
                                gmail = "";
                                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                                startActivity(intent);
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

        return v;
    }
}