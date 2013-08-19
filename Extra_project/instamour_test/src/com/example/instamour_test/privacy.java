package com.example.instamour_test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.easy.facebook.android.data.To;
import com.example.instamour_test.SampleActivity.LazyAdapter;
import com.example.instamour_test.SampleActivity.LazyAdapter1;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;



//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class privacy extends Activity {
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	
	static ListView listView;
	private String[] listArr = { "Enable Chat", "Enable Video Chat" , "Disable Account"};
	
	
	function c = new function();

	String uid;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpsupport);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		listView = (ListView) findViewById(R.id.userList);
		
		TextView txtheadtext = (TextView) findViewById(R.id.txtheadtext);
		txtheadtext.setText("Privacy" );

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(privacy.this,
								R.id.inner_content, width);
						startActivity(new Intent(privacy.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		MyArrayAdapter adapter = new MyArrayAdapter(privacy.this, listArr);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(privacy.this);
				uid = preferences.getString("uid", "");
				Log.e("uid",uid);
				
				/*if(arg2==0)
				{
					usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&chat");
				}
				else if(arg2==1)
				{
					usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&video_chat");
				}
				else if(arg2==2)
				{
					usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&disable_account");
				}*/

			}
		});

	}
	
	@SuppressLint("NewApi")
	public void usersetting(String url)
	{
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		
		
	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		result = c.getString(url);

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(privacy.this);
		
		SharedPreferences.Editor editor = preferences.edit();
		
		try {
			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			Log.e("root",root.toString());

			if (root.has("Setting")) {

				
				JSONObject login = root.getJSONObject("Setting");
				String chat = login.getString("chat");
				String video_chat = login.getString("video_chat");
				String disable_account = login.getString("disable_account");
				
				
				editor.putString("chat", String.valueOf(chat));
				editor.putString("video_chat", String.valueOf(video_chat));
				editor.putString("disable_account", String.valueOf(disable_account));
				
			
				editor.commit();
				
			}
			else if  (root.has("Result")) {			
				
				
			}
			
		

		} catch (Exception e) {

		}
	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
		final List<String> videoPathes = new ArrayList<String>();
		int videocount = 0, totalvideo;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.userprivacytlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.userprivacytlist, null,
					true);
			view.performClick();
			
			TextView txthelp = (TextView) view.findViewById(R.id.txthelp);
			txthelp.setText(listArr[position]);
		
			ImageView imgheart = (ImageView) view.findViewById(R.id.imgarrow);
			
			imgheart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(privacy.this);
					uid = preferences.getString("uid", "");
					Log.e("uid",uid);
					
					if(position==0)
					{
						usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&chat=");
						MyArrayAdapter adapter = new MyArrayAdapter(privacy.this, listArr);
						listView.setAdapter(adapter);
					}
					
					if(position==1)
					{
						usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&video_chat=");
						MyArrayAdapter adapter = new MyArrayAdapter(privacy.this, listArr);
						listView.setAdapter(adapter);
					}
					
					if(position==2)
					{
						usersetting(c.link.toString()+"/update_usersetting.php?uid="+uid+"&disable_account=");
						MyArrayAdapter adapter = new MyArrayAdapter(privacy.this, listArr);
						listView.setAdapter(adapter);
					}
				}
			});
			
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(privacy.this);
			String chat = preferences.getString("chat", "");
			String video_chat = preferences.getString("video_chat", "");
			String disable_account = preferences.getString("disable_account", "");
			
			if(position==0)
			{
				if(chat.equals("0"))
				{
					imgheart.setImageResource(R.drawable.privacy_heart);
				}
				else
				{
					imgheart.setImageResource(R.drawable.privacy_heart_disable);
				}
			}
			
			if(position==1)
			{
				if(video_chat.equals("0"))
				{
					imgheart.setImageResource(R.drawable.privacy_heart);
				}
				else
				{
					imgheart.setImageResource(R.drawable.privacy_heart_disable);
				}
			}
			
			if(position==2)
			{
				if(disable_account.equals("0"))
				{
					imgheart.setImageResource(R.drawable.privacy_heart);
				}
				else
				{
					imgheart.setImageResource(R.drawable.privacy_heart_disable);
				}
			}
			
			

			return view;
		}
	}

}
