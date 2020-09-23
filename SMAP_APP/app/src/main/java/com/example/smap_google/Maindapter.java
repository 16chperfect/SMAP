package com.example.smap_google;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smap_google.model.Snapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Maindapter  extends RecyclerView.Adapter<Maindapter.CustomViewHolder> {




    private ArrayList<Snapshot> arrayList;
    private Context context;
    public Maindapter(ArrayList<Snapshot> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPhotoUrl())
               .into(holder.iv_pro1);
        Picasso.get().load((arrayList.get(position).getPhotoUrl())).into(holder.iv_pro1);
        holder.tv_id.setText(arrayList.get(position).getTitle());
        holder.tv_id2.setText(arrayList.get(position).getHashTag());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_pro1;
        TextView tv_id;
        TextView tv_id2;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_pro1 = itemView.findViewById(R.id.iv_pro1);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_id2 = itemView.findViewById(R.id.tv_id2);
        }
    }
}
