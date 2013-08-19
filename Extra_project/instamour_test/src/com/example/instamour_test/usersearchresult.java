package com.example.instamour_test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.provider.MediaStore.Video;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class usersearchresult extends Activity {

	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	ArrayList<String> aryTicket = new ArrayList<String>();

	private String[] listArr;
	private String[] listArrVideo;
	private String[] listuserleft;
	private String[] listuserright;
	private String[] listuserid;
	private String[] list_videouserid;
	private String[] listuservideo1;
	private String[] list_uservideo1;
	private String[] listuservideo2;
	private String[] list_uservideo2;
	private String[] listuservideo3;
	private String[] list_uservideo3;
	private String[] listuservideo4;
	private String[] list_uservideo4;
	private String[] listuseraboutme;
	private String[] listuserheight;
	private String[] listuseridentity;
	private String[] listuserbodytype;
	private String[] listuserlookingfor;
	private String[] listusercity;
	private String[] listusername;
	private String[] listusernameleft;
	private String[] listusernameright;

	public ArrayList<String> ary_user_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_video_photo = new ArrayList<String>();
	public ArrayList<String> ary_user_left = new ArrayList<String>();
	public ArrayList<String> ary_user_right = new ArrayList<String>();
	public ArrayList<String> ary_user_id = new ArrayList<String>();
	public ArrayList<String> ary_user_video_id = new ArrayList<String>();
	public ArrayList<String> ary_user_video1 = new ArrayList<String>();
	public ArrayList<String> ary_user_video_video1 = new ArrayList<String>();
	public ArrayList<String> ary_user_video2 = new ArrayList<String>();
	public ArrayList<String> ary_user_video_video2 = new ArrayList<String>();
	public ArrayList<String> ary_user_video3 = new ArrayList<String>();
	public ArrayList<String> ary_user_video_video3 = new ArrayList<String>();
	public ArrayList<String> ary_user_video4 = new ArrayList<String>();
	public ArrayList<String> ary_user_video_video4 = new ArrayList<String>();
	public ArrayList<String> ary_user_aboutme = new ArrayList<String>();
	public ArrayList<String> ary_user_height = new ArrayList<String>();
	public ArrayList<String> ary_user_identity = new ArrayList<String>();
	public ArrayList<String> ary_user_bodytype = new ArrayList<String>();
	public ArrayList<String> ary_user_lookingfor = new ArrayList<String>();
	public ArrayList<String> ary_user_city = new ArrayList<String>();
	public ArrayList<String> ary_user_name = new ArrayList<String>();
	public ArrayList<String> ary_user_name_left = new ArrayList<String>();
	public ArrayList<String> ary_user_name_right = new ArrayList<String>();

	function c = new function();

	private ImageView btnuserview;

	private ImageView btnuservideo;
	
	
	LazyAdapter1 adaptervideo;
	
	String uid;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usersearchresult);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		listView = (ListView) findViewById(R.id.userList);

		//btnuserview = (ImageView) findViewById(R.id.btnuserview);

		//btnuservideo = (ImageView) findViewById(R.id.btnuservideo);

		 new AsyncAction().execute(null, null, null);

		//filllistdata();
		// filllistvideodata();

		/*adapter = new LazyAdapter(SampleActivity.this, listuserright);
		listView.setAdapter(adapter);*/

		

		

		
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

			adaptervideo = new LazyAdapter1(usersearchresult.this, listArr);
			listView.setAdapter(adaptervideo);
			/*MyArrayAdapter adapter = new MyArrayAdapter(SampleActivity.this,
					listuserright);
			listView.setAdapter(adapter);*/
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(usersearchresult.this);
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

		ary_user_id.clear();
		ary_user_video1.clear();
		ary_user_video2.clear();
		ary_user_video3.clear();
		ary_user_video4.clear();
		ary_user_aboutme.clear();
		ary_user_height.clear();
		ary_user_identity.clear();
		ary_user_bodytype.clear();
		ary_user_lookingfor.clear();
		ary_user_city.clear();
		ary_user_name.clear();

		listuserleft = new String[0];
		listuserright = new String[0];
		listuserid = new String[0];
		listuservideo1 = new String[0];
		listuservideo2 = new String[0];
		listuservideo3 = new String[0];
		listuservideo4 = new String[0];
		listuseraboutme = new String[0];
		listuserheight = new String[0];
		listuseridentity = new String[0];
		listuserbodytype = new String[0];
		listuserlookingfor = new String[0];
		listusercity = new String[0];
		listusername = new String[0];

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		
		Bundle data = getIntent().getExtras();
		String user_gender= data.getString("user_gender");
		String user_smoker= data.getString("user_smoker");
		String user_video= data.getString("user_video");
		String user_relation= data.getString("user_relation");
		String user_ethnicity= data.getString("user_ethnicity");
		String user_age1= data.getString("user_age1");
		String user_age2= data.getString("user_age2");
		
		
		
		
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(usersearchresult.this);
		uid = preferences.getString("uid", "");
		Log.e("uid",uid);
		String lat = preferences.getString("lat", "");
		String lng = preferences.getString("lng", "");
		result = c
				.getString(c.link.toString() +"/search_user.php?uid=" +
						uid +
						"&gender=" + user_gender +
						"&smoker=" + user_smoker +
						"&looking_for=" + user_relation +
						"&video=" + user_video +
						"&ethnicity=" + user_ethnicity +
						"&minage=" + user_age1 +
						"&maxage=" + user_age2
						
						);
		
		Log.e("result", result);
		
		Log.e("result", c.link.toString() +"/search_user.php?uid=" +
				uid +
				"&gender=" + user_gender +
				"&smoker=" + user_smoker +
				"&looking_for=" + user_relation +
				"&video=" + user_video +
				"&ethnicity=" + user_ethnicity +
				"&minage=" + user_age1 +
				"&maxage=" + user_age2);

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
				String userproile_pic = root1.getString("Daters");
				Log.e("photo", userproile_pic.toString());

				JSONObject obj = new JSONObject(userproile_pic);

				String photo = obj.getString("photo");
				String uname = obj.getString("uname");
				String id = obj.getString("uid");
				String video1 = obj.getString("video1");
				String video2 = obj.getString("video2");
				String video3 = obj.getString("video3");
				String video4 = obj.getString("video4");
				String aboutme = obj.getString("about_me");
				String height = obj.getString("height");
				String identity = obj.getString("identity");
				String body_type = obj.getString("body_type");
				String looking_for = obj.getString("looking_for");
				String city = obj.getString("city");

				// Height/ Identity/ Body type/ Looking for/ Location
				/*
				 * Toast.makeText(getApplicationContext(), photo,
				 * Toast.LENGTH_LONG) .show();
				 */

				/*
				 * Log.e("video1", video1); Log.e("video2", video2);
				 * Log.e("video3", video3); Log.e("video4", video4);
				 */

				ary_user_photo.add(photo);
				ary_user_name.add(uname);
				ary_user_id.add(id);
				ary_user_video1.add(video1);
				ary_user_video2.add(video2);
				ary_user_video3.add(video3);
				ary_user_video4.add(video4);
				ary_user_aboutme.add(aboutme);
				ary_user_height.add(height);
				ary_user_identity.add(identity);
				ary_user_bodytype.add(body_type);
				ary_user_lookingfor.add(looking_for);
				ary_user_city.add(city);

				/*
				 * Toast.makeText(getApplicationContext(), id,
				 * Toast.LENGTH_LONG) .show();
				 */

				if (ii % 2 == 0) {
					/*
					 * Toast.makeText(getApplicationContext(), ii + "  " +
					 * photo, Toast.LENGTH_LONG).show();
					 */
					//Toast.makeText(getApplicationContext(),"1 " + String.valueOf( id)+" photo name" + photo, Toast.LENGTH_LONG).show();
					ary_user_left.add(photo);
					ary_user_name_left.add(uname);
				} else {
					/*
					 * Toast.makeText(getApplicationContext(), ii + "  " +
					 * photo, Toast.LENGTH_LONG).show();
					 */
					//Toast.makeText(getApplicationContext(),"2 " + String.valueOf( id)+" photo name" + photo, Toast.LENGTH_LONG).show();
					ary_user_right.add(photo);
					ary_user_name_right.add(uname);
				}

			}

			listArr = new String[ary_user_photo.size()];
			listArr = ary_user_photo.toArray(listArr);

			listuserleft = new String[ary_user_left.size()];
			listuserleft = ary_user_left.toArray(listuserleft);

			listuserright = new String[ary_user_right.size()];
			listuserright = ary_user_right.toArray(listuserright);

			listuserid = new String[ary_user_id.size()];
			listuserid = ary_user_id.toArray(listuserid);
			
			listusername = new String[ary_user_name.size()];
			listusername = ary_user_name.toArray(listusername);
			
			listusernameleft = new String[ary_user_name_left.size()];
			listusernameleft = ary_user_name_left.toArray(listusernameleft);
			
			listusernameright = new String[ary_user_name_right.size()];
			listusernameright = ary_user_name_right.toArray(listusernameright);
			
			listuservideo1 = new String[ary_user_video1.size()];
			listuservideo1 = ary_user_video1.toArray(listuservideo1);

			listuservideo2 = new String[ary_user_video2.size()];
			listuservideo2 = ary_user_video2.toArray(listuservideo2);

			listuservideo3 = new String[ary_user_video3.size()];
			listuservideo3 = ary_user_video3.toArray(listuservideo3);

			listuservideo4 = new String[ary_user_video4.size()];
			listuservideo4 = ary_user_video4.toArray(listuservideo4);

			listuseraboutme = new String[ary_user_aboutme.size()];
			listuseraboutme = ary_user_aboutme.toArray(listuseraboutme);

			listuserheight = new String[ary_user_height.size()];
			listuserheight = ary_user_height.toArray(listuserheight);

			listuseridentity = new String[ary_user_identity.size()];
			listuseridentity = ary_user_identity.toArray(listuseridentity);

			listuserbodytype = new String[ary_user_bodytype.size()];
			listuserbodytype = ary_user_bodytype.toArray(listuserbodytype);

			listuserlookingfor = new String[ary_user_lookingfor.size()];
			listuserlookingfor = ary_user_lookingfor
					.toArray(listuserlookingfor);

			listusercity = new String[ary_user_city.size()];
			listusercity = ary_user_city.toArray(listusercity);

		}

		catch (Exception e) {

		}

	}

	

	static class ViewHolder {

		ImageView userleft;
		ImageView userright;
	}


public class LazyAdapter1 extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private  LayoutInflater inflater;
    public ImageLoader imageLoader; 
    
    final List<String> videoPathes = new ArrayList<String>();
	int videocount = -1;
	int totalvideo=-1;
 
    
    public LazyAdapter1(Activity a, String[] listArr) {
       
    	activity = a;
        data=listArr;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(usersearchresult.this);
        
        
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
        	//totalvideo=-1;
            vi = inflater.inflate(R.layout.uservideoview, null);

        TextView txtuname = (TextView) vi.findViewById(R.id.video_txtuser);
        txtuname.setText(listusername[position]);
        
        txtuname.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 listView.smoothScrollToPosition(5);
			}
		});
              
    	 final ImageView uservideopic = (ImageView) vi.findViewById(R.id.uservideopic);
        imageLoader.DisplayImage( c.link.toString() +"/User_files/"+listArr[position], uservideopic);
        
        String txtDesc = "";
		if (listuserheight[position].length() > 1) {
			txtDesc = listuserheight[position] + "/"
					+ listuseridentity[position] + "/"
					+ listuserbodytype[position] + "/"
					+ listuserlookingfor[position] + "/"
					+ listusercity[position];
			TextView txtextra = (TextView) vi.findViewById(R.id.txtdesc);
			txtextra.setText(txtDesc);
		} /*else {
			View namebar =  vi.findViewById(R.id.txtdesc);
			((LinearLayout) namebar.getParent()).removeView(namebar);
			
			
		}*/
        
		if (listuseraboutme[position].trim().length() > 1) {
			TextView txtaboutme = (TextView) vi
					.findViewById(R.id.txtaboutme);
			txtaboutme.setText(listuseraboutme[position]);
		} /*else {
			View namebar = vi.findViewById(R.id.txtaboutme);
			((LinearLayout) namebar.getParent()).removeView(namebar);

		
			namebar = vi.findViewById(R.id.txtaboutme1);
			((LinearLayout) namebar.getParent()).removeView(namebar);
		}*/
		
		 ImageView img_femalesendreq = (ImageView) vi.findViewById(R.id.img_femalesendreq);
		 Log.e("uid",uid);
		 Log.e("uid1",String.valueOf(listuserid[position]));
		 img_femalesendreq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sendreq(uid,String.valueOf(listuserid[position]));
				
			}
		});
		 
		 totalvideo=-1;
		
			if (listuservideo1[position].length() > 3) {
				
				videoPathes
						.add( c.link.toString() +"User_files/"
								+ listuservideo1[position]);
				totalvideo++;
			}
			if (listuservideo2[position].length() > 3) {
				
				videoPathes
						.add(c.link.toString() +"User_files/"
								+ listuservideo2[position]);
				totalvideo++;
			}
			if (listuservideo3[position].length() > 3) {
				
				videoPathes
						.add(c.link.toString() +"User_files/"
								+ listuservideo3[position]);
				totalvideo++;
			}
			if (listuservideo4[position].length() > 3) {
				
				videoPathes
						.add(c.link.toString() +"User_files/"
								+ listuservideo4[position]);
				totalvideo++;
			}
			 
			  final VideoView uservideoview = (VideoView) vi
						.findViewById(R.id.uservideoview);
		 
			ImageView videoplaybtn = (ImageView) vi.findViewById(R.id.videoplaybtn);
			videoplaybtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(totalvideo!=-1)
					{
					uservideoview.setVideoPath(videoPathes.get(totalvideo));
					uservideoview.start();
					uservideopic.setVisibility(View.GONE);
					// Toast.makeText(getApplicationContext(), String.valueOf( totalvideo), Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),  listuservideo1[position], Toast.LENGTH_LONG).show();
					//// Toast.makeText(getApplicationContext(),  listuservideo2[position], Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),  listuservideo3[position], Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),  listuservideo4[position], Toast.LENGTH_LONG).show();
					
					}
					else
					{
						Toast.makeText(getApplicationContext(), "No video Found", Toast.LENGTH_LONG).show();
					}
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


	
	@SuppressLint("NewApi")
	private void sendreq(String sender,String receiver)
	{
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		

		result = c.getString(c.link.toString() + "/send_friend_request.php?sender="+sender+"&receiver="+receiver);

		try {
			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			

			if (root.has("Result")) {
				JSONObject req = root.getJSONObject("Result");
				
				
				String result = req.getString("result");
				Log.e("result",result);
				
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				
			}
			else if (root.has("Request")) {
				JSONObject req = root.getJSONObject("Request");
				
				
				String result = req.getString("result");
				Log.e("result",result);
				
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				
			}
			
		

		} catch (Exception e) {

		}
	}
	

}
