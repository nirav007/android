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


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class listproperty extends Activity {

	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	ArrayList<String> aryTicket = new ArrayList<String>();

	private String[] listArr;
	private String[] listcustomer_name;
	private String[] listdistrict_no;
	private String[] listaddress1;
	

	
	private ArrayList<String> ary_store_no = new ArrayList<String>();
	private ArrayList<String> ary_customer_name = new ArrayList<String>();
	private ArrayList<String> ary_district_no = new ArrayList<String>();
	private ArrayList<String> ary_address1 = new ArrayList<String>();
	
	function c = new function();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// if (savedInstanceState == null) {

		setContentView(R.layout.listproperty);

		listView = (ListView) findViewById(R.id.itemList);

		
		new AsyncAction().execute(null, null, null);
		
		//filllistdata();
		

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
			MyArrayAdapter adapter = new MyArrayAdapter(listproperty.this, listArr);
			listView.setAdapter(adapter);
	
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(listproperty.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}
	

	public void filllistdata() {
		
		/*try {

			listView = (ListView) findViewById(R.id.itemList);
			HttpClient httpclient = c.getNewHttpClient();

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			String id = preferences.getString("id", "");

			/*
			 * Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG)
			 * .show();
			 *
			// /192.168.1.3 http://192.168.1.4
			HttpGet httppost = new HttpGet(
					c.link.toString()+"/property.php?user_id="+id);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

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
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		*/
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String id = preferences.getString("id", "");	
		result = c.getString(c.link.toString()+"/property.php?user_id="+id);

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
					
					String store_no = json_data.getString("store_no");
					String customer_name = json_data.getString("customer_name");
					String district_no = json_data.getString("district_no");
					String address1 = json_data.getString("address1");
					String address2 = json_data.getString("address2");
					String city = json_data.getString("city");
					String state = json_data.getString("state");
					String zipcode = json_data.getString("zipcode");
					
					

					
					ary_store_no.add(store_no);
					ary_customer_name.add(customer_name);
					ary_district_no.add(district_no);
					if(address2.equals(""))
					{
						ary_address1.add(address1+" ,  "+city+" , "+state+" , "+zipcode);
					}
					else
					{
						ary_address1.add(address1+" , "+address2+" , "+city+" , "+state+" , "+zipcode);
					}
					

					/*	Toast.makeText(getApplicationContext(), store_no,
							Toast.LENGTH_LONG).show();
					
				  Toast.makeText(getApplicationContext(), type,
					 * Toast.LENGTH_LONG).show();
					 * Toast.makeText(getApplicationContext(), username,
					 * Toast.LENGTH_LONG).show();
					 * Toast.makeText(getApplicationContext(), registered_date,
					 * Toast.LENGTH_LONG).show();
					 * Toast.makeText(getApplicationContext(), userstatus,
					 * Toast.LENGTH_LONG).show();
					 */
					// save value
				}
				}
				else
				{
					ary_store_no.add("No Record Found.");
					ary_customer_name.add("");
					ary_district_no.add("");
					ary_address1.add("");
				}

				listArr = new String[ary_store_no.size()];
				listArr = ary_store_no.toArray(listArr);
				
				listcustomer_name = new String[ary_customer_name.size()];
				listcustomer_name = ary_customer_name.toArray(listcustomer_name);
				
				listdistrict_no = new String[ary_district_no.size()];
				listdistrict_no = ary_district_no.toArray(listdistrict_no);
				
				listaddress1 = new String[ary_address1.size()];
				listaddress1 = ary_address1.toArray(listaddress1);

				

			}

		} catch (Exception e) {

		}

		

	}

	@SuppressLint("ResourceAsColor")
	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;

		private TextView btnchkout;

		// private final integer[] image;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.property, objects);
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
			View view = inflater.inflate(R.layout.property, null, true);

			
			TextView textView = (TextView) view.findViewById(R.id.txtTicketNo);
			if (listArr[position].equals("No Record Found.")) {
				textView.setText(Html.fromHtml(listArr[position]));
				textView.setTextColor(getResources().getColor(R.color.red));
			} else {
				textView.setText("Store No # "+listArr[position]);
			}
			
			textView.setTypeface(tf);
			
			textView = (TextView) view.findViewById(R.id.txtCustomername);
			textView.setText(listcustomer_name[position]);
			//textView.setText(listcustomer_name[position]+" # "+listdistrict_no[position] );
			textView.setTypeface(tf);

			textView = (TextView) view.findViewById(R.id.txtAddress);
			textView.setText(listaddress1[position] );
			textView.setTypeface(tf);
			


			return view;
		}
	}

	

}
