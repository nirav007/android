package com.example.fpm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.fpm.listticket.MyArrayAdapter;
import com.google.android.maps.MapActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class locationticket extends Activity {

	static ListView listView;

	private InputStream is;
	private StringBuilder sb;
	private String result;

	ArrayList<String> aryTicket = new ArrayList<String>();

	private String[] listArr;
	private String[] listSubject;
	private String[] listCustname;
	private String[] listStoreno;
	private String[] listAddress;
	private String[] listStatus;
	private String[] listTicketid;

	public ArrayList<String> ary_ticket_code = new ArrayList<String>();
	public ArrayList<String> ary_subject = new ArrayList<String>();
	public ArrayList<String> ary_cust_name = new ArrayList<String>();
	public ArrayList<String> ary_store_no = new ArrayList<String>();
	public ArrayList<String> ary_address = new ArrayList<String>();
	public ArrayList<String> ary_ticketid = new ArrayList<String>();

	public ArrayList<String> ary_status = new ArrayList<String>();

	function c = new function();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listticket);

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

			MyArrayAdapter adapter = new MyArrayAdapter(locationticket.this,
					listArr);
			listView.setAdapter(adapter);
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(locationticket.this);
			pd.setMessage("loading...");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

	}

	public void filllistdata() {

		ary_ticket_code.clear();
		ary_subject.clear();
		ary_cust_name.clear();
		ary_store_no.clear();
		ary_address.clear();
		ary_ticketid.clear();

		listArr = new String[0];
		listSubject = new String[0];
		listCustname = new String[0];
		listStoreno = new String[0];
		listAddress = new String[0];
		listStatus = new String[0];
		listTicketid = new String[0];

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String id = preferences.getString("id", "");
		String lat = preferences.getString("lat", "");
		String lng = preferences.getString("lng", "");
		result = c.getString(c.link.toString() + "/locationticket.php?user_id="
				+ id + "&lat=" + lat + "&lng=" + lng);

		try {

			/*
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show();
			 */
			Log.e("log_tag", "Result :" + result.toString());

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
						String ticket_code = json_data.getString("ticket_code");
						String subject = json_data.getString("subject");
						String cust_name = json_data.getString("cust_name");
						String store_no = json_data.getString("store_no");
						String address = json_data.getString("address");
						String status1 = json_data.getString("status1");
						String ticket_id = json_data.getString("ticket_id");

						ary_ticket_code.add(ticket_code);
						ary_subject.add(subject);
						ary_cust_name.add(cust_name);
						ary_store_no.add(store_no);
						ary_address.add(address);
						ary_status.add(status1);
						ary_ticketid.add(ticket_id);

						/*
						 * Toast.makeText(getApplicationContext(), ticket_code,
						 * Toast.LENGTH_LONG).show();
						 * 
						 * Toast.makeText(getApplicationContext(), subject,
						 * Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(), cust_name,
						 * Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(), store_no,
						 * Toast.LENGTH_LONG).show();
						 * Toast.makeText(getApplicationContext(), address,
						 * Toast.LENGTH_LONG).show();
						 */
						// save value
					}
				} else {
					ary_ticket_code.add("No Record Found.");
					ary_subject.add("");
					ary_cust_name.add("");
					ary_store_no.add("");
					ary_address.add("");
					ary_status.add("");
					ary_ticketid.add("");
				}

				listArr = new String[ary_ticket_code.size()];
				listArr = ary_ticket_code.toArray(listArr);

				listSubject = new String[ary_subject.size()];
				listSubject = ary_subject.toArray(listSubject);

				listCustname = new String[ary_cust_name.size()];
				listCustname = ary_cust_name.toArray(listCustname);

				listStoreno = new String[ary_store_no.size()];
				listStoreno = ary_store_no.toArray(listStoreno);

				listAddress = new String[ary_address.size()];
				listAddress = ary_address.toArray(listAddress);

				listStatus = new String[ary_status.size()];
				listStatus = ary_status.toArray(listStatus);

				listTicketid = new String[ary_ticketid.size()];
				listTicketid = ary_ticketid.toArray(listTicketid);

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
			super(context, R.layout.ticket, objects);
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
			View view = inflater.inflate(R.layout.ticket, null, true);

			TextView textView = (TextView) view.findViewById(R.id.txtTicketNo);

			if (listArr[position].equals("No Record Found.")) {
				textView.setText(Html.fromHtml(listArr[position]));
				textView.setTextColor(getResources().getColor(R.color.red));
			} else {
				textView.setText(listArr[position]);
			}

			textView.setTypeface(tf);

			final TextView txtAlert = (TextView) view
					.findViewById(R.id.txtAlert1);
			// Toast.makeText(getApplicationContext(), listStatus[position],
			// Toast.LENGTH_LONG).show();
			String statusCheck = listStatus[position].toString().trim();

			if (statusCheck.equals("Alert") == true) {
				txtAlert.setText(statusCheck);
				txtAlert.setTextColor(getResources().getColor(R.color.red));

			} else if (statusCheck.equals("In Progress") == true) {
				txtAlert.setText(statusCheck);
				txtAlert.setTextColor(getResources().getColor(R.color.view));

			} else if (statusCheck.equals("Work Complete Pending paperwork") == true) {
				txtAlert.setText("Work Complete Pending paperwork");
				txtAlert.setTextColor(getResources().getColor(R.color.green));

			} else {
				txtAlert.setText(statusCheck);
				// txtAlert.setTextColor(getResources().getColor(R.color.defaulttextcolor));
			}

			txtAlert.setTypeface(tf);

			textView = (TextView) view.findViewById(R.id.txtStore);
			if (listArr[position].equals("No Record Found.")) {
				textView.setText("");

			} else {
				textView.setText(listCustname[position] + " Store # "
						+ listStoreno[position]);
			}

			textView.setTypeface(tf);

			textView = (TextView) view.findViewById(R.id.txtAddress);
			textView.setText(listAddress[position]);
			textView.setTypeface(tf);

			textView = (TextView) view.findViewById(R.id.txtTask);
			textView.setText(listSubject[position]);
			textView.setTypeface(tf);

			final TextView btntextView = (TextView) view
					.findViewById(R.id.btnCheckin);
			btntextView.setTypeface(tf);
			
			if (listArr[position].equals("No Record Found.")) {
				View myView = (TextView) view.findViewById(R.id.btnCheckin);
				ViewGroup parent1 = (ViewGroup) myView.getParent();
				parent1.removeView(myView);
				myView = (TextView) view.findViewById(R.id.btnCheckout);
				parent1 = (ViewGroup) myView.getParent();
				parent1.removeView(myView);
			}
			
			else
			{

			if (statusCheck.equals("In Progress")) {
				Drawable d = getResources().getDrawable(R.drawable.btncheckin);
				btntextView.setBackgroundDrawable(d);
			} else if (statusCheck.equals("Work Complete Pending paperwork") == true) {
				Drawable d = getResources().getDrawable(R.drawable.btncheckin);
				btntextView.setBackgroundDrawable(d);
				btntextView.setClickable(false);
			}

			btntextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					txtAlert.setText("In Progress");
					txtAlert.setTextColor(getResources().getColor(R.color.view));
					Drawable d = getResources().getDrawable(
							R.drawable.btncheckin);
					btntextView.setBackgroundDrawable(d);
					btntextView.setClickable(false);

					try {

						HttpClient httpclient = c.getNewHttpClient();

						// /192.168.1.3
						HttpGet httppost = new HttpGet(
								"c.link.toString()/updatenote.php?id="
										+ listTicketid[position]
										+ "&btncheckIn=true");

						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						is = entity.getContent();

					} catch (Exception e) {
						Log.e("log_tag",
								"Error in http connection " + e.toString());
					}

					// convert response to string
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "iso-8859-1"), 8);
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						result = sb.toString();
					} catch (Exception e) {
						Log.e("log_tag",
								"Error converting result " + e.toString());
					}

				}
			});

			btnchkout = (TextView) view.findViewById(R.id.btnCheckout);
			btnchkout.setTypeface(tf);

			if (statusCheck.equals("Work Complete Pending paperwork")) {
				Drawable d = getResources().getDrawable(R.drawable.btncheckin);
				btnchkout.setBackgroundDrawable(d);
				btnchkout.setClickable(false);
				btntextView.setClickable(false);
			}

			if (statusCheck.equals("Work Complete Pending paperwork") == true) {
				Drawable d = getResources().getDrawable(R.drawable.btncheckin);
				btnchkout.setBackgroundDrawable(d);
				btnchkout.setClickable(false);
			}
			btnchkout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(locationticket.this,
							ticket_detail.class);

					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(locationticket.this);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("S_ticketno", listArr[position]);
					editor.putString("S_alert", txtAlert.getText().toString());
					editor.putString("S_storeno", listStoreno[position]);
					editor.putString("S_custname", listCustname[position]);
					editor.putString("S_address", listAddress[position]);
					editor.putString("S_subject", listSubject[position]);
					editor.putString("S_ticketid", listTicketid[position]);
					editor.putString("S_alertposition",
							String.valueOf(position));
					editor.commit();
					startActivity(intent);

				}
			});
			}

			if (statusCheck.equals("Alert") == true) {
				txtAlert.setText(statusCheck);
				txtAlert.setTextColor(getResources().getColor(R.color.red));

			} else if (statusCheck.equals("In Progress") == true) {
				txtAlert.setText(statusCheck);
				txtAlert.setTextColor(getResources().getColor(R.color.view));

			} else if (statusCheck.equals("Work Complete Pending paperwork") == true) {
				txtAlert.setText("Work Complete Pending paperwork");
				txtAlert.setTextColor(getResources().getColor(R.color.green));
				btnchkout.setClickable(false);
				btntextView.setClickable(false);

			} else {
				txtAlert.setText(statusCheck);
				// txtAlert.setTextColor(getResources().getColor(R.color.defaulttextcolor));
			}

			/*if (listArr[position].equals("No Record Found.")) {
				View myView = (TextView) view.findViewById(R.id.btnCheckin);
				ViewGroup parent1 = (ViewGroup) myView.getParent();
				parent1.removeView(myView);

				myView = (TextView) view.findViewById(R.id.btnCheckout);
				parent1 = (ViewGroup) myView.getParent();
				parent1.removeView(myView);
			}*/
			return view;
		}
	}

}
