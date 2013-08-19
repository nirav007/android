package com.example.fpm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;


import com.example.fpm.listticket.MyArrayAdapter;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class profile extends Activity {

	private TextView txtVendorname;
	private TextView txtAddress,txtAddress1;
	private TextView txtEmail;
	private TextView txtPhone;
	private ListView listvca;
	
	private InputStream is;
	private StringBuilder sb;
	private String result;

	

	private String[] listArr;
	private String[] listVcacode;
	private String[] listvcaproperty;
	private String[] listvcacust;

	public ArrayList<String> ary_id = new ArrayList<String>();
	public ArrayList<String> ary_vcacode = new ArrayList<String>();
	public ArrayList<String> ary_vcaproperty = new ArrayList<String>();
	public ArrayList<String> ary_vcacust = new ArrayList<String>();
	
	
	
	function c = new function();
	private TextView txtAddress2;
	private TextView txtVendorname1;
	private TextView txtEmail1;
	private TextView txtvca;
	private TextView txtvcacustname;
	private TextView txtvcaproperty;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profile);

		txtVendorname = (TextView) findViewById(R.id.txtVendorname);
		txtVendorname1 = (TextView) findViewById(R.id.txtVendorname1);
		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtAddress2 = (TextView) findViewById(R.id.txtAddress2);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtEmail1 = (TextView) findViewById(R.id.txtEmail1);
		txtPhone = (TextView) findViewById(R.id.txtPhone);
		listvca = (ListView)findViewById(R.id.listvca);
		
		txtvca = (TextView) findViewById(R.id.txtvca); 
		txtvcacustname = (TextView) findViewById(R.id.txtvcacustname);
		txtvcaproperty = (TextView) findViewById(R.id.txtvcaproperty);
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String vendorname = preferences.getString("vendor_name", "");
		String address = preferences.getString("address", "");
		String email = preferences.getString("contactemail", "");
		String phone = preferences.getString("contactphone", "");
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		// txtHead.setTypeface(tf);
		txtVendorname.setTypeface(tf);
		txtAddress.setTypeface(tf);
		txtEmail.setTypeface(tf);
		txtPhone.setTypeface(tf);
		txtAddress2.setTypeface(tf);
		txtVendorname1.setTypeface(tf);
		txtEmail1.setTypeface(tf);
		
		txtvca.setTypeface(tf);
		txtvcacustname.setTypeface(tf);
		txtvcaproperty.setTypeface(tf);
		
		txtVendorname.setText(vendorname.toString());
		txtAddress.setText(address.toString());
		txtEmail.setText(email.toString());
		txtPhone.setText(phone.toString());


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

			MyArrayAdapter adapter = new MyArrayAdapter(profile.this, listArr);
			listvca.setAdapter(adapter);
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(profile.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}



	public void filllistdata() {
		

		listArr = new String[0];
	
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String id = preferences.getString("id", "");
		
		result = c.getString(c.link.toString()+"/vcadetails.php?user_id=" + id);
		
		/*try {

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
					c.link.toString()+"/vcadetails.php?user_id=" + id);

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
				
				

				for (int i = 0; i <json_data1.length(); i++) {
					json_data = json_data1.getJSONObject(i);
					String vca_id = json_data.getString("vca_id");
					String vca_code = json_data.getString("vca_code");
					String cust_name = json_data.getString("cust_name");
					String property = json_data.getString("property");
					

					ary_id.add(vca_id);
					ary_vcacode.add(vca_code);
					ary_vcaproperty.add(property);
					ary_vcacust.add(cust_name);
					
					
					/*Toast.makeText(getApplicationContext(), ticket_code,
							Toast.LENGTH_LONG).show();
					
					 * Toast.makeText(getApplicationContext(), type,
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

				listArr = new String[ary_id.size()];
				listArr = ary_id.toArray(listArr);

				listVcacode = new String[ary_vcacode.size()];
				listVcacode = ary_vcacode.toArray(listVcacode);
				
				listvcacust = new String[ary_vcacust.size()];
				listvcacust = ary_vcacust.toArray(listvcacust);
				
				listvcaproperty = new String[ary_vcaproperty.size()];
				listvcaproperty = ary_vcaproperty.toArray(listvcaproperty);
				
				

			/*	listCustname = new String[ary_cust_name.size()];
				listCustname = ary_cust_name.toArray(listCustname);

				listStoreno = new String[ary_store_no.size()];
				listStoreno = ary_store_no.toArray(listStoreno);*/

			

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
			super(context, R.layout.vcadetails, objects);
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
			View view = inflater.inflate(R.layout.vcadetails, null, true);

			TextView textView = (TextView) view.findViewById(R.id.txtvca);
			textView.setText(listVcacode[position]);
			textView.setTypeface(tf);
			
			textView = (TextView) view.findViewById(R.id.txtvcacustname);
			textView.setText(listvcacust[position]);
			textView.setTypeface(tf);
			
			textView = (TextView) view.findViewById(R.id.txtvcaproperty);
			textView.setText(listvcaproperty[position]);
			textView.setTypeface(tf);

			
			return view;
		}
	}

	

}
