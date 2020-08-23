package com.example.smap_google;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultActivity_google extends AppCompatActivity {

    private TextView tv_result; //닉네임 text
    private ImageView IV_PROFILE; // 이미지 뷰


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_google);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName"); // MainActivity로 부터 닉네임 전달받음
        String phothurl = intent.getStringExtra("phothUrl");  // MainActivity로 부터 프로필 사진 전달받음

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(nickName); //닉네임 text를 텍스트 뷰에 세팅

        IV_PROFILE = findViewById(R.id.IV_PROFILE);
        Glide.with(this).load(phothurl).into(IV_PROFILE); //프로필 URL을 이미지 뷰에 세팅

    }
}