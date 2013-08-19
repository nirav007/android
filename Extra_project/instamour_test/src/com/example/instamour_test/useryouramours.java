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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;


//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class useryouramours extends Activity {
	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	function c = new function();
	LazyAdapter adapter;
	private String[] listArr;
	private String[] listArrphoto;
	private String[] listArruid;

	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_name = new ArrayList<String>();
	public ArrayList<String> ary_user_id = new ArrayList<String>();

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youramours);
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
						SlideoutActivity.prepare(useryouramours.this,
								R.id.inner_content, width);
						startActivity(new Intent(useryouramours.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		//new AsyncAction().execute(null, null, null);
		filllistdata();
		/*MyArrayAdapter adapter = new MyArrayAdapter(useryouramours.this,listArr);
		listView.setAdapter(adapter);*/
		
		adapter = new LazyAdapter(useryouramours.this, listArr);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent(getApplicationContext(),userpendingamoursdetails.class);
				intent.putExtra("username", listArr[arg2]);
				intent.putExtra("userphoto", listArrphoto[arg2]);
				intent.putExtra("userid", listArruid[arg2]);
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

			adapter = new LazyAdapter(useryouramours.this, listArr);
			listView.setAdapter(adapter);
			/*MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
					listuserright);
			listView.setAdapter(adapter);*/
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(useryouramours.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	@SuppressLint("NewApi")
	public void filllistdata() {

		// ary_user_photo.clear();
		// listArr = new String[0];

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(useryouramours.this);
	String	uid = preferences.getString("uid", "");
		Log.e("uid",uid);

		result = c
				.getString("http://www.instamour.com/APP_files/view_friends.php?uid="+uid);

		try {
			JSONObject json_data = new JSONObject(result);
			// JSONObject root = json_data.getJSONObject("root");

			Log.e("result", json_data.toString());
			String root = json_data.getString("root");

			JSONArray json_data1 = new JSONArray(root);

			Log.e("A", root.toString());

			for (int ii = 0; ii < json_data1.length(); ii++) {
				JSONObject root1 = json_data1.getJSONObject(ii);
				// json_data = json_data1.getJSONObject(ii);
				Log.e("B", root1.toString());
				String userproile_pic = root1.getString("Friends");
				Log.e("photo", userproile_pic.toString());

				JSONObject obj = new JSONObject(userproile_pic);

				String photo = obj.getString("photo");
				String uname = obj.getString("uname");
				String uid1 = obj.getString("uid");

				ary_user_name.add(uname);
				ary_user_photo.add(photo);
				ary_user_id.add(uid1);

			}

			listArr = new String[ary_user_name.size()];
			listArr = ary_user_name.toArray(listArr);
			
			listArrphoto = new String[ary_user_photo.size()];
			listArrphoto = ary_user_photo.toArray(listArrphoto);
			
			listArruid = new String[ary_user_id.size()];
			listArruid = ary_user_id.toArray(listArruid);

		}

		catch (Exception e) {

		}

	}

public class LazyAdapter extends BaseAdapter {
	    
	    private Activity activity;
	    private String[] data;
	    private  LayoutInflater inflater;
	    public ImageLoader imageLoader; 
	 
	    
	    public LazyAdapter(Activity a, String[] listArr) {
	       
	    	activity = a;
	        data=listArr;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader=new ImageLoader(useryouramours.this);
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
	    
		
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.useryouramourstlist, null);

	        TextView txtusername = (TextView)vi.findViewById(R.id.txtusername); // title
	       
	        ImageView thumb_image=(ImageView)vi.findViewById(R.id.userprofilepic); // thumb image
	        
	        //HashMap<String, String> song = new HashMap<String, String>();
	        //song = data.get(position);
	        
	       
	        
	        // Setting all values in listview
	        txtusername.setText(listArr[position]);
	        //artist.setText(song.get(CustomizedListView.KEY_ARTIST));
	        //duration.setText(song.get(CustomizedListView.KEY_DURATION));
	        imageLoader.DisplayImage("http://www.instamour.com/APP_files/User_files/"+listArrphoto[position], thumb_image);
	        return vi;
	    }
	}

	/*public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.useryouramourstlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.useryouramourstlist, null,
					true);

			TextView txtusername = (TextView) view
					.findViewById(R.id.txtusername);
			txtusername.setText(listArr[position]);
			
			final ImageView userprofilepic = (ImageView) view
					.findViewById(R.id.userprofilepic);
			
			if(userprofilepic.getTag() != null) {
				userprofilepic.setImageBitmap((Bitmap)view.getTag());
			} else {
			    new ImageLoader().execute(view, "http://www.instamour.com/APP_files/User_files/"+listArrphoto[position]);
			}
			
			/*try
			{
				
				new ImageLoader().execute(view, "http://www.instamour.com/APP_files/User_files/"+listArrphoto[position]);
				//userprofilepic.setImageBitmap(loadImageFromUrl("http://www.instamour.com/APP_files/User_files/"+listArrphoto[position]));
			}
			catch(Exception e)
			{
				
			}

			return view;
		}
	}*/
	/*public class ImageLoader extends AsyncTask<Object, String, Bitmap> {

	    private View view;
	    private Bitmap bm = null;

	    @Override
	    protected Bitmap doInBackground(Object... parameters) {

	        // Get the passed arguments here
	        view = (View) parameters[0];
	        String uri = (String)parameters[1];

	       
	        bm = loadImageFromUrl(uri);

	        return bm;
	        //return bitmap;
	    }

	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        if (bitmap != null && view != null) {
	            ImageView albumArt = (ImageView) view.findViewById(R.id.userprofilepic);
	            albumArt.setImageBitmap(bitmap);
	            albumArt.setTag(bitmap);
	        }
	    }
	} */
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