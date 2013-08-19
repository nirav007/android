package com.example.instamour_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class signupnext extends Activity {

	private Spinner spinner_signup;
	String signup_city;
	private TextView img_female;
	private TextView img_male;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	function c = new function();

	private String gender = "male";
	private TextView txt_getlocation;
	private DatePicker datepicker_signup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signupnext);

		spinner_signup = (Spinner) findViewById(R.id.spinner_signup);
		txt_getlocation = (TextView) findViewById(R.id.txt_getlocation);

		img_female = (TextView) findViewById(R.id.signup_imgfemale);
		img_male = (TextView) findViewById(R.id.signup_imgmale);

		datepicker_signup = (DatePicker) findViewById(R.id.datepicker_signup);

		ArrayList<String> array = new ArrayList<String>();
		array.add("Caucasian");
		array.add("Indian");
		array.add("Hispanic");
		array.add("Middle Eastern");
		array.add("Native American");

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, R.layout.spinnerrow, array);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_signup.setAdapter(spinnerArrayAdapter);

		img_female.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gender = "female";
				Drawable background = getResources().getDrawable(
						R.drawable.maled);
				img_female.setBackgroundDrawable(background);
				background = getResources().getDrawable(R.drawable.females);
				img_male.setBackgroundDrawable(background);
			}
		});

		img_male.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gender = "male";
				Drawable background = getResources().getDrawable(
						R.drawable.males);
				img_female.setBackgroundDrawable(background);
				background = getResources().getDrawable(R.drawable.femaled);
				img_male.setBackgroundDrawable(background);
			}
		});

		txt_getlocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncAction().execute(null, null, null);
				// getinertdata();
			}
		});
	}

	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				getinertdata();

				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(signupnext.this);
			String validsignup = preferences.getString("validsignup", "");

			if (validsignup.equals("false")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						signupnext.this);
				builder.setMessage("Email already exists")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										Intent intent = new Intent(
												signupnext.this, signup.class);
										startActivity(intent);
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
			if (validsignup.equals("true")) {
				Intent intent = new Intent(signupnext.this, createvideo.class);
				startActivity(intent);
			}

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(signupnext.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	private void getinertdata() {

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(signupnext.this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("validsignup", "true");
		editor.commit();

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		int year = datepicker_signup.getYear() - 1900;
		int month = datepicker_signup.getMonth();
		int day = datepicker_signup.getDayOfMonth();

		Date cDate = new Date(year, month, day);
		String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

		/*
		 * Toast.makeText(getApplicationContext(), fDate, Toast.LENGTH_LONG)
		 * .show();
		 */

		Bundle data = getIntent().getExtras();

		String signup_uname = data.getString("signupusername");
		String signup_email = data.getString("signupemail");
		String signup_pwd = data.getString("signuppassword");
		String ethnicity = spinner_signup.getSelectedItem().toString();
		String signupimgpath = data.getString("signupimgpath");

		GPSTracker gps;
		double latitude = 0;
		double longitude = 0;
		gps = new GPSTracker(signupnext.this);

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

			/*
			 * Toast.makeText( getApplicationContext(),
			 * "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			 * Toast.LENGTH_LONG).show();
			 */
		} else {

			gps.showSettingsAlert();
		}

		// Log.d(TAG, "UPLOAD: SendMultipartFile");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				c.link.toString()+"/user_reg1.php");

		File file = new File(signupimgpath);

		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			FormBodyPart userFile = new FormBodyPart("photo", new FileBody(
					file, "image/*"));
			// userFile.addField(Environment.getExternalStorageDirectory()+"/a.png","NEWNAMEOFILE.png");
			mpEntity.addPart(userFile);
			// mpEntity.addPart("photo", new FileBody(file, "image/png"));
			mpEntity.addPart("uname", new StringBody(signup_uname));
			mpEntity.addPart("email", new StringBody(signup_email));
			mpEntity.addPart("pwd", new StringBody(signup_pwd));
			mpEntity.addPart("gender", new StringBody(gender));
			mpEntity.addPart("dob", new StringBody(fDate));
			mpEntity.addPart("ethnicity", new StringBody(ethnicity));
			mpEntity.addPart("city", new StringBody(signup_city));
			mpEntity.addPart("ltd", new StringBody(String.valueOf(latitude)));
			mpEntity.addPart("lngd", new StringBody(String.valueOf(longitude)));

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
			if (root.has("Reg")) {

				JSONObject login = root.getJSONObject("Reg");
				Log.e("log_tag", "result is : " + login.toString());
				String uid = login.getString("uid");

				editor.putString("uid", uid);

				editor.commit();

			}

			if (root.has("Result")) {
				JSONObject loginRes = root.getJSONObject("Result");
				if (loginRes.has("result")) {
					String status = loginRes.getString("result");
					if (status.equalsIgnoreCase("Email already exists")) {
						editor.putString("validsignup", "false");
						editor.commit();

					}
				}

			}
		} catch (Exception e) {
		}
		httppost.setEntity(mpEntity);

		// Intent intent = new Intent(signupnext.this,signin.class);
		// startActivity(intent);

	}

}
