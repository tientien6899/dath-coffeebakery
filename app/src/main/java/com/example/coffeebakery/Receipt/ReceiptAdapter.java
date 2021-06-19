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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.coffeebakery.LoginActivity.gmail;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.Holder>{
    private List mDonhang;
    private Context mContext;
    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();

    public ReceiptAdapter(List donhang, Context context){
        this.mDonhang = donhang;
        this.mContext = context;
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
        myData.child("GioHang").child(gmail).child(md).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stt = "";
                for(DataSnapshot data : snapshot.getChildren()){
                    String gh = data.child("giohang").getValue().toString();
                    if(gh.contains(md)){
                        String sttgh = data.child("sttgiohang").getValue().toString();
                        if(sttgh.contains("1")){
                            holder.tenmon.setText(data.child("ten").getValue().toString());
                            holder.dongia.setText(data.child("gia").getValue().toString());
                            Glide.with(holder.hinhanh.getContext()).load(data.child("hinhanh").getValue().toString()).into(holder.hinhanh);
                        }
                    }
                    stt = data.child("sttgiohang").getValue().toString();
                }
                holder.xemthem.setText("Xem thêm " + "(" + stt + " sản phẩm)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        String tt = re.getTrangthai();
        if(tt.contains("Hoàn thành")){
            holder.trangthai.setText(tt);
            holder.trangthai.setTextColor(Color.GREEN);
        } else if(tt.contains("Đang xử lý")){
            holder.trangthai.setText(tt + "...");
            holder.trangthai.setTextColor(Color.BLUE);
        } else {
            holder.trangthai.setText(tt);
            holder.trangthai.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailReceiptActivity.class);
                intent.putExtra("MADON",re.getMadon());
                intent.putExtra("MAGIOHANG",re.getMadon());
                intent.putExtra("TONGTIEN",re.getTongtien());
                intent.putExtra("NGAYDAT",re.getNgaydat());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonhang.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView madon, ngaydat, tenmon, dongia, xemthem, trangthai;
        Button datlai;
        ImageView hinhanh;
        public Holder(@NonNull View itemView) {
            super(itemView);
            madon = (TextView) itemView.findViewById(R.id.txt_IDDonhang);
            ngaydat = (TextView) itemView.findViewById(R.id.txt_Thoigiandat);
            tenmon = (TextView) itemView.findViewById(R.id.txt_TenSpReceipt);
            dongia = (TextView) itemView.findViewById(R.id.txt_GiaSpReceipt);
            xemthem = (TextView) itemView.findViewById(R.id.txt_XemThem);
            trangthai = (TextView) itemView.findViewById(R.id.txt_ReceiptStatus);
            datlai = (Button) itemView.findViewById(R.id.btn_DatLai);
            hinhanh = (ImageView) itemView.findViewById(R.id.img_HinhReceipt);
        }
    }
}


//public class ReceiptAdapter extends FirebaseRecyclerAdapter<Receipt,ReceiptAdapter.Holder> {
//    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
//
//    public ReceiptAdapter(@NonNull FirebaseRecyclerOptions<Receipt> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Receipt model) {
//        holder.madon.setText(model.getMadon());
//        holder.ngaydat.setText(model.getNgaydat());
//        holder.dongia.setText(model.getTongtien());
////        myData.child("Taikhoan").child(gmail).child("Giohang").child(model.getMadon()).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                String stt = "";
////                for(DataSnapshot data : snapshot.getChildren()){
////                    String gh = data.child("giohang").getValue().toString();
////                    if(gh.contains(model.getMadon())){
////                        String sttgh = data.child("sttgiohang").getValue().toString();
////                        if(sttgh.contains("1")){
////                            String mon = data.child("ten").getValue().toString();
////                            String link = data.child("hinhanh").getValue().toString();
////                            holder.tenmon.setText(mon);
////                            Glide.with(holder.hinhanh.getContext()).load(link).into(holder.hinhanh);
////                        }
////                    }
////                    stt = data.child("sttgiohang").getValue().toString();
////                }
////                holder.xemthem.setText("Xem thêm " + "(" + stt + " sản phẩm)");
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//
//        String tt = model.getTrangthai();
//        if(tt.contains("Hoàn thành")){
//            holder.trangthai.setText(tt);
//            holder.trangthai.setTextColor(Color.BLUE);
//        } else if(tt.contains("Đang xử lý")){
//            holder.trangthai.setText(tt + "...");
//            holder.trangthai.setTextColor(Color.RED);
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailReceiptActivity.class);
//                intent.putExtra("MADON",model.getMadon());
//                intent.putExtra("MAGIOHANG",model.getMadon());
//                intent.putExtra("TONGTIEN",model.getTongtien());
//                intent.putExtra("NGAYDAT",model.getNgaydat());
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_receipt, parent, false);
//        return new ReceiptAdapter.Holder(v);
//    }
//
//    public class Holder extends RecyclerView.ViewHolder {
//        TextView madon, ngaydat, tenmon, dongia, xemthem, trangthai;
//        Button datlai;
//        ImageView hinhanh;
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            madon = (TextView) itemView.findViewById(R.id.txt_IDDonhang);
//            ngaydat = (TextView) itemView.findViewById(R.id.txt_Thoigiandat);
//            tenmon = (TextView) itemView.findViewById(R.id.txt_TenSpReceipt);
//            dongia = (TextView) itemView.findViewById(R.id.txt_GiaSpReceipt);
//            xemthem = (TextView) itemView.findViewById(R.id.txt_XemThem);
//            trangthai = (TextView) itemView.findViewById(R.id.txt_ReceiptStatus);
//            datlai = (Button) itemView.findViewById(R.id.btn_DatLai);
//            hinhanh = (ImageView) itemView.findViewById(R.id.img_HinhReceipt);
//        }
//    }
//}
