package com.example.fpm;


import java.io.InputStream;

import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private TextView txtHead;
	private EditText txtEmail;
	private EditText txtPassword;
	private TextView btnLogin;

	private InputStream is;
	private StringBuilder sb;
	private String result;
	private TextView txtFPM;

	private String getEmail;
	private String getPassword;
	
	String validlogin="false";

	function c = new function();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// txtHead = (TextView) findViewById(R.id.txtHead);

		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT>8){

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
	StrictMode.setThreadPolicy(policy);
	

		}

		txtEmail = (EditText) findViewById(R.id.txtEmail);

		txtPassword = (EditText) findViewById(R.id.txtPassword);

		btnLogin = (TextView) findViewById(R.id.btnLogin);
		txtFPM = (TextView) findViewById(R.id.txtFPM);

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		// txtHead.setTypeface(tf);
		txtEmail.setTypeface(tf);
		txtPassword.setTypeface(tf);
		btnLogin.setTypeface(tf);
		txtFPM.setTypeface(tf);

		 txtEmail.setText("v1@fpm.com");
		 txtPassword.setText("fpm");

		//txtEmail.setText("sanjay@fpm.com");
		//txtPassword.setText("fpm");

		btnLogin.setOnTouchListener(new CustomTouchListener());

		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Intent intent = new Intent(MainActivity.this,tab.class);
				// startActivity(intent);

				// Intent intent = new Intent(MainActivity.this,symbol.class);
				// startActivity(intent);

				
				boolean isonline = c.haveNetworkConnection(getApplicationContext());
				if(isonline==true)
				{
					new AsyncAction().execute(null, null, null);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No Data Connection Avaible", Toast.LENGTH_LONG).show();
				}

				// getLogin();

				// getEmail = "sanjay@fpm.com";
				// getPassword = "fpm";

			}
		});

	}

	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		@Override
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

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(MainActivity.this);
			String validlogin = preferences.getString("validlogin", "");
			if (validlogin.equals("false")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage("Invalid Username/Password")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// do things
										txtEmail.setText(null);
										txtPassword.setText(null);
										txtEmail.setFocusable(true);
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(MainActivity.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	
	
	public void getLogin() {
		getEmail = txtEmail.getText().toString().trim();
		getPassword = txtPassword.getText().toString().trim();

	
		
		result = c.getString(c.link.toString()+"/login.php?username=" + getEmail
							+ "&password=" + getPassword);

		try {

			/*
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show();
			 */
			Log.e("log_tag", "Result :" + result.toString());

			JSONObject json_data = new JSONObject(result);

			String status = json_data.getString("Status");
			/*
			 * Toast.makeText(MainActivity.this,
			 * json_data.getString("Status").toString(),
			 * Toast.LENGTH_LONG).show();
			 */
			if (status.equals("OK")) {

				String data = json_data.getString("Data");

				// get login data

				JSONObject json_data1 = new JSONObject(data);
				String id = json_data1.getString("id");
				String type = json_data1.getString("type");
				String username = json_data1.getString("username");
				String registered_date = json_data1
						.getString("registered_date");
				String userstatus = json_data1.getString("status");
				String vendorname = json_data1.getString("company_name");
				String contactname = json_data1.getString("contact1_name");
				String contactemail = json_data1.getString("contact1_email");
				String contactphone = json_data1.getString("contact1_phone");
				String address;
				if((json_data1.getString("address1").trim().length()==0 ) && (json_data1.getString("address2").trim().trim().length()==0) )
				{
					address="";
				}
				else if( (json_data1.getString("address2").trim().length()==0) )
				{
					address=json_data1.getString("address1");
				}
				else if( (json_data1.getString("address1").trim().length()==0) )
				{
					address=json_data1.getString("address2");
				}
				else
				{
					address = json_data1.getString("address1")+" , "+json_data1.getString("address2");
				}
				//String address = json_data1.getString("address1")+" , "+json_data1.getString("address2");
				
				String address1 ;
				
				if((json_data1.getString("city").length()==0 ) && (json_data1.getString("state").trim().length()==0) && (json_data1.getString("zipcode").trim().length()==0)  )
				{
					address1="";
				}
				else if((json_data1.getString("city").length()==0 ) && (json_data1.getString("state").trim().length()==0))
				{
					address1=json_data1.getString("zipcode");
				}
				else if((json_data1.getString("city").length()==0 ) && (json_data1.getString("zipcode").trim().length()==0))
				{
					address1=json_data1.getString("state");
				}
				else if((json_data1.getString("state").length()==0 ) && (json_data1.getString("zipcode").trim().length()==0))
				{
					address1 = json_data1.getString("city");
				}
				else if((json_data1.getString("city").length()==0 ))
				{
					address1 = json_data1.getString("state")+" , "+json_data1.getString("zipcode");
				}
				else if((json_data1.getString("state").length()==0 ) )
				{
					address1 = json_data1.getString("city")+" , "+json_data1.getString("zipcode");;
				}
				else if( (json_data1.getString("zipcode").trim().length()==0))
				{
					address1 = json_data1.getString("city")+" , "+json_data1.getString("state");
				}
				else
				{
					address1 = json_data1.getString("city")+" , "+json_data1.getString("state")+" , "+json_data1.getString("zipcode");
				}

				/*
				 * Toast.makeText(getApplicationContext(), vendorname,
				 * Toast.LENGTH_LONG).show();
				 * Toast.makeText(getApplicationContext(), type,
				 * Toast.LENGTH_LONG).show();
				 * Toast.makeText(getApplicationContext(), username,
				 * Toast.LENGTH_LONG).show();
				 * Toast.makeText(getApplicationContext(), registered_date,
				 * Toast.LENGTH_LONG).show();
				 * Toast.makeText(getApplicationContext(), userstatus,
				 * Toast.LENGTH_LONG).show();
				 */
				// save value
				
				if(address.trim().length()==0)
				{
					address=address1;
				}
				else
				{
					address=address+"\n"+address1;
				}

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("id", id.toString());
				editor.putString("type", type);
				editor.putString("username", username);
				editor.putString("registered_date", registered_date);
				editor.putString("vendor_name", vendorname);
				editor.putString("notification", "false");
				editor.putString("validlogin", "true");
				editor.putString("contactname", contactname);
				editor.putString("contactemail", contactemail);
				editor.putString("contactphone", contactphone);
				editor.putString("address", address);
				editor.putString("address1", address1);
				
				editor.commit();


				Intent intent = new Intent(MainActivity.this, symbol.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}

			else if (status.equals("Error")) {

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("validlogin", "false");
				editor.commit();
				//validlogin="false";
				

			}

		} catch (Exception e) {

		}
	}

	

	public class CustomTouchListener implements View.OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) view).setTextColor(0xFFED9118); // white
				break;
			case MotionEvent.ACTION_CANCEL:
				((TextView) view).setTextColor(0xFFFFFFFF); // white
				break;
			case MotionEvent.ACTION_UP:
				((TextView) view).setTextColor(0xFFFFFFFF); // black
				break;
			}
			return false;
		}
	}

	

}
