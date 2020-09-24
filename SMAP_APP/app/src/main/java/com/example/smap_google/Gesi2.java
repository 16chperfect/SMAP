package com.example.smap_google;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smap_google.model.Snapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Gesi2 extends  AppCompatActivity{



        private RecyclerView recyclerView;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager layoutManager;

        public  static  ArrayList<Snapshot> arrayList2;
        private FirebaseDatabase database;
        private DatabaseReference databaseReference;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gesi);

            arrayList2 = new ArrayList<>();






            database = FirebaseDatabase.getInstance();//파베 데베

            databaseReference = database.getReference("snapshot");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 list 추출

                        Snapshot snapshot1 = snapshot.getValue(Snapshot.class);
                        arrayList2.add(snapshot1);
                    }

                    adapter.notifyDataSetChanged();
                }
                @Override

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //디비를 가져오던중 에러 발생
                    Log.e("Gesi_Activity", String.valueOf(databaseError.toException()));

                }
            });



        }






}
