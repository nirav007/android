package com.distance;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Distance_locationActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LocationManager locManager;
        
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, locationListener);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null)                                
        {
            double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        }  

        
        updateWithNewLocation(location);
        
        Location locationA = new Location("point A");  

        String a = "Rajkot";
        locationA.setLatitude(22.0333333333);  
        locationA.setLongitude(70.783333);  

        Location locationB = new Location("point B");  

        locationB.setLatitude(22.0333333333);  
        locationB.setLongitude(70.5833333333);  

        double distance = locationA.distanceTo(locationB);
        
        String d = new Double(distance).toString();
       
        double mile =  0.000621371192237334 * distance;
        
        Toast.makeText(getApplicationContext(), d.toString(), Toast.LENGTH_LONG).show();
        
        String Mile = new Double(mile).toString();
        Toast.makeText(getApplicationContext(), Mile.toString(), Toast.LENGTH_LONG).show();
    }
    
    private void updateWithNewLocation(Location location) {
        TextView myLocationText = (TextView)findViewById(R.id.myLocationText);
        String latLongString = "";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;

            myLocationText.setText("Your Current Position is:\n" +
                    latLongString);

        } else {
            latLongString = "No location found";
        }
         //myLocationText.setText("Your Current Position is:\n" +
         //       latLongString);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


  


}