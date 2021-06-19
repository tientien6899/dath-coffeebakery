package com.example.coffeebakery.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.DetailProductActivity;
import com.example.coffeebakery.Home.TapChiAdapter;
import com.example.coffeebakery.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>{

    private List mProduct;
    private Context context;

    public ProductAdapter(List mProduct, Context context){
        this.mProduct = mProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_product,parent,false);
        return new ProductAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product p = (Product) mProduct.get(position);
        holder.tensp.setText(p.getTensp());
        int temp_gias = Integer.parseInt(p.getGiaS());
        if(temp_gias >= 1000000){
            temp_gias = temp_gias / 1000000;
            holder.gias.setText(temp_gias + ".000.000 ");
        }else if (temp_gias >= 1000){
            temp_gias = temp_gias / 1000;
            holder.gias.setText(temp_gias + ".000 ");
        }
        holder.mota.setText(p.getMota());
        Glide.with(holder.hinhanh.getContext()).load(p.getLink()).into(holder.hinhanh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("DANHMUC",p.getDanhmuc());
                intent.putExtra("TENSP",p.getTensp());
                intent.putExtra("MASP",p.getMasp());
                intent.putExtra("GIAS",p.getGiaS());
                intent.putExtra("GIAM",p.getGiaM());
                intent.putExtra("GIAL",p.getGiaL());
                intent.putExtra("GIAKM",p.getGiaKM());
                intent.putExtra("LINK",p.getLink());
                intent.putExtra("MOTA",p.getMota());
                intent.putExtra("MUA",p.getLuotMua());
                intent.putExtra("THICH",p.getLuotYeuThich());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView hinhanh;
        TextView tensp, gias, mota;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hinhanh = (ImageView)itemView.findViewById(R.id.img_Hinhsp);
            tensp = (TextView)itemView.findViewById(R.id.txt_Tensp);
            gias = (TextView)itemView.findViewById(R.id.txt_GiaspS);
            mota = (TextView)itemView.findViewById(R.id.txt_Mota);
        }
    }
}
