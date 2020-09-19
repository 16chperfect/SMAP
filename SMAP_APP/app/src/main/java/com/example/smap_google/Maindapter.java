package com.example.smap_google;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Maindapter extends RecyclerView.Adapter<Maindapter.CustomViewHolder> {

    private ArrayList<gesipan> arrayList;

    public Maindapter(ArrayList<gesipan> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Maindapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Maindapter.CustomViewHolder holder, int position) {
        holder.iv_pro.setImageResource(arrayList.get(position).getIv_pro());
        holder.tv_name.setText(arrayList.get(position).getTv_name());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String curName = holder.tv_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position)
    {
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_pro;
        protected TextView tv_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_pro = (ImageView) itemView.findViewById(R.id.iv_pro);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
