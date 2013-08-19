package com.example.instamour_test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class userpendingamours extends Activity {
	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result, uid;

	function c = new function();
	LazyAdapter adapter;
	LazyAdapter1 adapter1;

	private String[] listArr;
	private String[] listArrphoto;
	private String[] listArrDate;
	private String[] listArrid;

	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_name = new ArrayList<String>();
	public ArrayList<String> ary_user_lastdate = new ArrayList<String>();
	public ArrayList<String> ary_user_id = new ArrayList<String>();

	public ArrayList<String> hary_user_photo = new ArrayList<String>();
	public ArrayList<String> hary_user_name = new ArrayList<String>();
	public ArrayList<String> hary_user_lastdate = new ArrayList<String>();
	public ArrayList<String> hary_user_id = new ArrayList<String>();

	private ImageView btn_pending_heartbutton;

	private TextView txtheadtext;

	public String flag = "true";

	private String[] hlistArr;
	private String[] hlistuserphoto;

	private String[] hlistusername;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingyouramours);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		btn_pending_heartbutton = (ImageView) findViewById(R.id.btn_pending_heartbutton);
		txtheadtext = (TextView) findViewById(R.id.txtheadtext);

		btn_pending_heartbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (flag.equals("true")) {
					btn_pending_heartbutton
							.setImageResource(R.drawable.pending_heartbutton1);
					txtheadtext.setText("Hearts Pushed");
					flag = "false";

					filllistdataheart();
					
					
				} 
				
				else if (flag.equals("false")) {
					btn_pending_heartbutton
							.setImageResource(R.drawable.pending_heartbutton);
					txtheadtext.setText("Pending Amours");
					flag = "true";
					filllistdata();
					//adapter = new LazyAdapter(userpendingamours.this, listArr);
					//listView.setAdapter(adapter);
				}
			}
		});

		listView = (ListView) findViewById(R.id.userList);

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(userpendingamours.this,
								R.id.inner_content, width);
						startActivity(new Intent(userpendingamours.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		// new AsyncAction().execute(null, null, null);
		filllistdata();
		
		//filllistdataheart();
		/*
		 * MyArrayAdapter adapter = new
		 * MyArrayAdapter(useryouramours.this,listArr);
		 * listView.setAdapter(adapter);
		 */

		// adapter = new LazyAdapter(userpendingamours.this, listArr);
		// listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(getApplicationContext(),
						userpendingamoursdetails.class);
				intent.putExtra("username", listArr[arg2]);
				intent.putExtra("userphoto", listArrphoto[arg2]);
				intent.putExtra("userid", listArrid[arg2]);
				startActivity(intent);

			}
		});

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

			adapter = new LazyAdapter(userpendingamours.this, listArr);
			listView.setAdapter(adapter);
			/*
			 * MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
			 * listuserright); listView.setAdapter(adapter);
			 */
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(userpendingamours.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	private class AsyncActionHeart extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				filllistdataheart();
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

			adapter1 = new LazyAdapter1(userpendingamours.this, hlistArr);
			listView.setAdapter(adapter1);
			/*
			 * MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
			 * listuserright); listView.setAdapter(adapter);
			 */
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(userpendingamours.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	public void filllistdataheart() {
		
		hary_user_id.clear();
		hary_user_photo.clear();
		hary_user_name.clear();
		
		
		hlistArr = new String[0];
		hlistuserphoto = new String[0];
		hlistusername = new String[0];
		
		
		hlistArr = new String[hary_user_id .size()];
		hlistArr = hary_user_id.toArray(hlistArr);

		hlistuserphoto = new String[hary_user_photo.size()];
		hlistuserphoto = hary_user_photo.toArray(hlistuserphoto);

		hlistusername = new String[hary_user_name.size()];
		hlistusername = hary_user_name.toArray(hlistusername);
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(userpendingamours.this);
		uid = preferences.getString("uid", "");
		Log.e("uid", uid);

		result = c.getString(c.link.toString() + "/heartPushed.php?uid="
				+ uid);

		try {
			JSONObject json_data = new JSONObject(result);
			// JSONObject root = json_data.getJSONObject("root");

			Log.e("result", json_data.toString());
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
				String userproile_pic = root1.getString("Heartpushed");
				Log.e("photo", userproile_pic.toString());

				JSONObject obj = new JSONObject(userproile_pic);

				String photo = obj.getString("photo");
				String uname = obj.getString("uname");
				String id = obj.getString("uid");

				hary_user_photo.add(photo);
				hary_user_name.add(uname);
				hary_user_id.add(id);
				
				Log.e("uid",id);
				Log.e("uname",uname);
				Log.e("photo",photo);
				
				

			}

			hlistArr = new String[hary_user_id .size()];
			hlistArr = hary_user_id.toArray(hlistArr);

			hlistuserphoto = new String[hary_user_photo.size()];
			hlistuserphoto = hary_user_photo.toArray(hlistuserphoto);

			hlistusername = new String[hary_user_name.size()];
			hlistusername = hary_user_name.toArray(hlistusername);
			
			adapter1 = new LazyAdapter1(userpendingamours.this, hlistArr);
			listView.setAdapter(adapter1);

		}

		catch (Exception e) {

		}
	}

	@SuppressLint("NewApi")
	public void filllistdata() {

		// ary_user_photo.clear();
		// listArr = new String[0];
		
		ary_user_photo.clear();
		ary_user_name.clear();
		ary_user_id.clear();
		ary_user_lastdate.clear();
		
		listArr = new String[0];
		listArrid = new String[0];
		listArrphoto = new String[0];
		listArrDate = new String[0];
		
		
		

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(userpendingamours.this);
		String uid = preferences.getString("uid", "");
		Log.e("uid", uid);

		result = c.getString(c.link.toString() + "/view_requests.php?uid="
				+ uid);

		try {
			JSONObject json_data = new JSONObject(result);
			// JSONObject root = json_data.getJSONObject("root");

			Log.e("result", json_data.toString());
			String root = json_data.getString("root");

			Log.e("root", root.toString());

			if (root != "null") {
				JSONArray json_data1 = new JSONArray(root);

				Log.e("A", root.toString());

				for (int ii = 0; ii < json_data1.length(); ii++) {
					JSONObject root1 = json_data1.getJSONObject(ii);
					// json_data = json_data1.getJSONObject(ii);
					Log.e("B", root1.toString());
					String userproile_pic = root1.getString("Requests");
					Log.e("photo", userproile_pic.toString());

					JSONObject obj = new JSONObject(userproile_pic);

					String photo = obj.getString("photo");
					String uname = obj.getString("uname");
					String lastdate = obj.getString("last_online");
					String usid = obj.getString("uid");

					ary_user_id.add(usid);
					ary_user_name.add(uname);
					ary_user_photo.add(photo);
					ary_user_lastdate.add(lastdate);
					
					

				}

				listArr = new String[ary_user_name.size()];
				listArr = ary_user_name.toArray(listArr);

				listArrid = new String[ary_user_id.size()];
				listArrid = ary_user_id.toArray(listArrid);

				listArrphoto = new String[ary_user_photo.size()];
				listArrphoto = ary_user_photo.toArray(listArrphoto);

				listArrDate = new String[ary_user_lastdate.size()];
				listArrDate = ary_user_lastdate.toArray(listArrDate);

				adapter = new LazyAdapter(userpendingamours.this, listArr);
				listView.setAdapter(adapter);

			} else {
				Toast.makeText(getApplicationContext(), "No Pending amours",
						Toast.LENGTH_LONG).show();
			}

		}

		catch (Exception e) {

		}

	}

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private String[] data;
		private LayoutInflater inflater;
		public ImageLoader imageLoader;

		public LazyAdapter(Activity a, String[] listArr) {

			activity = a;
			data = listArr;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(userpendingamours.this);
		}

		public int getCount() {
			return data.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.userpendingamourstlist, null);
			
			LinearLayout headerrow = (LinearLayout) vi.findViewById(R.id.headerrow);

			TextView txtusername = (TextView) vi.findViewById(R.id.txtusername); // title

			ImageView thumb_image = (ImageView) vi
					.findViewById(R.id.userprofilepic); // thumb image

			TextView txtlastonline = (TextView) vi
					.findViewById(R.id.txtlastonline);

			// HashMap<String, String> song = new HashMap<String, String>();
			// song = data.get(position);

			// Setting all values in listview
			txtusername.setText(listArr[position]);
			txtlastonline.setText(listArrDate[position]);
			// artist.setText(song.get(CustomizedListView.KEY_ARTIST));
			// duration.setText(song.get(CustomizedListView.KEY_DURATION));
			imageLoader.DisplayImage(c.link.toString() + "/User_files/"
					+ listArrphoto[position], thumb_image);
			
			headerrow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(getApplicationContext(),
							userpendingamoursdetails.class);
					intent.putExtra("username", listArr[position]);
					intent.putExtra("userphoto", listArrphoto[position]);
					intent.putExtra("userid", listArrid[position]);
					startActivity(intent);
				}
			});
			return vi;
		}
	}
	
	public class LazyAdapter1 extends BaseAdapter {

		private Activity activity;
		private String[] data;
		private LayoutInflater inflater;
		public ImageLoader imageLoader;

		public LazyAdapter1(Activity a, String[] hlistArr) {

			activity = a;
			data = hlistArr;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(userpendingamours.this);
		}

		public int getCount() {
			return data.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.userpendingamourstlist, null);
			
			LinearLayout headerrow = (LinearLayout) vi.findViewById(R.id.headerrow);

			TextView txtusername = (TextView) vi.findViewById(R.id.txtusername); // title

			ImageView thumb_image = (ImageView) vi
					.findViewById(R.id.userprofilepic); // thumb image

			TextView txtlastonline = (TextView) vi
					.findViewById(R.id.txtlastonline);

			// HashMap<String, String> song = new HashMap<String, String>();
			// song = data.get(position);

			// Setting all values in listview
			txtusername.setText(hlistusername[position]);
			txtlastonline.setText("Dsf");
			// artist.setText(song.get(CustomizedListView.KEY_ARTIST));
			// duration.setText(song.get(CustomizedListView.KEY_DURATION));
			imageLoader.DisplayImage(c.link.toString() + "/User_files/"
					+ hlistuserphoto[position], thumb_image);
			
	headerrow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(getApplicationContext(),
							userpendingamoursdetails.class);
					intent.putExtra("username", hlistusername[position]);
					intent.putExtra("userphoto", hlistuserphoto[position]);
					intent.putExtra("userid", hlistArr[position]);
					startActivity(intent);
				}
			});
			return vi;
		}
	}

	public static Bitmap loadImageFromUrl(String url) {

		Bitmap bm;
		try {

			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();

			conn.connect();
			InputStream is = null;
			try {
				is = conn.getInputStream();
			} catch (IOException e) {
				return null;
			}

			BufferedInputStream bis = new BufferedInputStream(is);

			bm = BitmapFactory.decodeStream(bis);

			bis.close();
			is.close();

		} catch (IOException e) {
			return null;
		}

		return Bitmap.createScaledBitmap(bm, 60, 60, true);

	}
}