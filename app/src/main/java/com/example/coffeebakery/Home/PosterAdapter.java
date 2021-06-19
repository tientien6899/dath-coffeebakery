package com.example.coffeebakery.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakery.R;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.Holder> {
    private Context context;
    private List mPoster;

    public PosterAdapter(Context context, List mPoster){
        this.context = context;
        this.mPoster = mPoster;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_poster, parent, false);
        return new PosterAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Poster p = (Poster) mPoster.get(position);
        Glide.with(holder.image.getContext()).load(p.getLink()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPoster.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_Poster);
        }
    }
}
