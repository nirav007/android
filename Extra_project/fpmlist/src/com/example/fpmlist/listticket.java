package com.example.fpmlist;

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
public class listticket extends Activity {

	static ListView listView;

	ArrayList<String> aryTicket = new ArrayList<String>();

	private String[] listArr = { "A", "B" };

	
	

	ArrayList<String> ary_ticket_code = new ArrayList<String>();

	
	public static ArrayList<String> ary_status = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listticket);

		listView = (ListView) findViewById(R.id.itemList);

	

		listArr = new String[ary_ticket_code.size()];
		listArr = ary_ticket_code.toArray(listArr);

		
		MyArrayAdapter adapter = new MyArrayAdapter(this, listArr);
		listView.setAdapter(adapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ary_status.set(0, "Work Done");
		((MyArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
	
	}

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

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.ticket, null, true);

			TextView textView = (TextView) view.findViewById(R.id.txtTicketNo);
			textView.setText(listArr[position]);

			final TextView txtAlert = (TextView) view
					.findViewById(R.id.txtAlert1);
			
			

			final TextView btntextView = (TextView) view
					.findViewById(R.id.btnCheckin);

		

			btnchkout = (TextView) view.findViewById(R.id.btnCheckout);

			btnchkout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(listticket.this,
							ticket_detail.class);
					startActivity(intent);

				}
			});

			return view;
		}
	}

}
