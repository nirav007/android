package com.example.instamour_test;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.To;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class login extends Activity implements LoginListener{

	private ImageView login_emailsignup;
	private ImageView login_signin;
	private ImageView btn_fbsignup;
	String signup_city;
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	
	public final String KODEFUNFBAPP_ID = "303121969700700";
	
	function c = new function();
	
	private FBLoginManager fbLoginManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		login_emailsignup = (ImageView) findViewById(R.id.login_emailsignup);

		login_signin = (ImageView) findViewById(R.id.login_signin);

		login_emailsignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Intent intent = new Intent(login.this, signup.class);
				//startActivity(intent);
				Intent intent = new Intent(login.this, signup.class);
				startActivity(intent);
			}
		});

		login_signin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(login.this, signin.class);
				startActivity(intent);
			}
		});
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		
		btn_fbsignup = (ImageView) findViewById(R.id.btn_fbsignup);
		
		btn_fbsignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				connectToFacebook();
				
			}
		});
	}
	
	//@Override
	/*public void onBackPressed() {
	   
	   Intent setIntent =  new Intent(Intent.ACTION_MAIN);
	   setIntent.addCategory(Intent.CATEGORY_HOME);
	   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(setIntent);
	} */
	
	
	public void connectToFacebook(){

		//read about Facebook Permissions here:
		//http://developers.facebook.com/docs/reference/api/permissions/
		String permissions[] = {
				"user_about_me",

				"user_location",
				
				"email",

				"publish_checkins",
				"publish_stream",


		};
		
		
	/*	String permissions[] = {
				"user_about_me",
				"user_activities",
				"user_birthday",
				"user_checkins",
				"user_education_history",
				"user_events",
				"user_groups",
				"user_hometown",
				"user_interests",
				"user_likes",
				"user_location",
				"user_notes",
				"user_online_presence",
				"user_photo_video_tags",
				"user_photos",
				"user_relationships",
				"user_relationship_details",
				"user_religion_politics",
				"user_status",
				"user_videos",
				"user_website",
				"user_work_history",
				"email",

				"read_friendlists",
				"read_insights",
				"read_mailbox",
				"read_requests",
				"read_stream",
				"xmpp_login",
				"ads_management",
				"create_event",
				"manage_friendlists",
				"manage_notifications",
				"offline_access",
				"publish_checkins",
				"publish_stream",
				"rsvp_event",
				"sms",
				//"publish_actions",

				"manage_pages"

		};*/


		fbLoginManager = new FBLoginManager(this,
				R.layout.login, 
				KODEFUNFBAPP_ID, 
				permissions);

		if(fbLoginManager.existsSavedFacebook()){
			fbLoginManager.loadFacebook();
		}
		else{
			fbLoginManager.login();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data){
		fbLoginManager.loginSuccess(data);
	}

	public void loginSuccess(Facebook facebook) {
		GraphApi graphApi = new GraphApi(facebook);

		User user = new User();

		try{
			user = graphApi.getMyAccountInfo();

			//update your status if logged in
			//graphApi.setStatus("Hello, world!");
		} catch(EasyFacebookError e){
			Log.d("TAG: ", e.toString());
		}

		fbLoginManager.displayToast("First name" + user.getFirst_name() );
		fbLoginManager.displayToast("Email" + user.getEmail() );
		fbLoginManager.displayToast("Gender" + user.getGender() );
		fbLoginManager.displayToast("Id" + user.getId() );
		Log.e("id", user.getId() );
		fbLoginManager.displayToast("Location" + user.getLocation() );
		fbLoginManager.displayToast("birthdate" + user.getBirthday() );
		fbLoginManager.displayToast("Hometown" + user.getHometown() );
		fbLoginManager.displayToast("Reliagn" + user.getReligion() );
		
	
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				c.link.toString()+"/FB_login.php");

		

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(login.this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("validsignup", "true");
		editor.commit();
		
		GPSTracker gps;
		double latitude = 0;
		double longitude = 0;
		gps = new GPSTracker(login.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			editor.putString("lat", String.valueOf(latitude));
			editor.putString("lng", String.valueOf(longitude));

			editor.commit();
			// \n is for new line
			try {
				Geocoder gcd = new Geocoder(getApplicationContext(),
						Locale.getDefault());
				List<Address> addresses = gcd.getFromLocation(latitude,
						longitude, 1);
				if (addresses.size() > 0)
					System.out.println(addresses.get(0).getLocality());
				/*
				 * Toast.makeText(getApplicationContext(),
				 * addresses.get(0).getLocality(), Toast.LENGTH_LONG) .show();
				 */
				signup_city = addresses.get(0).getLocality();
			} catch (Exception e) {

			}

		} else {

			gps.showSettingsAlert();
		}
		
		
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			
			mpEntity.addPart("fbid", new StringBody(user.getId()));
			mpEntity.addPart("fbname", new StringBody(user.getName()));
			mpEntity.addPart("email", new StringBody(user.getEmail()));
			
			mpEntity.addPart("gender", new StringBody(user.getGender()));
			mpEntity.addPart("dob", new StringBody(user.getBirthday()));
			mpEntity.addPart("ethnicity", new StringBody(""));
			mpEntity.addPart("city", new StringBody(signup_city));
			mpEntity.addPart("ltd", new StringBody(String.valueOf(latitude)));
			mpEntity.addPart("lngd", new StringBody(String.valueOf(longitude)));

			mpEntity.addPart("last_online", new StringBody(""));
		} catch (Exception e) {

		}

		httppost.setEntity(mpEntity);
		HttpResponse response;
		try {
			// Log.d(TAG, "UPLOAD: about to execute");
			response = httpclient.execute(httppost);
			// /Log.d(TAG, "UPLOAD: executed");
			HttpEntity resEntity = response.getEntity();
			is = resEntity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("log_tag", "result is : " + result.toString());
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());

		}

		try {

			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			Log.e("log_tag", "result is : " + root.toString());
			if (root.has("Login")) {

				JSONObject login = root.getJSONObject("Login");
				Log.e("log_tag", "result is : " + login.toString());
				String uid = login.getString("uid");
				Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();
				editor.putString("uid", uid);

				editor.commit();

			}

			if (root.has("Result")) {
				JSONObject loginRes = root.getJSONObject("Result");
				/*if (loginRes.has("result")) {
					String status = loginRes.getString("result");
					if (status.equalsIgnoreCase("Email already exists")) {
						editor.putString("validsignup", "false");
						editor.commit();

					}
				}*/
				
				Toast.makeText(getApplicationContext(),"Email already exists" , Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
		}
		httppost.setEntity(mpEntity);
		
		
	}

	public void logoutSuccess() {
		fbLoginManager.displayToast("Logout Success!");
	}

	public void loginFail() {
		fbLoginManager.displayToast("Login Epic Failed!");
	}
	
	@Override
	public void onBackPressed() {
	   
	   Intent setIntent = new Intent(login.this,middleoverlay.class);
	   //setIntent.addCategory(Intent.CATEGORY_HOME);
	   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(setIntent);
	}


}
