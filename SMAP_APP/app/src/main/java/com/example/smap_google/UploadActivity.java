package com.example.smap_google;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class UploadActivity extends AppCompatActivity implements OnMapReadyCallback {
    final Snapshot snapshot = new Snapshot();
    private ImageButton uploadButton;
    private EditText edt_title;
    private EditText edt_contents;
    private EditText edt_hashtag;
    private Button btn_upload;
    private ImageButton btn_back;
    private int emotion_value;
    private int sky_value;
    private MapView mapView;
    public GoogleMap map;
    public static double lat;
    public static double lng;



    BannerData banner = new BannerData();
    ImageView button1;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int REQUEST_CODE = 0;
    ImageView imageView1;
    EditText edittext1;
    Uri imagefile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mapView = (MapView) findViewById(R.id.upload_map);
        mapView.getMapAsync(this);
//re

        //



        //뒤로가기를 누를 시 BottomNavViewActivity로 돌아감
        btn_back = (ImageButton) findViewById(R.id.btn_back1);
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
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_contents = (EditText) findViewById(R.id.edt_contents);
        edt_hashtag = (EditText) findViewById(R.id.edt_hashtag);

        button1 = (ImageView) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        //recyclerView


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    filePath = data.getData();
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    button1.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onEmotionButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
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
        switch (view.getId()) {
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
        map = googleMap;

    }

    private  static  final LatLng DEFAULT_LOCATION = new LatLng(37.56641923090,126.9778741551);
    private  static  final  int DEFAULT_ZOOM = 15;

    private static final long INTERVAL_TIME = 500000000;
    private static  final  long FASTEST_INTERVAL_TIME = 500000000;

    private Location lastknowLocation;




    public void uploadsnapshot() {

        snapshot.setId(UUID.randomUUID().toString());
        snapshot.setTitle(edt_title.getText().toString());
        snapshot.setContents(edt_contents.getText().toString());
        snapshot.setHashTag(edt_hashtag.getText().toString());
        snapshot.setWeather(sky_value);
        snapshot.setEmotion(emotion_value);

        //frag.getMyLocation();
        snapshot.setLng(lng);
        snapshot.setLat(lat);

        //snapshot.setLat();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("snapshot");


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseStorage storage;


        StorageReference ref
                = storageReference
                .child(
                        "images/"
                                + UUID.randomUUID().toString());

        // adding listeners on upload
        // or failure of image
        ref.putFile(filePath)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(
                                    UploadTask.TaskSnapshot taskSnapshot) {

                                // Image uploaded successfully
                                // Dismiss dialog
                                String downloadUri = taskSnapshot.getMetadata().getPath();
                                snapshot.setPhotoUrl(downloadUri);
                                myRef.child(snapshot.getId()).setValue(snapshot);

                                Toast
                                        .makeText(UploadActivity.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // Error, Image not uploaded

                        Toast
                                .makeText(UploadActivity.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });

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



}

