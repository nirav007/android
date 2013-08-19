package com.example.fpmlist;

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
		
		
		 txtAlert.setText(alert);
		 txtTicketNo.setText(ticketNo);
		 txtStore.setText(custName+" Store # "+storeNo);
		 txtAddress.setText(custAddress);
		 
		 txtTask.setText(subject);
		 
		
	
		
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String newNotes = txtNotedetail.getText().toString().trim();
				if(newNotes.length()==0)
				{
				
				}
				else
				{
					 new AsyncAction().execute(null,null,null);
					
					
					
					
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
	        	
	        	//updateNotes(vendorname,ticketId,newNotes);
	           
	            

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
	
			  
			  
	       Intent intent = new Intent(ticket_detail.this,listticket.class);
	       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
	

	
	  @Override
	   public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	           Intent a = new Intent(ticket_detail.this,listticket.class);
	           a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	           startActivity(a);
	           finish();
	    	  // super.onBackPressed();
	    	   //moveTaskToBack(true);
	           return true;
	       }
	       return super.onKeyDown(keyCode, event);
	   }   

	  public HttpClient getNewHttpClient() {
	        try {
	            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	            trustStore.load(null, null);

	            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	            HttpParams params = new BasicHttpParams();
	            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	            SchemeRegistry registry = new SchemeRegistry();
	            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	            registry.register(new Scheme("https", sf, 443));

	            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	            return new DefaultHttpClient(ccm, params);
	        } catch (Exception e) {
	            return new DefaultHttpClient();
	        }
	    }
}
