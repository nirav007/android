package com.example.instamour_test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import android.widget.TableRow.LayoutParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class listticket extends Activity {

	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	ArrayList<String> aryTicket = new ArrayList<String>();

	private String[] listArr;
	private String[] listuserleft;
	private String[] listuserright;

	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_left = new ArrayList<String>();
	public ArrayList<String> ary_user_right = new ArrayList<String>();

	function c = new function();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// if (savedInstanceState == null) {

		setContentView(R.layout.userhelpsupportlist);

		listView = (ListView) findViewById(R.id.userList);

		// new AsyncAction().execute(null, null, null);

		filllistdata();

		MyArrayAdapter adapter = new MyArrayAdapter(listticket.this, listuserright);
		listView.setAdapter(adapter);

	}

	@SuppressLint("NewApi")
	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				filllistdata();
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

			MyArrayAdapter adapter = new MyArrayAdapter(listticket.this,
					listArr);
			listView.setAdapter(adapter);
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(listticket.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	public void filllistdata() {

		ary_user_photo.clear();
		listArr = new String[0];

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		result = c
				.getString("http://www.instamour.com/APP_files/search_location.php?uid=1&ltd=22.362&lngd=77.30");

		try {
			JSONObject json_data = new JSONObject(result);
			// JSONObject root = json_data.getJSONObject("root");

			String root = json_data.getString("root");

			JSONArray json_data1 = new JSONArray(root);

			Log.e("A", root.toString());
			/*
			 * String data = json_data.getString("Daters");
			 * 
			 * Toast.makeText(getApplicationContext(), data,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * JSONArray json_data1 = new JSONArray(data);
			 */

			for (int ii = 0; ii < json_data1.length(); ii++) {
				JSONObject root1 = json_data1.getJSONObject(ii);
				// json_data = json_data1.getJSONObject(ii);
				Log.e("B", root1.toString());
				String userproile_pic = root1.getString("Daters");
				Log.e("photo", userproile_pic.toString());

				JSONObject obj = new JSONObject(userproile_pic);

				String photo = obj.getString("photo");
				/*Toast.makeText(getApplicationContext(), photo, Toast.LENGTH_LONG)
						.show();*/

				ary_user_photo.add(photo);
				
				if(ii%2==0)
				{
				
					ary_user_left.add(photo);
				}
				else
				{
					ary_user_right.add(photo);
				}
				
				
			}

			listArr = new String[ary_user_photo.size()];
			listArr = ary_user_photo.toArray(listArr);
			
			listuserleft = new String[ary_user_left.size()];
			listuserleft = ary_user_left.toArray(listuserleft);
			
			listuserright = new String[ary_user_right.size()];
			listuserright = ary_user_right.toArray(listuserright);

		}

		catch (Exception e) {

		}

	}

	@SuppressLint("ResourceAsColor")
	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
	 int i=0;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.userprofilepic, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.userprofilepic, null, true);

			
				ImageView userleft = (ImageView) view.findViewById(R.id.userleft);
				Drawable drawable = LoadImageFromWebOperations("http://www.instamour.com/APP_files/User_files/"
						+ listuserleft[position]);
				userleft.setImageDrawable(drawable);
			
			
			
				ImageView userright = (ImageView) view.findViewById(R.id.userright);
				Drawable drawable1 = LoadImageFromWebOperations("http://www.instamour.com/APP_files/User_files/"
						+ listuserright[position]);
				userright.setImageDrawable(drawable1);
			
			
			i++;

			return view;
		}
	}
	
	
	private Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

}
