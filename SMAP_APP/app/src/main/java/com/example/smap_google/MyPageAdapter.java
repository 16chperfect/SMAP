package com.example.smap_google;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyPageAdapter extends PagerAdapter {
    List<BannerData> data = new ArrayList<>();
    Context context;
    MyPageAdapter(Context context)
    {
        this.context = context;
    }

    public void add(List<BannerData> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(context).inflate(R.id.imageview1);
        ImageView imageview1=(ImageView) view.findViewById(R.id.imageview1);
        StorageReference storage;
        storage = FirebaseStorage.getInstance().getReference();
        StorageReference banner1 = storage.child(data.get(position).getImagename());
        Glide.with(context.getApplicationContext())
                .using(new FIrebaseImageLoader())
                .load(banner1)
                .into(imageview1);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){

        container.removeView((View) object);
        Toast.makeText(context,"마지막게시물입니다",Toast.LENGTH_SHORT).show();
    }
}
