package com.example.smap_google;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frag4 extends Fragment {

    private View view;
    private ImageButton btn_album1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4 , container, false);

        //앨범을 누를시 activity_album1로 이동
        btn_album1 = (ImageButton)view.findViewById(R.id.btn_album1);
        btn_album1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), Album1Activity.class );
                startActivity(intent);
            }
        });

        return view;
    }
}