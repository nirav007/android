package com.newproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newproject.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.test.IsolatedContext;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class interest extends Activity {
	
	SeekBar seekBar_reminder,seekbar_social,seekbar_distance;
	TextView seekBarValue;
	private InputStream is;
	private StringBuilder sb;
	private String result;
	private TextView saveinterest;
	int reminder_day=0,user_id,social_action,reminder_day_notification,cnt=0;
	DataHendleCls obS = new DataHendleCls();
	private EditText edit_interest;
	private TextView save_interest;
	private TextView btnback;
	
	CountDownTimer t;
	static final int TIME_DIALOG_ID = 999;
	private static final int NOTIF_ID = 1234;
	NotificationManager notifManager;
	private TextView txt_distance;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.interest);
		
		init();
		
		
	
	//	findlocation();
		
		
		
		select_social_prefrence();
		
		saveinterest.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
				// Toast.makeText(interest.this,reminder_day, Toast.LENGTH_LONG).show();
				SharedPreferences p  = getSharedPreferences("reminder", MODE_WORLD_WRITEABLE);
			  	SharedPreferences.Editor preEditor = p.edit();
			  	preEditor.putInt("reminder", reminder_day );
			  	preEditor.commit(); // save globle data
		  	
			  	
			  	saveprefrence(); // save social prefrences
			  	
			  	cnt=0;
				
				 t = new CountDownTimer( Long.MAX_VALUE , 1000) {

			            public void onTick(long millisUntilFinished) {
			                Log.d("test","Timer tick");
			                setCurrentTimeOnView1();
			                
			                cnt++;
			            }

			            public void onFinish() {
			                Log.d("test","Timer last tick");
			                
			            }
			         }.start();
				
			}
		});
		
		btnback.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		save_interest.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
				saveuserinterest();	
				
			}
		});
		
		seekBar_reminder.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}
			
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				
					
			}
			
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				seekBarValue.setText(Integer.toString(progress)+ " Days");
				 reminder_day = progress;
			}
		});
		
		
		seekbar_distance.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			
				txt_distance.setText(Integer.toString(progress)+ " Mile");
			}
		});
		seekbar_social.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				social_action=progress;
				
			}
		});
	}
	
	

	private void init() {
		// TODO Auto-generated method stub
		
		SharedPreferences sp = this.getSharedPreferences("reminder", MODE_WORLD_READABLE);
		reminder_day_notification = sp.getInt("reminder",0);
		
		seekBar_reminder = (SeekBar) findViewById(R.id.seekbar_reminder);
		seekBar_reminder.incrementProgressBy(1);
		seekBar_reminder.setProgress(reminder_day_notification);
		seekBar_reminder.setMax(7);
		
		seekBarValue = (TextView)findViewById(R.id.txtreminder);
		edit_interest = (EditText) findViewById(R.id.edit_interest);
		txt_distance = (TextView) findViewById(R.id.txt_distance);
		
		
		seekbar_social = (SeekBar) findViewById(R.id.seekbar_social);
		seekbar_social.incrementProgressBy(1);
		seekbar_social.setProgress(0);
		
		saveinterest = (TextView) findViewById(R.id.saveinterest);
		save_interest = (TextView) findViewById(R.id.save_interest);
		
		btnback = (TextView) findViewById(R.id.btnback);
		
		seekbar_distance = (SeekBar) findViewById(R.id.seekbar_distance);
		//seekbar_social.incrementProgressBy(1);
		seekbar_distance.setMax(10);
		
		SharedPreferences sp1 = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		user_id = sp1.getInt("user_id",0);
	}

	private void saveuserinterest()
	{
		try
        {	
			String interestname  = edit_interest.getText().toString().trim();
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(obS.interestUrl);
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
			nameValue.add(new BasicNameValuePair("name"," "+ interestname));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
			edit_interest.setText("");
			
        }
        catch (Exception e) { } 
	}
	
	private void saveprefrence()
	{
		try
        {	
			String interestname  = edit_interest.getText().toString().trim();
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(obS.socialUrl);
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
			nameValue.add(new BasicNameValuePair("action"," "+ social_action ));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
			edit_interest.setText("");
			
        }
        catch (Exception e) { } 
	}
	
	public void setCurrentTimeOnView1()
	{
		if(cnt==(reminder_day_notification*1000)) // second display
		{
			notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Notification note = new Notification(R.drawable.icon, "Reminder", System.currentTimeMillis());

			PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, interest.class), 0);

			note.setLatestEventInfo(this, "Setting", "Reminder", intent);
        
			notifManager.notify(NOTIF_ID, note);
			
			cnt=0;
			
			t.cancel();
					
		}
				       
		}
		
	public void select_social_prefrence()
	{
		 try
	        {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.socialUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
	        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	        	HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();       	
	        	
	        }
	        catch (Exception e) {
	        	Log.e("log_tag", "Error in HTTP connection" + e.toString()); 	
			}
	        
	        try
	        {
	        	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	        	sb = new StringBuilder();
	        	sb.append(BufR.readLine() + "\n");
	        	String line = "0";
	        	
	        	while((line = BufR.readLine()) != null)
	        	{
	        		sb.append(line + "\n");
	        	}
	        	
	        	is.close();
	        	result = sb.toString();
	        }
	        catch (Exception e) {
	        	Log.e("log_tag", "Error in convert String" + e.toString());
			}
	        
	        try{
	        	
	        	
        	 	JSONObject json_data = new JSONObject(result);
        	
        
        	
        	String status = json_data.getString("Status");
        	
        
        	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        	
        	String data = json_data.getString("Data");
        	JSONArray jArray1= new JSONArray(data);
        	JSONObject json_data1 = null;
        	json_data1 = jArray1.getJSONObject(0);
        	String pre = json_data1.getString("prefrence");
        	//Toast.makeText(getApplicationContext(), pre, Toast.LENGTH_LONG).show();
        	
        	Integer pre_value = new Integer(pre);
        	seekbar_social.setProgress(pre_value);
        
        }
        catch (JSONException e){} 	
		
        catch (Exception e){	}
}
	        
	       
	
	public void findlocation()
	{
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
        locationB.setLongitude(70.7433333333);  

        double distance = locationA.distanceTo(locationB);
        
        String d = new Double(distance).toString();
       
        double mile =  0.000621371192237334 * distance;
        
        //Toast.makeText(getApplicationContext(), d.toString(), Toast.LENGTH_LONG).show();
        
        String Mile = new Double(mile).toString();
       // Toast.makeText(getApplicationContext(), Mile.toString(), Toast.LENGTH_LONG).show();
        
        int in;

        in= (int) (mile);
        seekbar_distance.setProgress(in);
        txt_distance.setText(in+ " Mile");
	}
			
	private void updateWithNewLocation(Location location) {
       // TextView myLocationText = (TextView)findViewById(R.id.myLocationText);
        String latLongString = "";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;

         //   myLocationText.setText("Your Current Position is:\n" +
         //           latLongString);

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
