package com.example.smap_google;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity {
   private static final  String MAP_BUNDLE_KEY ="MapBundlekey";
   private  static  final LatLng DEFAULT_LOCATION = new LatLng(37.56641923090,126.9778741551);
   private  static  final  int DEFAULT_ZOOM = 15;

   private static final long INTERVAL_TIME = 5000;
   private static  final  long FASTEST_INTERVAL_TIME = 2000;

   private GoogleMap map;
   private MapView mapView;
   private  Location lastknowLocation;

   private void getMyLocation(){
       if(ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
       {
           return;
       }
       map.setMyLocationEnabled(true);
       map.getUiSettings().setMyLocationButtonEnabled(true);

       FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);

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
               if(ActivityCompat.checkSelfPermission(LocationActivity.this,
                       Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
               ActivityCompat.checkSelfPermission(LocationActivity.this,
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
       if(ActivityCompat.checkSelfPermission(this,
               Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
       ActivityCompat.checkSelfPermission(this,
               Manifest.permission.ACCESS_COARSE_LOCATION) !=
       PackageManager.PERMISSION_GRANTED) {
           return;
       }
        LocationRequest locationRequest = LocationRequest.create();
       locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       locationRequest.setInterval(INTERVAL_TIME);
       locationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);

       FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);

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


}
