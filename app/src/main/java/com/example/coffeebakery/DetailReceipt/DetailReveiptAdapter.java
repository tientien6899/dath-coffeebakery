package com.example.coffeebakery.DetailReceipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.R;

import java.util.List;

public class DetailReveiptAdapter extends RecyclerView.Adapter<DetailReveiptAdapter.Holder> {
    private List mDetail;
    private Context mContext;

    public DetailReveiptAdapter(List detail, Context context){
        this.mDetail = detail;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_detail_receipt, parent, false);
        return new DetailReveiptAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DetailReceipt dr = (DetailReceipt) mDetail.get(position);
        holder.sl.setText(dr.getSl());
        holder.ten.setText(dr.getTen());
        holder.gia.setText(dr.getGia());
        Glide.with(holder.hinh.getContext()).load(dr.getLink()).into(holder.hinh);
    }

    @Override
    public int getItemCount() {
        return mDetail.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView sl, ten, gia;
        ImageView hinh;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hinh = (ImageView) itemView.findViewById(R.id.img_DetailReceipt);
            sl = (TextView) itemView.findViewById(R.id.txt_Detail_Soluong);
            ten = (TextView) itemView.findViewById(R.id.txt_Detail_TenMon);
            gia = (TextView) itemView.findViewById(R.id.txt_Detail_Dongia);
        }
    }
}
