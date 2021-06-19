package com.example.coffeebakery.Cart;

import android.app.AlertDialog;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakery.DetailProductActivity.STT;
import static com.example.coffeebakery.LoginActivity.uid;


public class CartAdapter extends FirebaseRecyclerAdapter<Cart, CartAdapter.Holder> {
    public CartAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Cart model) {
        holder.txt_carttensp.setText(model.ten);
        holder.txt_cartgiasp.setText(model.gia);
        holder.txt_cartsize.setText(model.kichthuoc);
        holder.txt_cartsoluong.setText(model.getSoluong());

        Glide.with(holder.img_carthinhsp.getContext()).load(model.getHinhanh()).into(holder.img_carthinhsp);

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
                    String tong = (Integer.parseInt(model.getGia()) * a) + "";
                    Cart c = new Cart(model.getSttgiohang(),model.getGiohang(),model.getMa(),model.getTen(),model.getGia(),a+"",model.getHinhanh(), tong,model.getKichthuoc(),model.getGhichu(),model.getNguoidung());
                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(model.getSttgiohang()).setValue(c);
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
                    String tong = (Integer.parseInt(model.getGia()) * a) + "";
                    Cart c = new Cart(model.getSttgiohang(),model.getGiohang(),model.getMa(),model.getTen(),model.getGia(),a+"",model.getHinhanh(), tong,model.getKichthuoc(),model.getGhichu(),model.getNguoidung());
                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(model.getSttgiohang()).setValue(c);
                }
            }
        });

        holder.img_deletecartsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xóa sản phẩm \"" + model.getTen() + "\" này không?")
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
                                                if(sttgh.contains(model.getSttgiohang())){
                                                    ref.child("Giỏ hàng").child(uid).child("Cart"+STT).child(model.getSttgiohang()).removeValue();
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


    @NonNull
    @Override
    public CartAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_cart, parent, false);
        return new CartAdapter.Holder(v);
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView img_carthinhsp, img_deletecartsp;
        TextView txt_carttensp, txt_cartsize, txt_cartgiasp, txt_cartsoluong;
        Button btn_cartgiamsl, btn_carttangsl;

        public Holder(@NonNull final View itemView) {
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
