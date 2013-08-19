package com.example.instamour_test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class userpendingamoursdetails extends Activity {
	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	function c = new function();
	LazyAdapter1 adapter;
	private String[] listArr;
	private String[] listArrphoto;

	String updateresult;
	
	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_name = new ArrayList<String>();
	
	String userid;

	private Button btn_videorowback;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userpendingamoursdetail);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		Bundle getdate = getIntent().getExtras();
		
		String username = getdate.getString("username");
		userid = getdate.getString("userid");

		TextView txtusername = (TextView) findViewById(R.id.txtusername);
		txtusername.setText(username);
		
		listView = (ListView) findViewById(R.id.userList);

		
		//new AsyncAction().execute(null, null, null);
		filllistdata();
		adapter = new LazyAdapter1(userpendingamoursdetails.this, listArr);
		listView.setAdapter(adapter);
		/*MyArrayAdapter adapter = new MyArrayAdapter(useryouramours.this,listArr);
		listView.setAdapter(adapter);*/
		
		//adapter = new LazyAdapter(userpendingamoursdetails.this, listArr);
		//listView.setAdapter(adapter);
		
		btn_videorowback = (Button) findViewById(R.id.btn_videorowback);
		btn_videorowback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
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

			adapter = new LazyAdapter1(userpendingamoursdetails.this, listArr);
			listView.setAdapter(adapter);
			/*MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
					listuserright);
			listView.setAdapter(adapter);*/
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(userpendingamoursdetails.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	
	
	@SuppressLint("NewApi")
	public void filllistdata() {

		

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(userpendingamoursdetails.this);
	String	uid = preferences.getString("uid", "");
		Log.e("uid",uid);

		
		try {
		

				Bundle getdate = getIntent().getExtras();
				String photo =getdate.getString("userphoto");
				String username = getdate.getString("username");
				Log.e("photo", photo);
				ary_user_name.add(username);
				ary_user_photo.add(photo);
				
				Toast.makeText(getApplicationContext(), photo, Toast.LENGTH_LONG).show();


			listArr = new String[ary_user_name.size()];
			listArr = ary_user_name.toArray(listArr);
			
			listArrphoto = new String[ary_user_photo.size()];
			listArrphoto = ary_user_photo.toArray(listArrphoto);

		}

		catch (Exception e) {

		}

	}

	public class LazyAdapter1 extends BaseAdapter {
	    
	    private Activity activity;
	    private String[] data;
	    private  LayoutInflater inflater;
	    public ImageLoader imageLoader; 
	 
	    
	    public LazyAdapter1(Activity a, String[] listArr) {
	       
	    	activity = a;
	        data=listArr;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader=new ImageLoader(userpendingamoursdetails.this);
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
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.uservideoview, null);

	        TextView txtuname = (TextView) vi.findViewById(R.id.video_txtuser);
	        txtuname.setText(listArr[position]);
	        
	       ImageView uservideopic = (ImageView) vi.findViewById(R.id.uservideopic);
	       imageLoader.DisplayImage("http://www.instamour.com/APP_files/User_files/"+listArrphoto[position], uservideopic);
	       
	       ImageView img_femalesendreq = (ImageView) vi.findViewById(R.id.img_femalesendreq);
	       img_femalesendreq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new AsyncAction1().execute(null, null, null);
			}
		});
	       
	    
	        return vi;
	    }
	}
	
	@SuppressLint("NewApi")
	private class AsyncAction1 extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				 acceptFriend();
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
			pd = new ProgressDialog(userpendingamoursdetails.this);
			pd.setMessage("accept request .." +
					"");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	private void acceptFriend()
	{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				c.link.toString()+"/accept_reject_requests.php");

		
		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			
			
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(userpendingamoursdetails.this);
		String	uid = preferences.getString("uid", "");
			Log.e("uid",uid);
			
			mpEntity.addPart("uid", new StringBody(uid));
			mpEntity.addPart("opponentID", new StringBody(userid));
			mpEntity.addPart("status", new StringBody("accept"));
			

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

			if (root.has("Status")) {

				JSONObject reg = root.getJSONObject("Status");
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