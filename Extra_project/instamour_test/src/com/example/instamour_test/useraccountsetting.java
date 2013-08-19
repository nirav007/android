package com.example.instamour_test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class useraccountsetting extends Activity {
	static ListView listView;
	private String[] listArr = { "Change videos", "Edit profile",
			"Email address", "Password", "Update location",
			"Manage Notifications" };
	private ImageView imguserprofile;
	private TextView txtchaneimage;

	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath = "";

	private InputStream is;
	private StringBuilder sb;
	private String result;
	String uid;
	function c = new function();
	
	String passResult,locationResult;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountsetting);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		listView = (ListView) findViewById(R.id.userList);

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(useraccountsetting.this);
		uid = preferences.getString("uid", "");
		Log.e("uid", uid);

		TextView txtheadtext = (TextView) findViewById(R.id.txtheadtext);
		txtheadtext.setText("Account Settings");

		imguserprofile = (ImageView) findViewById(R.id.imguserchangeprofile);
		txtchaneimage = (TextView) findViewById(R.id.txtchangeimg);

		txtchaneimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncAction().execute(null, null, null);
				// updateimage();
			}
		});

		imguserprofile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);

			}
		});

		MyArrayAdapter adapter = new MyArrayAdapter(useraccountsetting.this,
				listArr);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if(arg2==1)
				{
					Intent intent = new Intent(getApplicationContext(), editprofile.class);
					startActivity(intent);
				}
				
				
				if (arg2 == 2) {
				
					final AlertDialog.Builder alert = new AlertDialog.Builder(useraccountsetting.this);
				    final EditText input = new EditText(useraccountsetting.this);
				    alert.setView(input);
				    alert.setTitle("Update Email");
				    alert.setMessage("Enter Your New Email");
				    alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            String value = input.getText().toString().trim();
				            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
				            new AsyncActionEmail().execute(value, null,null);
				        }
				    });

				    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            dialog.cancel();
				        }
				    });
				    alert.show();  
				}
				
				if (arg2 == 3) {
					
					final AlertDialog.Builder alert = new AlertDialog.Builder(useraccountsetting.this);
				    /*final EditText oldpass = new EditText(useraccountsetting.this);
				    alert.setView(oldpass);
				    final EditText newpass = new EditText(useraccountsetting.this);
				    alert.setView(newpass);*/
				    alert.setTitle("Update Password");
				    //alert.setMessage("Enter Your New Email");
				    
				    
				    final LinearLayout layout = new LinearLayout(useraccountsetting.this);
				    layout.setOrientation(LinearLayout.VERTICAL);

				    final EditText oldpass = new EditText(useraccountsetting.this);
				    oldpass.setHint("Old pass");
				    final EditText newpass = new EditText(useraccountsetting.this);
				    newpass.setHint("new password");
				    
				    layout.addView(oldpass);
				    layout.addView(newpass);

				    alert.setView(layout);
				    
				    alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            String opass = oldpass.getText().toString().trim();
				            String npass = newpass.getText().toString().trim();
				            //Toast.makeText(getApplicationContext(), opass, Toast.LENGTH_SHORT).show();
				            new AsyncActionPass().execute(opass, npass,null);
				            //updatepass(opass, npass);
				        }
				    });

				    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            dialog.cancel();
				        }
				    });
				    alert.show();  
				}
				
				if(arg2==4)
				{
					  new AsyncActionLocation().execute(null, null,null);
					updatelocation();
				}
			}
		});

	}

	@SuppressLint("NewApi")
	private void updatelocation()
	{
		
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
		
		
		
		GPSTracker gps;
		double latitude = 0;
		double longitude = 0;
		gps = new GPSTracker(useraccountsetting.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			
			// \n is for new line
			try {
				Geocoder gcd = new Geocoder(getApplicationContext(),
						Locale.getDefault());
				List<Address> addresses = gcd.getFromLocation(latitude,
						longitude, 1);
				if (addresses.size() > 0)
					System.out.println(addresses.get(0).getLocality());
				/*
				 * Toast.makeText(getApplicationContext(),
				 * addresses.get(0).getLocality(), Toast.LENGTH_LONG) .show();
				 */
				//signup_city = addresses.get(0).getLocality();
			} catch (Exception e) {

			}

			/*
			 * Toast.makeText( getApplicationContext(),
			 * "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			 * Toast.LENGTH_LONG).show();
			 */
		} else {

			gps.showSettingsAlert();
		}
		
		
		
		result = c
				.getString(c.link.toString() +"/update_location.php?uid="
						+ uid + "&ltd=" + latitude +  "&lngd=" + longitude);

		Log.e("location",c.link.toString() +"/update_location.php?uid="
						+ uid + "&ltd=" + latitude +  "&lngd=" + longitude);
		try {

			JSONObject json_data = new JSONObject(result);
			String location = json_data.getString("root");
			
			
			Log.e("root",location.toString());
			
			JSONObject json_data1 = new JSONObject(location);
			location = json_data1.getString("location");
			
			Log.e("root",location.toString());
			
			locationResult = location;

		}
		
		

		catch (Exception e) {

		}
	}
	
	
	private class AsyncActionLocation extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				updatelocation();
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();
			
			Toast.makeText(getApplicationContext(),locationResult ,
					Toast.LENGTH_LONG).show();

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(useraccountsetting.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	
	@SuppressLint("NewApi")
	private void updateemail(String newEmail) {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(useraccountsetting.this);
		String email = preferences.getString("email", "");
		String uid = preferences.getString("uid", "");
		Log.e("email", email);

		//uid = "1";

		/*
		 * result = c
		 * .getString("http://www.instamour.com/APP_files/delete_acount.php?email="
		 * +email);
		 */

		result = c
				.getString(c.link.toString() +"/email_update.php?uid="
						+ uid + "&email=" + newEmail);

		try {

			JSONObject json_data = new JSONObject(result);
			JSONObject root = json_data.getJSONArray("root").getJSONObject(0);

			if (root.has("Result")) {

				JSONObject login = root.getJSONObject("Result");
				String Result = login.getString("Result");
				Toast.makeText(getApplicationContext(), "Account is Deleted !",
						Toast.LENGTH_LONG).show();
			}

		}

		catch (Exception e) {

		}
	}
	
	@SuppressLint("NewApi")
	private void updatepass(String oldpass,String newpass) {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(useraccountsetting.this);
		
		String uid = preferences.getString("uid", "");
		Log.e("uid", uid);

		//uid = "1";

		/*
		 * result = c
		 * .getString("http://www.instamour.com/APP_files/delete_acount.php?email="
		 * +email);
		 */

		result = c
				.getString(c.link.toString() +"/changePassword.php?uid="
						+ uid + "&oldpass=" + oldpass + "&newpass=" + newpass);

		Log.e("link",c.link.toString() +"/changePassword.php?uid="
						+ uid + "&oldpass=" + oldpass + "&newpass=" + newpass);
		try {

			JSONObject json_data = new JSONObject(result);
			String password = json_data.getString("root");
			
			
			Log.e("root",password.toString());
			
			JSONObject json_data1 = new JSONObject(password);
			password = json_data1.getString("password");
			
			Log.e("root",password.toString());
			
			passResult = password;

		}
		
		

		catch (Exception e) {

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
				imguserprofile.setImageURI(selectedImageUri);

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

	private class AsyncActionEmail extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				updateemail(arg0[0]);
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
			pd = new ProgressDialog(useraccountsetting.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	
	private class AsyncActionPass extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				updatepass(arg0[0],arg0[1]);
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();
			
			Toast.makeText(getApplicationContext(),passResult ,
					Toast.LENGTH_LONG).show();

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(useraccountsetting.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

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
			pd = new ProgressDialog(useraccountsetting.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	private void updateimage() {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			/*
			 * HttpPost httppost = new HttpPost(
			 * c.link.toString()+"/user_reg.php");
			 */

			HttpPost httppost = new HttpPost(
				c.link.toString()
				+"/image_edit.php");
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
		} catch (OutOfMemoryError e) {
			Toast.makeText(getApplicationContext(), "Low memory space",
					Toast.LENGTH_LONG).show();
		}
	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
		final List<String> videoPathes = new ArrayList<String>();
		int videocount = 0, totalvideo;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.useraccountsettingtlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.useraccountsettingtlist,
					null, true);
			view.performClick();
			TextView txthelp = (TextView) view.findViewById(R.id.txthelp);
			txthelp.setText(listArr[position]);

			return view;
		}
	}

}
