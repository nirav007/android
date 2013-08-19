package com.example.httpcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy); 
				
		boolean a = isNetworkAvailable();
		if (a == true) {
			Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG)
					.show();
		}
				
		
		try {
			URL newurl = new URL("http://www.google.com");
			HttpURLConnection con = (HttpURLConnection) newurl.openConnection();
			readStream(con.getInputStream());

		} catch (Exception e) {
			Log.e("tag", e.getMessage());
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
				
				
		try {
			URL url = new URL("http://www.vogella.com");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			readStream(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) 
	      getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 

	private void readStream(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			String data = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				data += line;

			}
			Toast.makeText(getApplicationContext(), "Data : " + data,
					Toast.LENGTH_LONG).show();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
