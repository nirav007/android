package com.example.fpm;

import org.json.JSONArray;




import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class symbol extends Activity  {
	
	private ImageView imgTicket;
	private ImageView imgProperty;
	private ImageView imgMessage;
	private ImageView imgProfile;
	private TextView txtTicket;
	private TextView txtProperty;
	private TextView txtMessage;
	private TextView txtProfile;
	
	JSONArray locationresults = null;
	
	Location location;
    LocationManager locManager ;
    
    double longitude = 0;
	double latitude = 0;
	
	function c = new function();
	
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.symbol);
		
		imgTicket = (ImageView) findViewById(R.id.imgTicket);
		imgProperty = (ImageView) findViewById(R.id.imgProperty);
		imgMessage = (ImageView) findViewById(R.id.imgMessage);
		imgProfile = (ImageView) findViewById(R.id.imgProfile);
		
		 Typeface tf = Typeface.createFromAsset(
					getBaseContext().getAssets(), "fonts/GOTHIC.TTF");
		 
		
		txtTicket = (TextView) findViewById(R.id.txtTicket);
		txtProperty = (TextView) findViewById(R.id.txtProperty);
		txtMessage = (TextView) findViewById(R.id.txtMessage);
		txtProfile = (TextView) findViewById(R.id.txtProfile);
		
		txtTicket.setTypeface(tf);
		txtProperty.setTypeface(tf);
		txtMessage.setTypeface(tf);
		txtProfile.setTypeface(tf);
		
		boolean isonline = c.haveNetworkConnection(getApplicationContext());
		if(isonline==true)
		{
			
			 gps = new GPSTracker(symbol.this);
			
			   if(gps.canGetLocation()){
		        	
		        	double latitude = gps.getLatitude();
		        	double longitude = gps.getLongitude();
		        	
		        	
		        	SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("lat", String.valueOf(latitude) );
					editor.putString("lng", String.valueOf(longitude) );
					
					editor.commit();
		        	// \n is for new line
		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
		}
		
		imgTicket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(symbol.this,tab.class);
				startActivity(intent);
				
			}
		});
		
		imgProperty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(symbol.this,listproperty.class);
				startActivity(intent);
				
			}
		});
		
		imgMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(symbol.this,listmessage.class);
				startActivity(intent);
				
			}
		});
		
		imgProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(symbol.this,profile.class);
				startActivity(intent);
				
			}
		});
		
		imgTicket.setOnTouchListener(new OnTouchListener() {
           
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				 
				switch (arg1.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                    imgTicket.setImageResource(R.drawable.hticket);
	                	boolean isonline = c.haveNetworkConnection(getApplicationContext());
	            		if(isonline==true)
	            		{
	            			Intent intent = new Intent(symbol.this,tab.class);
		    				startActivity(intent);
	            		}
	            		else
	            		{
	            			Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
	            		}

	                    
	                    break;
	                }
	                case MotionEvent.ACTION_UP: {
	                    imgTicket.setImageResource(R.drawable.ticket);
	                    break;
	                }
	                case MotionEvent.ACTION_CANCEL:{
	                	imgTicket.setImageResource(R.drawable.ticket);
	                    break;
	                }
	                }
	                return true;
				// TODO Auto-generated method stub
				
			}
        });
		
		imgProperty.setOnTouchListener(new OnTouchListener() {
	           
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				 
				switch (arg1.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                	imgProperty.setImageResource(R.drawable.hproperty);
	                	
	                	boolean isonline = c.haveNetworkConnection(getApplicationContext());
	            		if(isonline==true)
	            		{
	            			Intent intent = new Intent(symbol.this,listproperty.class);
		    				startActivity(intent);
	            		}
	            		else
	            		{
	            			Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
	            		}
	                	
	                    break;
	                }
	                case MotionEvent.ACTION_UP: {
	                	imgProperty.setImageResource(R.drawable.property);
	                    break;
	                }
	                case MotionEvent.ACTION_CANCEL:{
	                	imgProperty.setImageResource(R.drawable.property);
	                    break;
	                }
	                }
	                return true;
				// TODO Auto-generated method stub
				
			}
        });
		
		imgMessage.setOnTouchListener(new OnTouchListener() {
	           
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				 
				switch (arg1.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                	imgMessage.setImageResource(R.drawable.hmessage);
	                	
	                	boolean isonline = c.haveNetworkConnection(getApplicationContext());
	            		if(isonline==true)
	            		{
	            			Intent intent = new Intent(symbol.this,listmessage.class);
		    				startActivity(intent);
	            		}
	            		else
	            		{
	            			Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
	            		}
	                	
	                    break;
	                }
	                case MotionEvent.ACTION_UP: {
	                	imgMessage.setImageResource(R.drawable.message);
	                    break;
	                }
	                case MotionEvent.ACTION_CANCEL:{
	                	imgMessage.setImageResource(R.drawable.message);
	                    break;
	                }
	                }
	                return true;
				// TODO Auto-generated method stub
				
			}
        });
		
		imgProfile.setOnTouchListener(new OnTouchListener() {
	           
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				 
				switch (arg1.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                	imgProfile.setImageResource(R.drawable.hprofile);
	                	
	                	boolean isonline = c.haveNetworkConnection(getApplicationContext());
	            		if(isonline==true)
	            		{
	            			Intent intent = new Intent(symbol.this,profile.class);
		    				startActivity(intent);
	            		}
	            		else
	            		{
	            			Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
	            		}
	            		
	                    break;
	                }
	                case MotionEvent.ACTION_UP: {
	                	imgProfile.setImageResource(R.drawable.profile);
	                    break;
	                }
	                case MotionEvent.ACTION_CANCEL:{
	                	imgProfile.setImageResource(R.drawable.profile);
	                    break;
	                }
	                }
	                return true;
				// TODO Auto-generated method stub
				
			}
        });
		
		
		

	
	}
	
	
	private void updateWithNewLocation(Location location) 
    {  
    	
    Criteria locationCritera = new Criteria();
    locationCritera.setAccuracy(Criteria.ACCURACY_FINE);
    locationCritera.setAltitudeRequired(false);
    locationCritera.setBearingRequired(false);
    locationCritera.setCostAllowed(true);
    locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

 
    	 locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
         locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
         
         locManager.getBestProvider(locationCritera, true);
         location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
  
         if(location != null)                                
         {
             latitude = location.getLatitude();
             longitude = location.getLongitude();
             
             String lat1 = Double.toString(latitude);
             String lng1 = Double.toString(longitude);
             Toast.makeText(getApplicationContext()," Function :"+ lat1, Toast.LENGTH_LONG).show();
             Toast.makeText(getApplicationContext()," Function :"+ lng1, Toast.LENGTH_LONG).show();
         }
         
        
       // TextView myLocationText = (TextView)findViewById(R.id.txtfind);
        String latLongString = "";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;



        } else {
            latLongString = "No location found";
        }
        String lat1 = Double.toString(latitude);
        String lng1 = Double.toString(longitude);
        Toast.makeText(getApplicationContext()," Location Updated :"+ lat1 + " - "+lng1, Toast.LENGTH_LONG).show();
         //txtfind.setText("Your Current Position is:\n" +
          //      latLongString);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
           // updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

	

}
