package com.example.coffeebakery.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.LoginActivity.uid;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder>{

    private List mCart;
    private Context mContext;
    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();

    public OrderAdapter(List cart, Context context){
        this.mCart = cart;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_cart, parent, false);

        return new OrderAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Holder holder, int position) {
        Cart ca = (Cart) mCart.get(position);
        holder.txt_carttensp.setText(ca.getTen());
        holder.txt_cartgiasp.setText(ca.getGia());
        holder.txt_cartsize.setText(ca.getKichthuoc());
        holder.txt_cartsoluong.setText(ca.getSoluong());
        Glide.with(holder.img_carthinhsp.getContext()).load(ca.getHinhanh()).into(holder.img_carthinhsp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        holder.btn_cartgiamsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(holder.txt_cartsoluong.getText().toString().trim());
                if(a > 1){
                    holder.btn_cartgiamsl.setEnabled(true);
                    a--;
                    holder.txt_cartsoluong.setText(a + "");
                    String temp_gia = ca.getGia().replace(".","");
                    String tong = (Integer.parseInt(temp_gia) * a) + "";
                    Cart c = new Cart(ca.getSttgiohang(),ca.getGiohang(),ca.getMa(),ca.getTen(),ca.getGia(),a+"",ca.getHinhanh(), tong,ca.getKichthuoc(),ca.getGhichu(),ca.getNguoidung());
                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(ca.getSttgiohang()).setValue(c);
                }
            }
        });

        holder.btn_carttangsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(holder.txt_cartsoluong.getText().toString().trim());
                if(a > 0){
                    a++;
                    holder.btn_cartgiamsl.setEnabled(true);
                    holder.txt_cartsoluong.setText(a + "");
                    String temp_gia = ca.getGia().replace(".","");
                    String tong = (Integer.parseInt(temp_gia) * a) + "";
                    Cart c = new Cart(ca.getSttgiohang(),ca.getGiohang(),ca.getMa(),ca.getTen(),ca.getGia(),a+"",ca.getHinhanh(), tong,ca.getKichthuoc(),ca.getGhichu(),ca.getNguoidung());
                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(ca.getSttgiohang()).setValue(c);
                }
            }
        });

        holder.img_deletecartsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xóa sản phẩm \"" + ca.getTen() + "\" này không?")
                        .setCancelable(true)
                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ref.child("Giỏ hàng").child(uid).child("Cart"+STT).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren()){
                                            String sttgh = data.child("sttgiohang").getValue().toString();
                                            String gh = data.child("giohang").getValue().toString();
                                            if(gh.contains("Cart"+STT)){
                                                if(sttgh.contains(ca.getSttgiohang())){
                                                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(ca.getSttgiohang()).removeValue();
                                                    Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        })
                        .setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_carthinhsp, img_deletecartsp;
        TextView txt_carttensp, txt_cartsize, txt_cartgiasp, txt_cartsoluong;
        Button btn_cartgiamsl, btn_carttangsl;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_carthinhsp = (ImageView) itemView.findViewById(R.id.img_CartHinhsp);
            img_deletecartsp = (ImageView) itemView.findViewById(R.id.img_DeleteCartSP);
            txt_carttensp = (TextView) itemView.findViewById(R.id.txt_CartTensp);
            txt_cartsize = (TextView) itemView.findViewById(R.id.txt_CartSize);
            txt_cartgiasp = (TextView) itemView.findViewById(R.id.txt_CartGiaSP);
            txt_cartsoluong = (TextView) itemView.findViewById(R.id.txt_CartSoluong);
            btn_cartgiamsl = (Button) itemView.findViewById(R.id.btn_CartGiamSL);
            btn_carttangsl = (Button) itemView.findViewById(R.id.btn_CartTangSL);
        }
    }
}
