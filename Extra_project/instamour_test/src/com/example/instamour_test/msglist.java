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

import com.easy.facebook.android.data.To;


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

public class msglist extends Activity {
	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result, uid;

	function c = new function();
	
	private String[] listArr;
	private String[] listArrdate;
	private String[] listArrmsg;
	private String[] listArrphoto;
	private String[] listArruname;

	public ArrayList<String> ary_msg_id = new ArrayList<String>();
	public ArrayList<String> ary_msg_date = new ArrayList<String>();
	public ArrayList<String> ary_msg_msg = new ArrayList<String>();
	public ArrayList<String> ary_msg_photo = new ArrayList<String>();
	public ArrayList<String> ary_msg_uname = new ArrayList<String>();
	
	
	
	LazyAdapter adapter;
	

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msglist);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}



		listView = (ListView) findViewById(R.id.userList);

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(msglist.this,
								R.id.inner_content, width);
						startActivity(new Intent(msglist.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		
		
		fillmsg();

	}

	@SuppressLint("NewApi")
	public void fillmsg() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(msglist.this);
		String uid = preferences.getString("uid", "");
		Log.e("uid", uid);

	/*	result = c.getString(c.link.toString() + "/view_requests.php?uid="
				+ uid);
*/
		result = c.getString("http://www.instamour.com/APP_files/message_history.php?uid=9");
		
		try {
			JSONObject json_data = new JSONObject(result);
			// JSONObject root = json_data.getJSONObject("root");

			Log.e("result", json_data.toString());
			String root = json_data.getString("root");

			Log.e("root", root.toString());

			if (root != "null") {
				JSONArray json_data1 = new JSONArray(root);

				Log.e("A", root.toString());
				
				Log.e("r",String.valueOf( json_data1.length()));

				for (int ii = 0; ii < json_data1.length(); ii++) {
					JSONObject root1 = json_data1.getJSONObject(ii);
					// json_data = json_data1.getJSONObject(ii);
					Log.e("B", root1.toString());
					String msgdata = root1.getString("Message");

					Log.e("msgdata",msgdata);
					JSONObject obj = new JSONObject(msgdata);

					String message_id = obj.getString("message_id");
					String sender = obj.getString("sender");
					String reciever = obj.getString("reciever");
					String date = obj.getString("date");
					String message = obj.getString("message");
					String viewed = obj.getString("viewed");
					String photo = obj.getString("photo");
					String uname = obj.getString("uname");
					

					ary_msg_id.add(message_id);
					ary_msg_date.add(date);
					ary_msg_msg.add(message);
					ary_msg_photo.add(photo);
					ary_msg_uname.add(uname);
					Log.e("msg",message);
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					
					

				}

				listArr = new String[ary_msg_id.size()];
				listArr = ary_msg_id.toArray(listArr);
				
				listArrdate = new String[ary_msg_date.size()];
				listArrdate = ary_msg_date.toArray(listArrdate);
				
				listArrphoto = new String[ary_msg_photo.size()];
				listArrphoto = ary_msg_photo.toArray(listArrphoto);
				
				listArrmsg = new String[ary_msg_msg.size()];
				listArrmsg = ary_msg_msg.toArray(listArrmsg);
				
				listArruname = new String[ary_msg_uname.size()];
				listArruname = ary_msg_uname.toArray(listArruname);

				

				adapter = new LazyAdapter(msglist.this, listArr);
				listView.setAdapter(adapter);

			} else {
				Toast.makeText(getApplicationContext(), "No Messages",
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
			imageLoader = new ImageLoader(msglist.this);
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
				
				Toast.makeText(getApplicationContext(), listArr[position],Toast.LENGTH_LONG).show();
				
				vi = inflater.inflate(R.layout.msg, null);
			
			LinearLayout headerrow = (LinearLayout) vi.findViewById(R.id.headerrow);

			TextView txtmsg = (TextView) vi.findViewById(R.id.txtmsg); // title
			TextView txtusername = (TextView) vi.findViewById(R.id.txtusername); // title

			ImageView thumb_image = (ImageView) vi
					.findViewById(R.id.userprofilepic); // thumb image
			imageLoader.DisplayImage(c.link.toString() + "/User_files/"
					+ listArrphoto[position], thumb_image);

			TextView txtlastonline = (TextView) vi
					.findViewById(R.id.txtlastonline);

			txtmsg.setText(listArrmsg[position]);
			txtlastonline.setText(listArrdate[position]);
			txtusername.setText(listArruname[position]);
			
			return vi;
		}
	}
	
}