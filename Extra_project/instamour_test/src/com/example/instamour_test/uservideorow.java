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

public class uservideorow extends Activity {
	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	function c = new function();
	LazyAdapter1 adapter;
	private String[] listArr;
	private String[] listArrphoto;

	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_name = new ArrayList<String>();

	private Button btn_videorowback;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uservideorow);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		Bundle getdate = getIntent().getExtras();
		
		String username = getdate.getString("username");

		TextView txtusername = (TextView) findViewById(R.id.txtusername);
		txtusername.setText(username);
		
		listView = (ListView) findViewById(R.id.userList);

		
		//new AsyncAction().execute(null, null, null);
		filllistdata();
		adapter = new LazyAdapter1(uservideorow.this, listArr);
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

			adapter = new LazyAdapter1(uservideorow.this, listArr);
			listView.setAdapter(adapter);
			/*MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
					listuserright);
			listView.setAdapter(adapter);*/
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(uservideorow.this);
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
				.getDefaultSharedPreferences(uservideorow.this);
	String	uid = preferences.getString("uid", "");
		Log.e("uid",uid);

		
		try {
		

				Bundle getdate = getIntent().getExtras();
				String photo =getdate.getString("userphoto");
				String username = getdate.getString("username");
				Log.e("photo", photo);
				ary_user_name.add(username);
				ary_user_photo.add(photo);
				
			//	Toast.makeText(getApplicationContext(), photo, Toast.LENGTH_LONG).show();
				

			//}

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
	        imageLoader=new ImageLoader(uservideorow.this);
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
                try
                {
                 is= conn.getInputStream();  
                }catch(IOException e)
                {
                     return null;
                }

                BufferedInputStream bis = new BufferedInputStream(is);  

                bm = BitmapFactory.decodeStream(bis);

                bis.close();  
                is.close();  

           } catch (IOException e) {  
            return null;
           }  

        return  Bitmap.createScaledBitmap(bm,60,60,true);


    }
}