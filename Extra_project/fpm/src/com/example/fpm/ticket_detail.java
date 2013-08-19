package com.example.fpm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

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


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ticket_detail extends Activity {
	
	private TextView btnSubmit;
	private TextView txtTicketNo;
	private TextView txtAlert;
	private TextView txtStore;
	private TextView txtAddress;
	private TextView txtTask;
	private TextView txtNote;
	private EditText txtNotedetail;
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	
	function c = new function();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ticket_detail);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ticket_detail.this);
		  String ticketNo = preferences.getString("S_ticketno","");
		  String alert = preferences.getString("S_alert","");
		  String storeNo = preferences.getString("S_storeno","");
		  String custName = preferences.getString("S_custname","");
		  String custAddress = preferences.getString("S_address","");
		  String subject = preferences.getString("S_subject","");
		  final String ticketId = preferences.getString("S_ticketid","");
		  final String vendorname = preferences.getString("vendor_name","");
		  
		
		btnSubmit = (TextView) findViewById(R.id.btnSubmit);
		txtTicketNo = (TextView) findViewById(R.id.txtTicketNo);
		txtAlert = (TextView) findViewById(R.id.txtAlert);	
		
		txtStore = (TextView) findViewById(R.id.txtStore);
		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtTask =  (TextView) findViewById(R.id.txtTask);
		txtNote = (TextView) findViewById(R.id.txtNote);
		txtNotedetail = (EditText) findViewById(R.id.txtNotedetail);
		
	
		if(alert.equals("Alert")==true)
		{
			
			txtAlert.setTextColor(getResources().getColor(R.color.red));
			
		}
		else if (alert.equals("In Progress")==true)
		{
			txtAlert.setTextColor(getResources().getColor(R.color.view));
		}
		else if (alert.equals("Work Complete Pending paperwork")==true)
		{
			txtAlert.setTextColor(getResources().getColor(R.color.green));
		}
		
		
		
		 txtAlert.setText(alert);
		 txtTicketNo.setText(ticketNo);
		 txtStore.setText(custName+" Store # "+storeNo);
		 txtAddress.setText(custAddress);
		 
		 txtTask.setText(subject);
		 
		
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
		txtTicketNo.setTypeface(tf);
		txtAlert.setTypeface(tf);
		txtStore.setTypeface(tf);
		
		txtAddress.setTypeface(tf);
		txtTask.setTypeface(tf);
		
		txtNote.setTypeface(tf);
		txtNotedetail.setTypeface(tf);
		btnSubmit.setTypeface(tf);
		
	
		
		
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String newNotes = txtNotedetail.getText().toString().trim();
				if(newNotes.length()==0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(ticket_detail.this);
					builder.setMessage("Please Enter Notes")
					       .setCancelable(false)
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //do things
					        	 txtNotedetail.setFocusable(true);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				
				}
				else
				{
					 new AsyncAction().execute(null,null,null);
					
					
					//Intent intent = new Intent(ticket_detail.this,tab.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//startActivity(intent);
					
				}
				//onBackPressed();
			}
		});
	}
	
	private class AsyncAction extends AsyncTask<String, Void, String> 
	{
	     public boolean status=false;
		private ProgressDialog pd;
	    @Override
	    protected String doInBackground(String... arg0) 
	    {
	        // TODO Auto-generated method stub
	        try
	          {
	        	//((MyApplication) ticket_detail.this.getApplication()).setSomeVariable("true");
	        	 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ticket_detail.this);
	  	       SharedPreferences.Editor editor = preferences.edit();
	  			  editor.putString("notification","true");
	  			  editor.commit();
	  		
	        	
	        	String newNotes = txtNotedetail.getText().toString().trim();
	       // 	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ticket_detail.this);
	  		  final String ticketId = preferences.getString("S_ticketid","");
	  		  final String vendorname = preferences.getString("vendor_name","");
	  		  
	        	updateNotes(vendorname,ticketId,newNotes);
	           
	              status=true;

	        } 
	          catch (Exception e) 
	          {
	            // TODO: handle exception
	        }

	        return null;
	    }

	    @Override
	    protected void onPostExecute(String result) 
	    {

	       pd.dismiss();
	       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ticket_detail.this);	  
				String notification = preferences.getString("notification", "");
			
			  
			  
	       Intent intent = new Intent(ticket_detail.this,tab.class);
	       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		   startActivity(intent);
	       //finish();

	    }

	    protected void onPreExecute() 
	    {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        pd = new ProgressDialog(ticket_detail.this);
	        pd.setMessage("Please Wait ...");
	        pd.setIndeterminate(true);
	        pd.setCancelable(false);
	        pd.show();
	    }

	}
	
	public void updateNotes(String cuString,String id,String newNotes)
	{
		try {

			HttpClient httpclient = c.getNewHttpClient();

			// /192.168.1.3
			HttpGet httppost = new HttpGet(c.link.toString()+"/updatenote.php?id="+id+"&custname="+cuString+"&notes="+newNotes);
					

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
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
			Log.e("log_tag", "Error converting result " + e.toString());
		}
	}
	
	  @Override
	   public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	           Intent a = new Intent(ticket_detail.this,tab.class);
	           a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	           startActivity(a);
	           finish();
	    	  // super.onBackPressed();
	    	   //moveTaskToBack(true);
	           return true;
	       }
	       return super.onKeyDown(keyCode, event);
	   }   

	 
}
