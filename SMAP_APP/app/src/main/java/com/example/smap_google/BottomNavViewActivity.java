package com.example.smap_google;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BottomNavViewActivity extends AppCompatActivity{

    private Button btn_mysnap;
    private Button btn_backmain;
    private Button btn_album;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private frag1 frag1;
    private frag2 frag2;
    private frag3 frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        btn_mysnap = (Button)findViewById(R.id.my_snapshot);
        btn_backmain = (Button)findViewById(R.id.my_backmainmenu);
        btn_album = (Button)findViewById(R.id.my_memoryalbum);

        frag1 = new frag1(); // 0
        frag2 = new frag2(); // 1
        frag3 = new frag3(); // 2
        setFrag(1); //첫 프래그먼트 화면

        btn_mysnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrag(0);
            }
        });
        btn_backmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrag(1);
            }
        });
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrag(2);
            }
        });

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) { //버튼을 누름에 따라 바뀌는 정수 N의 값에 맞춰 화면을 출력 (프레그먼트 교체)
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
        }

    }
}
