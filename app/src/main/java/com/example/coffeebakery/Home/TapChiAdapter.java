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
import com.example.coffeebakery.R;

import java.util.List;

public class TapChiAdapter extends RecyclerView.Adapter<TapChiAdapter.Holder> {
    private List mTapchi;
    private Context context;

    public TapChiAdapter(List mTapchi, Context context){
        this.mTapchi = mTapchi;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tapchi,parent,false);
        return new TapChiAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TapChi tc = (TapChi) mTapchi.get(position);
        holder.tieude.setText(tc.getTieude());
        holder.noidung.setText(tc.getNoidung());
        Glide.with(holder.hinhanh.getContext()).load(tc.getHinhanh()).into(holder.hinhanh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,DetailTapChiActivity.class);
                intent.putExtra("TIEUDE",tc.getTieude());
                intent.putExtra("NOIDUNG",tc.getNoidung());
                intent.putExtra("HINHANH",tc.getHinhanh());
                intent.putExtra("NGAYDANG",tc.getNgaydang());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTapchi.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView hinhanh;
        TextView tieude, noidung;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hinhanh = itemView.findViewById(R.id.tapchi_hinhanh);
            tieude = itemView.findViewById(R.id.tapchi_tieude);
//            noidung = itemView.findViewById(R.id.tapchi_noidung);
        }
    }
}
