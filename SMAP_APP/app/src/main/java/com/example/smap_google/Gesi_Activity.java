package com.example.smap_google;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smap_google.model.Snapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;


public class Gesi_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Snapshot> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesi);




        recyclerView = findViewById(R.id.recyclerView);// 아디 연결
        recyclerView.setHasFixedSize(true); //성능 강
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //객체 담음

        database = FirebaseDatabase.getInstance();//파베 데베

        databaseReference = database.getReference("snapshot");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열리스트 존재없게 초 기 화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 list 추출
                    Snapshot snapshot1 = snapshot.getValue(Snapshot.class);
                    arrayList.add(snapshot1);//담은 데이터를 배열리스트에 넣고 리사이클러뷰로 보낼준비

                }
                adapter.notifyDataSetChanged();

            }
            @Override

            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던중 에러 발생
                Log.e("Gesi_Activity", String.valueOf(databaseError.toException()));

            }
        });
        adapter = new Maindapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리싸이클러뷰에 어댑터 연결

    }



}

