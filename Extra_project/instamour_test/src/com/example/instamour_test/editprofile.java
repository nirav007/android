package com.example.instamour_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;





//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class editprofile extends Activity {

	private InputStream is;
	private StringBuilder sb;
	private String result;
	function c = new function();
	private Button btn_editsave;
	private TextView txtIndentity;
	private TextView txtLookingfor;
	private TextView txtBodytype;
	private TextView txtSmoker;
	private TextView txtEthnicity;
	private EditText txtUsername;
	private EditText txtHeight;
	private EditText txtAboutme;
	
	
	String updateresult;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);

		btn_editsave = (Button) findViewById(R.id.btn_editsave);

		btn_editsave.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				new AsyncAction().execute(null, null, null);
				//EditProfile();
			}
		});

		txtIndentity = (TextView) findViewById(R.id.txtIndentity);

		txtIndentity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Adventurer");
				list.add("All Business");
				list.add("Analytical");
				list.add("Artsy");
				list.add("Caffeine Junkie");
				list.add("Chef");
				list.add("Creative");
				list.add("Cultured");
				list.add("Extraterrestrial");
				list.add("Film Buff");
				list.add("Fist Pumper");
				list.add("Gamer");

				list.add("Gym Rat");
				list.add("Hipster");
				list.add("Insomniac");
				list.add("Inspirer");
				list.add("Jokester");
				list.add("Lush");

				list.add("Meow");
				list.add("Motivator");
				list.add("Musician");
				list.add("Must Love Dogs");
				list.add("Nurturer");
				list.add("Outdoorsy");

				list.add("Smiley");
				list.add("Techie");
				list.add("Thinker");
				list.add("Traveler");
				list.add("Wired");

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						editprofile.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();
								
								txtIndentity.setText(list.get(position).toString());

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		txtLookingfor = (TextView) findViewById(R.id.txtLookingfor);
		txtLookingfor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("friends first");
				list.add("love");
				list.add("marriage");
				list.add("a partner in crime");
				list.add("a relationship");
				list.add("some fun");
				list.add("a soulmate");
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						editprofile.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();
								
								txtLookingfor.setText(list.get(position).toString());

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});

		txtBodytype = (TextView) findViewById(R.id.txtBodytype);
		txtBodytype.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Athletic");
				list.add("Slim");
				list.add("Average");
				list.add("A Little Extra");
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						editprofile.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();
								
								txtBodytype.setText(list.get(position).toString());

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		txtSmoker = (TextView) findViewById(R.id.txtSmoker);
		txtSmoker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Yes");
				list.add("No");
			
				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						editprofile.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();
								
								txtSmoker.setText(list.get(position).toString());

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		txtEthnicity = (TextView) findViewById(R.id.txtEthnicity);
		
		txtEthnicity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Hispanic");
				list.add("Caucasian");
				list.add("Asian");
				list.add("Middle Eastern");
				list.add("European");
				list.add("Indian");
				list.add("Black");
				list.add("Other");
			
				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						editprofile.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								txtEthnicity.setText(list.get(position).toString());

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtHeight= (EditText) findViewById(R.id.txtHeight);
		txtAboutme= (EditText) findViewById(R.id.txtAboutme);
	}
	
	@SuppressLint("NewApi")
	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				EditProfile();
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();
			Toast.makeText(getApplicationContext(), updateresult.toString(), Toast.LENGTH_LONG).show();
			
		}

		@SuppressLint("NewApi")
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(editprofile.this);
			pd.setMessage("updating profile ..");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	private void EditProfile() {

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(editprofile.this);
		String uid = preferences.getString("uid", "");

		

		GPSTracker gps;
		double latitude = 0;
		double longitude = 0;
		gps = new GPSTracker(editprofile.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
		}
		
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://192.168.1.4/instamour/APP_files/profile_edit.php");

		
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			
			
			
			mpEntity.addPart("uid", new StringBody("25"));
			mpEntity.addPart("identity", new StringBody(txtIndentity.getText().toString()));
			mpEntity.addPart("height", new StringBody(txtHeight.getText().toString()));
			mpEntity.addPart("body_type", new StringBody(txtBodytype.getText().toString()));
			mpEntity.addPart("looking_for", new StringBody(txtLookingfor.getText().toString()));
			mpEntity.addPart("ethnicity", new StringBody(txtEthnicity.getText().toString()));
			mpEntity.addPart("smoker", new StringBody(txtSmoker.getText().toString()));
			mpEntity.addPart("about_me", new StringBody(txtAboutme.getText().toString()));
			mpEntity.addPart("uname", new StringBody(txtUsername.getText().toString()));
			mpEntity.addPart("gender", new StringBody(""));
			mpEntity.addPart("dob", new StringBody(""));
			mpEntity.addPart("ltd", new StringBody(String.valueOf(latitude)));
			mpEntity.addPart("lngd", new StringBody(String.valueOf(longitude)));

		} catch (Exception e) {

		} finally {
		}

		httppost.setEntity(mpEntity);
		HttpResponse response;
		try {
			// Log.d(TAG, "UPLOAD: about to execute");
			response = httpclient.execute(httppost);
			// /Log.d(TAG, "UPLOAD: executed");
			HttpEntity resEntity = response.getEntity();
			is = resEntity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// convert response to string
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

			if (root.has("Reg")) {

				JSONObject reg = root.getJSONObject("Reg");
				updateresult = reg.getString("result");

			} else {
				JSONObject reg = root.getJSONObject("Result");
				updateresult = reg.getString("result");	
			}
		}
		catch (Exception e) {

		}
		}
		
	

		
	
}
