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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_order, parent, false);

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
            txt_carttensp = (TextView) itemView.findViewById(R.id.txt_CartTensp);
            txt_cartsize = (TextView) itemView.findViewById(R.id.txt_CartSize);
            txt_cartgiasp = (TextView) itemView.findViewById(R.id.txt_CartGiaSP);
            txt_cartsoluong = (TextView) itemView.findViewById(R.id.txt_CartSoluong);
        }
    }
}
