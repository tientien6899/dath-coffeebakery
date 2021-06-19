package com.example.coffeebakery.Home;

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
import com.example.coffeebakery.Product.Product;
import com.example.coffeebakery.R;

import java.util.List;

public class BanChayAdapter extends RecyclerView.Adapter<BanChayAdapter.Holder>{
    private List mProduct;
    private Context context;

    public BanChayAdapter(List mProduct, Context context){
        this.mProduct = mProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public BanChayAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_new_product,parent,false);
        return new BanChayAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BanChayAdapter.Holder holder, int position) {
        Product p = (Product) mProduct.get(position);
        holder.newtensp.setText(p.getTensp());
        int temp_gias = Integer.parseInt(p.getGiaS());
        if(temp_gias >= 1000000){
            temp_gias = temp_gias / 1000000;
            holder.newgias.setText(temp_gias + ".000.000 đ");
        }else if (temp_gias >= 1000){
            temp_gias = temp_gias / 1000;
            holder.newgias.setText(temp_gias + ".000 đ");
        }
        Glide.with(holder.newhinhanh.getContext()).load(p.getLink()).into(holder.newhinhanh);

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
        ImageView newhinhanh;
        TextView newtensp, newgias;
        public Holder(@NonNull View itemView) {
            super(itemView);
            newhinhanh = (ImageView)itemView.findViewById(R.id.img_NewHinhsp);
            newtensp = (TextView)itemView.findViewById(R.id.txt_NewTensp);
            newgias = (TextView)itemView.findViewById(R.id.txt_NewGiaspS);
        }
    }
}
