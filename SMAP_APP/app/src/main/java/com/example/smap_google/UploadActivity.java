package com.example.smap_google;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.smap_google.model.Snapshot;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class UploadActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageButton uploadButton;
    private EditText edt_title;
    private EditText edt_contents;
    private EditText edt_hashtag;
    private Button btn_upload;
    private ImageButton btn_back;
    private int emotion_value;
    private int sky_value;
    private MapView mapView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mapView = (MapView) findViewById(R.id.upload_map);
        mapView.getMapAsync(this);

        //뒤로가기를 누를 시 BottomNavViewActivity로 돌아감
        btn_back = (ImageButton)findViewById(R.id.btn_back1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this, BottomNavViewActivity.class);
                startActivity(intent);
            }
        });

        uploadButton = (ImageButton) findViewById(R.id.btn_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadsnapshot();
            }
        });
        edt_title =(EditText) findViewById(R.id.edt_title);
        edt_contents =(EditText) findViewById(R.id.edt_contents);
        edt_hashtag =(EditText) findViewById(R.id.edt_hashtag);
    }

    public void onEmotionButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_good:
                if (checked)
                    emotion_value = 0;
                    break;
            case R.id.radio_bad:
                if (checked)
                    emotion_value = 1;
                    break;
        }
    }
    public void onSkyButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sunny:
                if (checked)
                    sky_value = 0;
                    break;
            case R.id.radio_rain:
                if (checked)
                    sky_value = 1;
                    break;
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

//        map = googleMap;
//
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        map.addMarker(markerOptions);
//
//        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        map = googleMap;
        getMyLocation();

    }

    private  static  final LatLng DEFAULT_LOCATION = new LatLng(37.56641923090,126.9778741551);
    private  static  final  int DEFAULT_ZOOM = 15;

    private static final long INTERVAL_TIME = 5000;
    private static  final  long FASTEST_INTERVAL_TIME = 2000;

    private Location lastknowLocation;

    private void getMyLocation(){
        Activity activity = UploadActivity.this;

        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    200);
        }
        if(ActivityCompat.checkSelfPermission(activity , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(activity);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location) {
                lastknowLocation = location;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastknowLocation.getLatitude(),
                                lastknowLocation.getLongitude()),
                        DEFAULT_ZOOM
                ));
                updateMyLocation();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(ActivityCompat.checkSelfPermission(UploadActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(UploadActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION,
                        DEFAULT_ZOOM));
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        });
    }

    private void updateMyLocation() {
        if(ActivityCompat.checkSelfPermission(UploadActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(UploadActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_TIME);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);

        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(UploadActivity.this);

        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location = locationResult.getLastLocation();

                        map.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(location.getLatitude(), location.getLongitude())));
                    }

                },null);

    }
    public void uploadsnapshot()
    {
        Snapshot snapshot = new Snapshot();
        snapshot.setId(UUID.randomUUID().toString());
        snapshot.setTitle(edt_title.getText().toString());
        snapshot.setContents(edt_contents.getText().toString());
        snapshot.setHashTag(edt_hashtag.getText().toString());
        snapshot.setWeather(sky_value);
        snapshot.setEmotion(emotion_value);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("snapshot");
        myRef.child(snapshot.getId()).setValue(snapshot);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Intent intent = new Intent(UploadActivity.this, BottomNavViewActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("test", "Failed to read value.", error.toException());
            }
        });
    }
    public void clickUploadbutton(com.google.firebase.database.core.view.View v)
    {
        StorageReference storage;
        storage = FirebaseStorage.getInstance().getReference();
        SimpleDateFormat dateform = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        final String imagedata = "image/"+date.toString()+".png";
        StorageReference data = storage.child(imagedata);

        data.putFile(imagefile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                BannerData board = new BannerData(imagedata,edittext1.getText().toString());
                database.child("imagedata").push().setValue(board);
                edittext1.setText("");

                Toast.makeText(getApplicationContext(),"전송이 완료 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"전송이 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });

    }
}