package com.pittchallenge.buylite.models;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class OrderLatLng {
    private double latitude;
    private double longitude;

    public OrderLatLng(){

    }

    public OrderLatLng(double lat, double lng){
        latitude = lat;
        longitude = lng;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString(){
        return latitude + "," + longitude;
    }
}
