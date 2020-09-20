package com.example.smap_google;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;

public class MarkerItem {
    double lat;
    double lon;
    String PhotoURL;
    private ImageView imageView;

    public MarkerItem(double lat, double lon, String PhotoURL) {
        this.lat = lat;
        this.lon = lon;
        this.PhotoURL = PhotoURL;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setPhotoURL(String photoURL) {
        this.PhotoURL = photoURL;
    }
    private void setCustomMarker(){
        imageView = (ImageView) imageView.findViewById(R.id.tv_maker);
    }



}

