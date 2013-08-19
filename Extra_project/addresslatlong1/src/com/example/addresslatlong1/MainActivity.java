package com.example.addresslatlong1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static// url to make request
	String address = "Rajkot, Gujarat, India";

	private static String url = "http://maps.googleapis.com/maps/api/geocode/json?address="
			+ address + "&sensor=true";

	// JSON Node names
	private static final String TAG_RESULTS = "results";
	private static final String TAG_GEOMETRY = "geometry";
	private static final String TAG_VIEWPORT = "viewport";
	private static final String TAG_NORTHEAST = "northeast";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	// contacts JSONArray
	JSONArray results = null;

	TextView tvlng, tvlat;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvlat = (TextView) findViewById(R.id.tvlat);
		tvlng = (TextView) findViewById(R.id.tvlng);

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		address = "Rajkot, Gujarat, India";

		address = address.replaceAll(" ", "%20");
		url = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ address + "&sensor=true";

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			// Getting Array of results
			results = json.getJSONArray(TAG_RESULTS);

			Toast.makeText(getApplication(),
					"Number of results : " + results.length(),
					Toast.LENGTH_LONG).show();

			for (int i = 0; i < results.length(); i++) {
				JSONObject r = results.getJSONObject(i);

				// geometry and location is again JSON Object
				JSONObject geometry = r.getJSONObject(TAG_GEOMETRY);

				JSONObject viewport = geometry.getJSONObject(TAG_VIEWPORT);

				JSONObject northest = viewport.getJSONObject(TAG_NORTHEAST);

				String lat = northest.getString(TAG_LAT);
				String lng = northest.getString(TAG_LNG);

				tvlat.setText(lat);
				tvlng.setText(lng);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}