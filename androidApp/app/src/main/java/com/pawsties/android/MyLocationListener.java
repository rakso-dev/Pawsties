package com.pawsties.android;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationListener implements LocationListener {
    double latitude, longitude, altitude;
    Context context;

    public MyLocationListener(Context baseContext){
        context = baseContext;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        //Toast.makeText (context, "Latitud: " + latitude + "\nLongitud: " + longitude+"\nAltitud: "+altitude, Toast.LENGTH_LONG).show ();

        Geocoder geocoder = new Geocoder (context, Locale.getDefault ());
        List<Address> addresses;
        String city = "";

        try {
            addresses = geocoder.getFromLocation (location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size () > 0) {
                //Log.i ("GEO", addresses.get (0).getLocality ());
                city = addresses.get (0).getLocality();
                Toast.makeText(context, "Perfiles cerca de: "+city, Toast.LENGTH_LONG).show();
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        }

        //Log.i ("GEO", "City: " + city);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}