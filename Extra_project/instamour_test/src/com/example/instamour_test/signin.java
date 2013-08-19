package com.example.instamour_test;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.To;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class signin extends Activity implements LoginListener{

	private EditText edt_signinemail;
	private EditText edt_signinpass;
	private ImageView img_signinlogin;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	String getEmail, getPassword;
	
	
	public final String KODEFUNFBAPP_ID = "303121969700700";
	
	
	
	private FBLoginManager fbLoginManager;
	function c = new function();
	private ImageView img_back;
	private ImageView img_forgotpass;
	
	String passResult;
	private ImageView btn_fbsignin;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signin);
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		

		edt_signinemail = (EditText) findViewById(R.id.edt_loginemail);
		edt_signinpass = (EditText) findViewById(R.id.edt_loginpassword);
		
		img_back = (ImageView) findViewById(R.id.imgback);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intent = new Intent(signin.this,login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		img_signinlogin = (ImageView) findViewById(R.id.img_signinlogin);
		img_forgotpass = (ImageView) findViewById(R.id.img_forgotpass);
		
		//edt_signinemail.setText("seb@seb.com"); 
		//edt_signinpass.setText("seb");
		edt_signinemail.setText("L@l.com");
		edt_signinpass.setText("l");
		
		
		img_signinlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean isonline = c
						.haveNetworkConnection(getApplicationContext());
				if (isonline == true) {
					new AsyncAction().execute(null, null, null);
				} else {
					Toast.makeText(getApplicationContext(),
							"No Data Connection Avaible", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
		
		img_forgotpass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				final AlertDialog.Builder alert = new AlertDialog.Builder(signin.this);
			    final EditText input = new EditText(signin.this);
			    alert.setView(input);
			    alert.setTitle("Forgot Password");
			    alert.setMessage("Enter Your Email");
			    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            String value = input.getText().toString().trim();
			            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
			            //new AsyncActionEmail.execute(null, null,null);
			            new AsyncActionPass().execute(input.getText().toString().trim(), null,null);
			        }
			    });

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			        }
			    });
			    alert.show();  
				
			}
		});
		
		btn_fbsignin =   (ImageView) findViewById(R.id.btn_fbsignin);
		
		btn_fbsignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connectToFacebook();
			}
		});
	}

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
		
		
	

		fbLoginManager = new FBLoginManager(this,
				R.layout.signin, 
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
		fbLoginManager.displayToast("Location" + user.getLocation() );
		fbLoginManager.displayToast("birthdate" + user.getBirthday() );
		fbLoginManager.displayToast("Hometown" + user.getHometown() );
		fbLoginManager.displayToast("Reliagn" + user.getReligion() );
		
		Toast.makeText(getApplicationContext(), user.getId(), Toast.LENGTH_LONG).show();

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				c.link.toString()+"/FB_signin.php");
		
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			
			mpEntity.addPart("fbid", new StringBody(user.getId()));
		
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
			
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(signin.this);
			
			SharedPreferences.Editor editor = preferences.edit();

			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			Log.e("log_tag", "result is : " + root.toString());
			if (root.has("Login")) {

				JSONObject login = root.getJSONObject("Login");
				Log.e("log_tag", "result is : " + login.toString());
				String uid = login.getString("uid");
				String uname = login.getString("uname");
				String email = login.getString("email");
				String gender = login.getString("gender");
				String dob = login.getString("dob");
				String ethnicity = login.getString("ethnicity");
				String city = login.getString("city");
				String ltd = login.getString("ltd");
				String lngd = login.getString("lngd");
				String photo = login.getString("photo");
				String fbid = login.getString("fbid");
				String video1 = login.getString("video1");	
				String video2 = login.getString("video2");
				String video3 = login.getString("video3");
				String video4 = login.getString("video4");
				String video_count = login.getString("video_count");
					String pid = login.getString("pid");
					String identity = login.getString("identity");
					String height = login.getString("height");
					String body_type = login.getString("body_type");
					String looking_for = login.getString("looking_for");
					String smoker = login.getString("smoker");
					String about_me = login.getString("about_me");
					String chat = login.getString("chat");
					String video_chat = login.getString("video_chat");
					String disable_account = login.getString("disable_account");
					
				
					////Age/ Height/ Identity/ Body type/ Looking for/ Location
					editor.putString("validlogin", "true");
					editor.putString("uid", String.valueOf(uid));
					editor.putString("email", String.valueOf(email));
					editor.putString("uname", String.valueOf(uname));
					editor.putString("gender", String.valueOf(gender));
					editor.putString("lat", String.valueOf(ltd));
					editor.putString("lng", String.valueOf(lngd));
					editor.putString("photo", String.valueOf(photo));
					editor.putString("height", String.valueOf(height));
					editor.putString("body_type", String.valueOf(body_type));
					editor.putString("Looking for", String.valueOf(looking_for));
					editor.putString("city", String.valueOf(city));
					editor.putString("identity", String.valueOf(identity));
					editor.putString("about_me", String.valueOf(about_me));
					editor.putString("chat", String.valueOf(chat));
					editor.putString("video_chat", String.valueOf(video_chat));
					editor.putString("disable_account", String.valueOf(disable_account));
					
				
					
					//MyApp appState = ((MyApp)getApplicationContext());
				   //appState.setState(uname);
					
					// session login
					editor.putString("session_login", String.valueOf("true"));
					
					
					editor.commit();
				//editor.putString("uid", uid);

				//editor.commit();
					
					Intent intent = new Intent(signin.this,SampleActivity.class);
					startActivity(intent);

			}

			else if  (root.has("Result")) {			
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						signin.this);
				builder.setMessage("Login failed")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				
				//editor.putString("validlogin", "false");
				//editor.commit();
			}
			
		
			
		} catch (Exception e) {
		}
		
		
	}

	public void logoutSuccess() {
		fbLoginManager.displayToast("Logout Success!");
	}

	public void loginFail() {
		fbLoginManager.displayToast("Login Epic Failed!");
	}
	private class AsyncActionPass extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				password(arg0[0].toString());
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();
			
			Toast.makeText(getApplicationContext(),passResult,
					Toast.LENGTH_LONG).show();

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(signin.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	
	
	@SuppressLint("NewApi")
	private void password(String email) {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		

		result = c
				.getString(c.link.toString()+"/forgotpassword.php?uid="+""+"&Email=" + email.toString());

		/*result = c
				.getString("http://192.168.1.1/instamour/APP_files/forgotpassword.php?uid="+""+"&email=" + "nirav.r@wingstechsolutions.com");*/
		
		try {

			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("Result").getJSONObject(0);

			Log.e("root",root.toString());
			if (root.has("Result")) {

				//JSONObject login = root.getJSONObject("Result");
				//Log.e("login",login.toString());
				String Result = root.getString("Result");
				Log.e("login",Result.toString());
				
				passResult = Result.toString();
			}

		}

		catch (Exception e) {

		}
	}
	
	@Override
	public void onBackPressed() {
	   
		finish();
	   //Intent setIntent = new Intent(signin.this,login.class);
	   //setIntent.addCategory(Intent.CATEGORY_HOME);
	   //setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   //startActivity(setIntent);
	}
	@SuppressLint("NewApi")
	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				getLogin();

				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

			//Intent intent = new Intent(signin.this,com.korovyansk.android.sample.slideout.SampleActivity.class);
			//startActivity(intent);
			
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(signin.this);
			String validlogin = preferences.getString("validlogin", "");
			
			/*Toast.makeText(getApplicationContext(), validlogin, Toast.LENGTH_LONG)
					.show();*/
			if (validlogin.equals("false")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						signin.this);
				builder.setMessage("Login failed")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// do things
										// edt_signinemail.setText(null);
										// edt_signinpass.setText(null);
										edt_signinemail.setFocusable(true);
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			} else if (validlogin.equals("true")) {
				//Intent intent = new Intent(signin.this, createvideo.class);
				//startActivity(intent);
				Intent intent = new Intent(signin.this,SampleActivity.class);
			startActivity(intent);
				
				
			}
		}

		@SuppressLint("NewApi")
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(signin.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	public void getLogin() {
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(signin.this);
		
		SharedPreferences.Editor editor = preferences.edit();
		
		
		getEmail = edt_signinemail.getText().toString().trim();
		getPassword = edt_signinpass.getText().toString().trim();
		
		 Date cDate = new Date();
		 String lastOnlineDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

		 Log.e("last", c.link.toString() + "/user_login.php?email="
				+ getEmail + "&pwd=" + getPassword + "&last_online="+lastOnlineDate);
		result = c.getString(c.link.toString() + "/user_login.php?email="
				+ getEmail + "&pwd=" + getPassword + "&last_online="+lastOnlineDate);

		try {
			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			

			if (root.has("Login")) {

				JSONObject login = root.getJSONObject("Login");
				String uid = login.getString("uid");
				String uname = login.getString("uname");
				String email = login.getString("email");
				String gender = login.getString("gender");
				String dob = login.getString("dob");
				String ethnicity = login.getString("ethnicity");
				String city = login.getString("city");
				String ltd = login.getString("ltd");
				String lngd = login.getString("lngd");
				String photo = login.getString("photo");
				String fbid = login.getString("fbid");
				String video1 = login.getString("video1");	
				String video2 = login.getString("video2");
				String video3 = login.getString("video3");
				String video4 = login.getString("video4");
				String video_count = login.getString("video_count");
					String pid = login.getString("pid");
					String identity = login.getString("identity");
					String height = login.getString("height");
					String body_type = login.getString("body_type");
					String looking_for = login.getString("looking_for");
					String smoker = login.getString("smoker");
					String about_me = login.getString("about_me");
					String chat = login.getString("chat");
					String video_chat = login.getString("video_chat");
					String disable_account = login.getString("disable_account");
					
				
					////Age/ Height/ Identity/ Body type/ Looking for/ Location
					editor.putString("validlogin", "true");
					editor.putString("uid", String.valueOf(uid));
					editor.putString("email", String.valueOf(email));
					editor.putString("uname", String.valueOf(uname));
					editor.putString("gender", String.valueOf(gender));
					editor.putString("lat", String.valueOf(ltd));
					editor.putString("lng", String.valueOf(lngd));
					editor.putString("photo", String.valueOf(photo));
					editor.putString("height", String.valueOf(height));
					editor.putString("body_type", String.valueOf(body_type));
					editor.putString("Looking for", String.valueOf(looking_for));
					editor.putString("city", String.valueOf(city));
					editor.putString("identity", String.valueOf(identity));
					editor.putString("about_me", String.valueOf(about_me));
					editor.putString("chat", String.valueOf(chat));
					editor.putString("video_chat", String.valueOf(video_chat));
					editor.putString("disable_account", String.valueOf(disable_account));
					
					//MyApp appState = ((MyApp)getApplicationContext());
				   //appState.setState(uname);
					
					// session login
					editor.putString("session_login", String.valueOf("true"));
					
					
					editor.commit();
				
			}
			else if  (root.has("Result")) {			
				
				editor.putString("validlogin", "false");
				editor.commit();
			}
			
		

		} catch (Exception e) {

		}
	}

}

