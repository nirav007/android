package com.example.nstamour;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	private InputStream is;
	private StringBuilder sb;
	private String result;
	function c = new function();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		result = c.getString(c.link.toString() + "/user_login.php");
		// http://www.instamour.com/APP_files/user_login.php?email=Far@far.com&pwd=far
		result = c
				.getString("http://www.instamour.com/APP_files/user_login.php?email=Far@far.com&pwd=far12");
		try {

			/*
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * Log.e("log_tag", "root :" + result.toString());
			 * 
			 * JSONObject json_data = new JSONObject(result);
			 * 
			 * String root = json_data.getString("root");
			 * 
			 * Toast.makeText(getApplicationContext(), root,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * JSONArray json_array = new JSONArray(root);
			 * 
			 * json_data = json_array.getJSONObject(0);
			 * 
			 * String Login = json_data.getString("Login");
			 * 
			 * Toast.makeText(getApplicationContext(), Login,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * json_data = new JSONObject(Login);
			 * 
			 * String email = json_data.getString("email");
			 * 
			 * Toast.makeText(getApplicationContext(), email,
			 * Toast.LENGTH_LONG).show();
			 */

			/*
			 * 
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * Log.e("log_tag", "root :" + result.toString());
			 * 
			 * JSONObject json_data = new JSONObject(result);
			 * 
			 * String root = json_data.getString("root");
			 * 
			 * Toast.makeText(getApplicationContext(), root,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * JSONArray json_array = new JSONArray(root);
			 * 
			 * json_data = json_array.getJSONObject(0);
			 * 
			 * if(json_data.getString("Login").length()>=1) { String Login =
			 * json_data.getString("Login");
			 * Toast.makeText(getApplicationContext(), Login,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * json_data = new JSONObject(Login);
			 * 
			 * String email = json_data.getString("email");
			 * 
			 * Toast.makeText(getApplicationContext(), email,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * 
			 * }
			 */

			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);
			if (root.has("Result")) {
				JSONObject loginRes = root.getJSONObject("Result");
				if (loginRes.has("Result")) {
					String status = loginRes.getString("Result");
					if (status.equalsIgnoreCase("Login failed")) {
						Toast.makeText(getApplicationContext(), status,
								Toast.LENGTH_LONG).show();
					}
				}

			}

			if (root.has("Login")) {

				JSONObject login = root.getJSONObject("Login");
				String email = login.getString("email");
				Toast.makeText(getApplicationContext(), email,
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
