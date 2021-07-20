package com.example.coffeebakery.Receipt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.DetailReceipt.DetailReceiptActivity;
import com.example.coffeebakery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import static com.example.coffeebakery.SplashActivity.uid;
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.Holder>{
    private List mDonhang;
    private Context mContext;
    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();

    public ReceiptAdapter(List donhang, Context context){
        this.mContext = context;
        this.mDonhang = donhang;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_receipt, parent, false);

        return new ReceiptAdapter.Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Receipt re = (Receipt) mDonhang.get(position);
        holder.madon.setText(re.getMadon());
        holder.ngaydat.setText(re.getNgaydat());

        String md = re.getMadon();
        myData.child("Đơn hàng").child("Chi tiết").child(md).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                for(DataSnapshot data : snapshot.getChildren()){
                    String temp_stt = data.child("sttgiohang").getValue().toString();
                    if(temp_stt.contains("1")){
                        String temp_tenmon = data.child("ten").getValue().toString();
                        if (count > 1) {
                            holder.tenmon.setText(temp_tenmon + " + " + (count - 1) + " món khác");
                        }
                        else {
                            holder.tenmon.setText(temp_tenmon);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myData.child("Đơn hàng").child("Thông tin").child(md).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tongtien = 0;
                String temp_tongtien = snapshot.child("tongtien").getValue().toString();
                tongtien += Integer.parseInt(temp_tongtien.replace(".",""));
                if(tongtien >= 1000000){
                    int trieu = tongtien / 1000000;
                    int ngan = tongtien % 1000000;
                    if(ngan >= 1000){
                        int tram = ngan / 1000;
                        holder.dongia.setText(trieu + "." + tram + ".000");
                    }
                }else {
                    if(tongtien >= 1000){
                        int ngan = tongtien / 1000;
                        holder.dongia.setText(ngan + ".000");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String tt = re.getTrangthai();
        switch (tt){
            case "Hoàn thành":
            case "Đang vận đơn":
                holder.trangthai.setText(tt);
                holder.trangthai.setTextColor(Color.GREEN);
                break;
            case "Đang xử lý":
                holder.trangthai.setText(tt);
                holder.trangthai.setTextColor(Color.BLUE);
                break;
            case "Đang chuẩn bị đơn":
                holder.trangthai.setText(tt);
                holder.trangthai.setTextColor(Color.YELLOW);
                break;
            case "Hủy bỏ":
                holder.trangthai.setText(tt);
                holder.trangthai.setTextColor(Color.RED);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailReceiptActivity.class);
                intent.putExtra("MADON",re.getMadon());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonhang.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView madon, ngaydat, tenmon, dongia, trangthai;
        public Holder(@NonNull View itemView) {
            super(itemView);
            madon = (TextView) itemView.findViewById(R.id.txt_IDDonhang);
            ngaydat = (TextView) itemView.findViewById(R.id.txt_Thoigiandat);
            tenmon = (TextView) itemView.findViewById(R.id.txt_TenSpReceipt);
            dongia = (TextView) itemView.findViewById(R.id.txt_GiaSpReceipt);
            trangthai = (TextView) itemView.findViewById(R.id.txt_ReceiptStatus);
        }
    }
}

