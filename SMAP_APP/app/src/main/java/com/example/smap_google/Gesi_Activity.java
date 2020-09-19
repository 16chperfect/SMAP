package com.example.smap_google;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Gesi_Activity extends AppCompatActivity {
    private ImageButton uploadButton;
    private ArrayList<gesipan> arrayList;
    private  Maindapter maindapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        setContentView(R.layout.gesipan);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        arrayList = new ArrayList<>();

        maindapter = new Maindapter(arrayList);
        recyclerView.setAdapter(maindapter);

        uploadButton = (ImageButton) findViewById(R.id.btn_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gesipan gesi = new gesipan(R.drawable.ic_launcher_background,".getText().toString()");
                arrayList.add(gesi);
                maindapter.notifyDataSetChanged();
            }
        });


    }


}
