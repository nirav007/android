package com.example.fpm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.fpm.listticket.MyArrayAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class listmessages extends Activity {

	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	private String[] listArr;
	private String[] listDate;

	private boolean isChangedStat = false;
	private static final int MENUITEM = Menu.FIRST;

	private ArrayList<String> ary_msg = new ArrayList<String>();
	private ArrayList<String> ary_date = new ArrayList<String>();

	function c = new function();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// if (savedInstanceState == null) {

		setContentView(R.layout.listmessage);

		listView = (ListView) findViewById(R.id.itemList);

		new AsyncAction().execute(null, null, null);

		// filllistdata();

		// here
	}

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

			MyArrayAdapter adapter = new MyArrayAdapter(listmessages.this,
					listArr);
			listView.setAdapter(adapter);

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(listmessages.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	public void filllistdata() {

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String id = preferences.getString("id", "");
		result = c.getString(c.link.toString() + "/messeges.php?user_id=" + id);

		try {

			/*
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show();
			 */

			Log.e("log_tag", "Error in convert String" + result.toString());

			JSONObject json_data = new JSONObject(result);

			String status = json_data.getString("Status");
			// Toast.makeText(getApplicationContext(), status,
			// Toast.LENGTH_LONG).show();

			if (status.equals("OK")) {

				String data = json_data.getString("Data");

				JSONArray json_data1 = new JSONArray(data);

				if (json_data1.length() >= 1) {
					for (int i = 0; i < json_data1.length(); i++) {
						json_data = json_data1.getJSONObject(i);

						String store_no = json_data.getString("msg");
						String date = json_data.getString("date");

						ary_msg.add(store_no);
						ary_date.add(date);

						/*
						 * Toast.makeText(getApplicationContext(), store_no,
						 * Toast.LENGTH_LONG).show();
						 * 
						 * Toast.makeText(getApplicationContext(), type,
						 * Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(), username,
						 * Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(),
						 * registered_date, Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(), userstatus,
						 * Toast.LENGTH_LONG).show();
						 */
						// save value
					}
				} else {
					// String store_no = json_data.getString("msg");
					// String date = json_data.getString("date");

					ary_msg.add("No Record Found.");
					ary_date.add("  ");
				}

				listArr = new String[ary_msg.size()];
				listArr = ary_msg.toArray(listArr);

				listDate = new String[ary_date.size()];
				listDate = ary_date.toArray(listDate);

			}

		} catch (Exception e) {

		}

		// MyArrayAdapter adapter = new MyArrayAdapter(this, listArr);
		// listView.setAdapter(adapter);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent a = new Intent(listmessages.this, symbol.class);
			a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(a);
			finish();
			// super.onBackPressed();
			// moveTaskToBack(true);
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			Intent a = new Intent(listmessages.this, MainActivity.class);
			a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(a);
			finish();
			// super.onBackPressed();
			// moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == MENUITEM) {
			isChangedStat = true;
			startActivity(new Intent(this, listmessage.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();

		menu.add(0, MENUITEM, 0, "Received");

		return super.onPrepareOptionsMenu(menu);
	}

	@SuppressLint("ResourceAsColor")
	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;

		private TextView btnchkout;

		// private final integer[] image;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.message, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			Typeface tf = Typeface.createFromAsset(
					getBaseContext().getAssets(), "fonts/GOTHIC.TTF");

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.message, null, true);

			TextView textView = (TextView) view.findViewById(R.id.txtMessage);

			if (listArr[position].equals("No Record Found.")) {
				textView.setText(Html.fromHtml(listArr[position]));
				textView.setTextColor(getResources().getColor(R.color.red));
			} else {
				textView.setText(Html.fromHtml(listArr[position]));
			}
			// textView.setText( Html.fromHtml( listArr[position].replace("\n",
			// "<br>")));

			textView.setTypeface(tf);

			textView = (TextView) view.findViewById(R.id.txtDate);
			if (listDate[position].equals("  ")) {
				textView.setText("  ");
			} else {
				textView.setText("Sent on " + listDate[position]);
			}
			textView.setTypeface(tf);

			return view;
		}
	}

}
