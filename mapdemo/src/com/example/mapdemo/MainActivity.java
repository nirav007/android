package com.example.mapdemo;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends MapActivity {

 private LocationManager myLocationManager;
 private LocationListener myLocationListener;
 private TextView myLongitude, myLatitude;
 
 private MapView myMapView;
 
 private MapController myMapController;

 private void CenterLocatio(GeoPoint centerGeoPoint)
 {
  myMapController.animateTo(centerGeoPoint);
  
 
  myLongitude.setText("Longitude: "+
   String.valueOf((float)centerGeoPoint.getLongitudeE6()/1000000)
   );
  myLatitude.setText("Latitude: "+
   String.valueOf((float)centerGeoPoint.getLatitudeE6()/1000000)
   );
 };

 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  myMapView = (MapView)findViewById(R.id.mapview);
  myLongitude = (TextView)findViewById(R.id.longitude);
  myLatitude = (TextView)findViewById(R.id.latitude);
 // myMapView.setSatellite(true); //Set satellite view
  myMapController = myMapView.getController();
  myMapController.setZoom(10); //Fixed Zoom Level

  myLocationManager = (LocationManager)getSystemService(
    Context.LOCATION_SERVICE);

  
  
  
  myLocationListener = new MyLocationListener();

  myLocationManager.requestLocationUpdates(
    LocationManager.GPS_PROVIDER,
    10,
    1,
    myLocationListener);
  
  /*Get the current location in start-up
  GeoPoint initGeoPoint = new GeoPoint(
   (int)(myLocationManager.getLastKnownLocation(
    LocationManager.NETWORK_PROVIDER)
    .getLatitude()*1000000),
   (int)(myLocationManager.getLastKnownLocation(
    LocationManager.NETWORK_PROVIDER)
    .getLongitude()*1000000));
   CenterLocatio(initGeoPoint);*/ 
 }

 private class MyLocationListener implements LocationListener{

  public void onLocationChanged(Location argLocation) {
   // TODO Auto-generated method stub
   GeoPoint myGeoPoint = new GeoPoint(
    (int)(argLocation.getLatitude()*1000000),
    (int)(argLocation.getLongitude()*1000000));
   
   CenterLocatio(myGeoPoint);
  }

  public void onProviderDisabled(String provider) {
   // TODO Auto-generated method stub
  }

  public void onProviderEnabled(String provider) {
   // TODO Auto-generated method stub
  }

  public void onStatusChanged(String provider,
    int status, Bundle extras) {
   // TODO Auto-generated method stub
  }
 }

 @Override
 protected boolean isRouteDisplayed() {
  // TODO Auto-generated method stub
  return false;
 };
}