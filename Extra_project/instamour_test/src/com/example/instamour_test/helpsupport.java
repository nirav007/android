package com.example.instamour_test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.example.instamour_test.twitter.updateTwitterStatus;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class helpsupport extends Activity {
	
	/*twitter */
	
	static String TWITTER_CONSUMER_KEY = "f0Z7gFSkFIXAqBVFd5wHHA"; // place your cosumer key here
	static String TWITTER_CONSUMER_SECRET = "utdtrqaq2KNLnaJGQs3GQvdNQG1km2KiAxCzCQM"; // place your consumer secret here

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
	ProgressDialog pDialog;
	
	private static Twitter twitter;
	private static RequestToken requestToken;
	
	// Shared Preferences
	private static SharedPreferences mSharedPreferences;
	
	// Internet Connection detector
	private ConnectionDetector cd;
	
	/* twitter over */
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	
	String feedback;

	function c = new function();

	static ListView listView;
	private String[] listArr = { "Rate our app", "Send us Feedback",
			"Share Instamour", "Delete account", "About Instamour",
			"Privacy Policy", "Terms of Service" };

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpsupport);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}
		
		// Shared Preferences
				mSharedPreferences = getApplicationContext().getSharedPreferences(
						"MyPref", 0);
				
				

		listView = (ListView) findViewById(R.id.userList);

		TextView txtheadtext = (TextView) findViewById(R.id.txtheadtext);
		txtheadtext.setText("Help & Support");

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(helpsupport.this,
								R.id.inner_content, width);
						startActivity(new Intent(helpsupport.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		MyArrayAdapter adapter = new MyArrayAdapter(helpsupport.this, listArr);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (arg2 == 3) {
					AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
							helpsupport.this);
					myAlertDialog.setTitle("Delete Account");
					myAlertDialog.setMessage("Are you sure ?");
					myAlertDialog.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// do something when the Cancel button is
									// clicked
									Toast.makeText(getApplicationContext(),
											"no", Toast.LENGTH_LONG).show();
								}
							});
					myAlertDialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// do something when the OK button is
									// clicked
									Toast.makeText(getApplicationContext(),
											"yes", Toast.LENGTH_LONG).show();

									new AsyncAction().execute(null, null, null);
									// deleteaccount();
								}
							});

					myAlertDialog.show();
				} else if (arg2 == 2) {
					showPopup();
				}
				
				else if (arg2 == 1) {
					
					
					AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
							helpsupport.this);
					myAlertDialog.setTitle("Send Feedback");
					final EditText input = new EditText(helpsupport.this);
					input.setHeight(20);
				    //input.setHint("hint");
				    myAlertDialog.setView(input);
					myAlertDialog.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									
								}
							});
					myAlertDialog.setPositiveButton("Send",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									

									String search =  input.getText().toString();
									search = search.replace(" ", "%20");
									new AsyncActionFeedback().execute(search, null, null);
									// deleteaccount();
								}
							});

					myAlertDialog.show();
					
					
					
				}
				
			}
		});

	}
	
	
	@SuppressLint("NewApi")
	public void userfeedback(String txtfeed)
	{
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(helpsupport.this);
		String uid = preferences.getString("uid", "");
		
		result = c
				.getString(c.link.toString()+"/userfeedback.php?uid="+uid+"&feedback=" + txtfeed);

		/*result = c
				.getString("http://192.168.1.1/instamour/APP_files/forgotpassword.php?uid="+""+"&email=" + "nirav.r@wingstechsolutions.com");*/
		
		try {

			JSONObject json_data = new JSONObject(result);
			feedback = json_data.getString("root");
			
			
			Log.e("root",feedback.toString());
			
			JSONObject json_data1 = new JSONObject(feedback);
			feedback = json_data1.getString("feedback");
			
			Log.e("root",feedback.toString());
			/*if (root.has("Result")) {

				//JSONObject login = root.getJSONObject("Result");
				//Log.e("login",login.toString());
				String Result = root.getString("Result");
				Log.e("login",Result.toString());
				
				
			}*/

		}

		catch (Exception e) {

		}
	}
	
	private class AsyncActionFeedback extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				//password(arg0[0].toString());
				userfeedback(arg0[0].toString());
			//	status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();
			
			Toast.makeText(getApplicationContext(),feedback,
					Toast.LENGTH_LONG).show();

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(helpsupport.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	


	public void showPopup() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Share Instamour");

		LayoutInflater inflater = getLayoutInflater();
		View checkboxLayout = inflater.inflate(R.layout.popuplayout, null);
		helpBuilder.setView(checkboxLayout);

		TextView txtFacebook = (TextView) checkboxLayout
				.findViewById(R.id.txt1);
		txtFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "facebook",
						Toast.LENGTH_LONG).show();

			}
			// Perform button logic
		});

		TextView txtTwitter = (TextView) checkboxLayout.findViewById(R.id.txt2);
		txtTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(helpsupport.this,twitter.class);
				// startActivity(intent);
				twitterPopup();
			}
			// Perform button logic
		});

		helpBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do nothing but close the dialog
					}
				});

		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}
	EditText txtUpdateStatus;
	public void twitterPopup() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);

		LayoutInflater inflater = getLayoutInflater();
		View checkboxLayout = inflater.inflate(R.layout.twitterpost, null);
		helpBuilder.setView(checkboxLayout);

		txtUpdateStatus = (EditText) checkboxLayout
				.findViewById(R.id.txtUpdateStatus);
		
		Button btnTweet = (Button) checkboxLayout
				.findViewById(R.id.btnTwitter);
		btnTweet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				loginToTwitter(txtUpdateStatus.getText().toString());

			}
			// Perform button logic
		});

		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}
	
	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}
	
	private void loginToTwitter(String txtTweet) {
		// Check if already logged in
		
		if (!isTwitterLoggedInAlready()) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			Configuration configuration = builder.build();
			
			TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter
						.getOAuthRequestToken(TWITTER_CALLBACK_URL);
				this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(requestToken.getAuthenticationURL())));
				
				String status = txtTweet.toString();

				// Check for blank text
				if (status.trim().length() > 0) {
					// update status
					new updateTwitterStatus().execute(status);
				} else {
					// EditText is empty
					Toast.makeText(getApplicationContext(),
							"Please enter status message", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {
			// user already logged into twitter
			/*Toast.makeText(getApplicationContext(),
					"Already Logged into twitter", Toast.LENGTH_LONG).show();*/
			
			String status = txtTweet.toString();

			// Check for blank text
			if (status.trim().length() > 0) {
				// update status
				new updateTwitterStatus().execute(status);
			} else {
				// EditText is empty
				Toast.makeText(getApplicationContext(),
						"Please enter status message", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	
	/** This if conditions is tested once is
	 * redirected from twitter page. Parse the uri to get oAuth
	 * Verifier
	 * */
	class updateTwitterStatus extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(helpsupport.this);
			pDialog.setMessage("Updating to twitter...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			Log.d("Tweet Text", "> " + args[0]);
			String status = args[0];
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
				
				// Access Token 
				String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
				
				AccessToken accessToken = new AccessToken(access_token, access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
				
				// Update status
				twitter4j.Status response = twitter.updateStatus(status);
				
				Log.d("Status", "> " + response.getText());
			} catch (TwitterException e) {
				// Error in updating status
				Log.d("Twitter Update Error", e.getMessage());
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog and show
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * from background thread, otherwise you will get error
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(),
							"Status tweeted successfully", Toast.LENGTH_SHORT)
							.show();
					// Clearing EditText field
					//txtUpdate.setText("");
				}
			});
		}

	}

	@SuppressLint("NewApi")
	private class AsyncAction extends AsyncTask<String, Void, String> {
		public boolean status = false;
		private ProgressDialog pd;

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				deleteaccount();
				Thread.sleep(3000);
				status = true;

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pd.dismiss();

			Intent intent = new Intent(helpsupport.this, signin.class);
			startActivity(intent);

		}

		@SuppressLint("NewApi")
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(helpsupport.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	@SuppressLint("NewApi")
	private void deleteaccount() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(helpsupport.this);
		String email = preferences.getString("email", "");
		Log.e("email", email);

		/*
		 * result = c
		 * .getString("http://www.instamour.com/APP_files/delete_acount.php?email="
		 * +email);
		 */

		result = c
				.getString(c.link.toString()+"/delete_acount.php?email="
						+ email);

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

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
		final List<String> videoPathes = new ArrayList<String>();
		int videocount = 0, totalvideo;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.userhelpsupportlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.userhelpsupportlist, null,
					true);
			view.performClick();
			TextView txthelp = (TextView) view.findViewById(R.id.txthelp);
			txthelp.setText(listArr[position]);

			return view;
		}
	}

}
