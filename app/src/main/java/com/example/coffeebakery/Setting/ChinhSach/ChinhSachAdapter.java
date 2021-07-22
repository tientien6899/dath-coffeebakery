package com.example.coffeebakery.Setting.ChinhSach;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakery.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChinhSachAdapter extends RecyclerView.Adapter<ChinhSachAdapter.Holder>{

    private List mChinhSach;
    private Context context;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    public ChinhSachAdapter(List mchinhSach, Context context){
        this.mChinhSach = mchinhSach;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_chinh_sach, parent, false);
        return new ChinhSachAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChinhSachAdapter.Holder holder, int position) {
        ChinhSach cs = (ChinhSach) mChinhSach.get(position);
        holder.tieude.setText(cs.getTieude());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, CTDSActivity.class);
                intent.putExtra("TIEUDE",cs.getTieude());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChinhSach.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tieude;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tieude = itemView.findViewById(R.id.book_tieude);
        }
    }
}
