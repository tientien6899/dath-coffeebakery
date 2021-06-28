package com.example.coffeebakery.Setting.ListAddress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        myData.child("Sổ địa chỉ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        String userid = data.child("uid").getValue().toString();
                        if(userid.contains(uid)){
                            holder.hoten.setText(data.child("hoten").getValue().toString());
                            holder.sdt.setText(data.child("sdt").getValue().toString());
                            holder.sonha.setText(data.child("sonha").getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.macdinh.setText("Mặc định");
                Intent intent = new Intent(view.getContext(), ReceiptsActivity.class);
                intent.putExtra("HOTEN",holder.hoten.getText());
                intent.putExtra("SDT",holder.sdt.getText());
                intent.putExtra("SONHA",holder.sonha.getText());
                mContext.startActivity(intent);
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
