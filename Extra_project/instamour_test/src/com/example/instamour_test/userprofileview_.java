package com.example.instamour_test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class userprofileview_ extends Activity {

	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath = "";
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	String uid;
	
	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofileview_);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(userprofileview_.this,
								R.id.inner_content, width);
						startActivity(new Intent(userprofileview_.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		 setuserstyle();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(userprofileview_.this);

		TextView txtheadtext = (TextView) findViewById(R.id.txtusername);
		String uname = preferences.getString("uname", "");
		
		uid = preferences.getString("uid", "");
		txtheadtext.setText(uname);

		TextView txtaboutme = (TextView) findViewById(R.id.txtaboutme);
		String about_me = preferences.getString("about_me", "");
		txtaboutme.setText(about_me);

		ImageLoader imageLoader;
		imageLoader = new ImageLoader(userprofileview_.this);
		ImageView edit_uservideopic = (ImageView) findViewById(R.id.edit_uservideopic);
		String photo = preferences.getString("photo", "");
		imageLoader.DisplayImage(
				"http://www.instamour.com/APP_files/User_files/" + photo,
				edit_uservideopic);

	}
	
	private void setuserstyle()
	{
		 ImageLoader imageLoader; 
		  imageLoader=new ImageLoader(userprofileview_.this);
		TextView txtaboutme = (TextView) findViewById(R.id.txtaboutme1);
		TextView txtheadtext = (TextView) findViewById(R.id.txtusername);
		TextView txteditphoto = (TextView) findViewById(R.id.txteditphoto);
		txteditphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);
				
				
			}
		});
		TextView txteditaboutme = (TextView) findViewById(R.id.txteditaboutme);
		ImageView imgeditaboutme = (ImageView)findViewById(R.id.img_editaboutme);
		ImageView imgeditphoto = (ImageView)findViewById(R.id.img_editphoto);
		LinearLayout imguserback = (LinearLayout)findViewById(R.id.img_userback);
		
		
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(userprofileview_.this);
		String gender = preferences.getString("gender", "");
		
		
		
		if(gender.equalsIgnoreCase("male"))
		{
			
			txtaboutme.setTextColor(getResources().getColor(R.color.usermaletxtcolor));
			txtheadtext.setTextColor(getResources().getColor(R.color.usermaletxtcolor));
			txteditphoto.setTextColor(getResources().getColor(R.color.usermaletxtcolor));
			txteditaboutme.setTextColor(getResources().getColor(R.color.usermaletxtcolor));
			imgeditaboutme.setImageDrawable(getResources().getDrawable(R.drawable.male_blueeditaboutme));
			imgeditphoto.setImageDrawable(getResources().getDrawable(R.drawable.male_bluecamera));
			imguserback.setBackgroundDrawable(getResources().getDrawable(R.drawable.male_bluebar));
		}
		if(gender.equalsIgnoreCase("female"))
		{
			txtaboutme.setTextColor(getResources().getColor(R.color.userfemaletxtcolor));
			txtheadtext.setTextColor(getResources().getColor(R.color.userfemaletxtcolor));
			txteditphoto.setTextColor(getResources().getColor(R.color.userfemaletxtcolor));
			txteditaboutme.setTextColor(getResources().getColor(R.color.userfemaletxtcolor));
			imgeditaboutme.setImageDrawable(getResources().getDrawable(R.drawable.female_rededitaboutme));
			imgeditphoto.setImageDrawable(getResources().getDrawable(R.drawable.female_redcamera));
			
			imguserback.setBackgroundDrawable(getResources().getDrawable(R.drawable.female_redbox));
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				
				  Toast.makeText(getApplicationContext(), selectedImagePath,
				  Toast.LENGTH_LONG).show();
				 Log.e("a", selectedImagePath);
				// img.setVisibility(0);
				 ImageView edit_uservideopic = (ImageView) findViewById(R.id.edit_uservideopic);
				 edit_uservideopic.setImageURI(selectedImageUri);
				
				new AsyncAction().execute(null, null, null);
				
				// ss = selectedImagePath.replace("/mnt", "");

			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				
				updateimage();
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

		

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(userprofileview_.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	
	private void updateimage()
	{
		try
		{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		

		HttpPost httppost = new HttpPost(
				"http://www.instamour.com/APP_files/image_edit.php");
		File file = new File(selectedImagePath);

		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			FormBodyPart userFile = new FormBodyPart("photo", new FileBody(
					file, "image/*"));
			// userFile.addField(Environment.getExternalStorageDirectory()+"/a.png","NEWNAMEOFILE.png");
			mpEntity.addPart(userFile);
			// mpEntity.addPart("photo", new FileBody(file, "image/png"));
			mpEntity.addPart("uid", new StringBody(uid));
			

		} catch (Exception e) {

		}

		httppost.setEntity(mpEntity);
		HttpResponse response;
		try {
			// Log.d(TAG, "UPLOAD: about to execute");
			response = httpclient.execute(httppost);
			// /Log.d(TAG, "UPLOAD: executed");
			HttpEntity resEntity = response.getEntity();
			is = resEntity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		catch(OutOfMemoryError e)
		{
			Toast.makeText(getApplicationContext(), "Low memory space", Toast.LENGTH_LONG).show();
		}
	}

}