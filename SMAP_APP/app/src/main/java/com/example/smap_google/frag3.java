package com.example.smap_google;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class frag3 extends Fragment {

    private View view;
    private TextView tv_result; //닉네임 text
    private ImageView IV_PROFILE; // 이미지 뷰

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3 , container, false);


      //  Intent intent = getActivity().getIntent();
      //  if (intent.getStringExtra("nickName") != null) {
       //     String nickName = intent.getStringExtra("nickName"); // MainActivity로 부터 닉네임 전달받음
        //    String phothurl = intent.getStringExtra("phothUrl");  // MainActivity로 부터 프로필 사진 전달받음

           // tv_result = (TextView)view.findViewById(R.id.tv_result);
           // tv_result.setText(nickName); //닉네임 text를 텍스트 뷰에 세팅

          //  IV_PROFILE = (ImageView)view.findViewById(R.id.IV_PROFILE);
          //  Glide.with(this).load(phothurl).into(IV_PROFILE); //프로필 URL을 이미지 뷰에 세팅

       // }
        return view;
    }
}
