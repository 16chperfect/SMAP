package com.example.smap_google;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BottomNavViewActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigationView;
    private GoogleMap mMap;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private frag1 frag1;
    private frag2 frag2;
    private frag3 frag3;
    private frag4 frag4;
    private frag5 frag5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.act_mainmenu:
                        setFrag(0);
                        break;
                    case R.id.act_search:
                        setFrag(1);
                        break;
                    case R.id.act_challenge:
                        setFrag(2);
                        break;
                    case R.id.act_album:
                        setFrag(3);
                        break;
                    case R.id.act_profile:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        frag1 = new frag1(); // 1
        frag2 = new frag2(); // 0
        frag3 = new frag3(); // 2
        frag4 = new frag4(); // 3
        frag5 = new frag5(); // 4
        setFrag(0); //첫 프래그먼트 화면
    }

    //프래그먼트 화면을 바꾸는 함수

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) { //버튼을 누름에 따라 바뀌는 정수 N의 값에 맞춰 화면을 출력 (프레그먼트 교체)
            case 0:
                ft.replace(R.id.bottom_Nav, frag2);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.bottom_Nav, frag1);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.bottom_Nav, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.bottom_Nav, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.bottom_Nav, frag5);
                ft.commit();
                break;
        }
    }


}
