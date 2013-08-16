package com.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class Location_findActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LocationManager locationManager;
        locationManager = (LocationManager)getSystemService
        (Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation
        (LocationManager.GPS_PROVIDER);


        locationManager.requestLocationUpdates(                
                 LocationManager.GPS_PROVIDER,0,0,(LocationListener) this);
        updateWithNewLocation(location);
        }

        private void updateWithNewLocation(Location location) {
        TextView myLocationText = (TextView)findViewById(R.id.myLocationText);

        String latLongString;

        if (location != null) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        latLongString = "Lat:" + lat + "\nLong:" + lng;
        } else {
        latLongString = "No location found";
        }

        myLocationText.setText("Your Current Position is:\n" +
        latLongString);
        }
}