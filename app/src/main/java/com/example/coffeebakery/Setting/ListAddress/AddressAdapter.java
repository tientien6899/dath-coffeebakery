package com.example.coffeebakery.Setting.ListAddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakery.Cart.OrderActivity;
import com.example.coffeebakery.DetailReceipt.DetailReceiptActivity;
import com.example.coffeebakery.R;
import com.example.coffeebakery.Receipt.ReceiptAdapter;
import com.example.coffeebakery.Receipt.ReceiptsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import static com.example.coffeebakery.LoginActivity.uid;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Holder>{

    private List mAddress;
    private Context mContext;
    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();

    public  AddressAdapter(List address, Context context){
        this.mAddress = address;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_address, parent, false);

        return new AddressAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.Holder holder, int position) {
        Address ad = (Address) mAddress.get(position);
        holder.hoten.setText(ad.getHoten());
        holder.sonha.setText(ad.getSonha());
        holder.sdt.setText(ad.getSdt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                holder.macdinh.setText("Mặc định");
                Intent intent = new Intent(context, OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("HOTEN",holder.hoten.getText().toString());
                bundle.putString("SDT",holder.sdt.getText().toString());
                bundle.putString("SONHA",holder.sonha.getText().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddress.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView hoten, macdinh, sdt, sonha;
        public Holder(@NonNull  View itemView) {
            super(itemView);
            hoten = itemView.findViewById(R.id.txt_hoten);
            macdinh = itemView.findViewById(R.id.txt_macdinh);
            sdt = itemView.findViewById(R.id.txt_sdt);
            sonha = itemView.findViewById(R.id.txt_sonha);
        }
    }
}
