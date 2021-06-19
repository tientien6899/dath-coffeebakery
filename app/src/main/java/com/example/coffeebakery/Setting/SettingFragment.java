package com.example.coffeebakery.Setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.coffeebakery.LoginActivity;
import com.example.coffeebakery.Profile;
import com.example.coffeebakery.ProfileActivity;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.ReceiptFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.coffeebakery.LoginActivity.gmail;

public class SettingFragment extends Fragment {

    LinearLayout thongtintaikhoan, doimatkhau, sodiachi, thongtinthanhtoan, donhangcuatoi, danhsachyeuthich, vechungtoi, lienhecskh, chinhsachdieukhoan;
    Button dangxuat;
    FirebaseAuth mAuth;
    public SettingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

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
//                Context context = v.getContext();
//                Intent intent = new Intent(context, ReceiptFragment.class);
//                context.startActivity(intent);
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
                                mAuth.signOut();
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