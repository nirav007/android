package com.example.demojson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button b;
	EditText et,pass;
	TextView tv;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    b = (Button)findViewById(R.id.Button01); 
	    et = (EditText)findViewById(R.id.username);
	    pass= (EditText)findViewById(R.id.password);
	    tv = (TextView)findViewById(R.id.tv);

	    b.setOnClickListener(new OnClickListener() {
	        
	        public void onClick(View v) {

	           login();    

	        }
	        });}
	
	
		void login()
		{
			String data = null;
			InputStream is = null;
			StringBuilder sb;
			String result;

			try
			{
		    	HttpClient httpsClient = new DefaultHttpClient();
		    	HttpPost httpPost = new HttpPost("http://192.168.1.4/check.php");
		    	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
		    	
				nameValue.add(new BasicNameValuePair("username", "" + et.getText().toString()));
				nameValue.add(new BasicNameValuePair("password", "" + pass.getText().toString()));
				
		    	
				httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			
				HttpResponse httpRes = httpsClient.execute(httpPost);
				
				
				HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();
				
			}
			catch(Exception e) {}
			
			
			 try
		        {
		        	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
		        	sb = new StringBuilder();
		        	sb.append(BufR.readLine() + "\n");
		        	String line = "0";
		        	
		        	while((line = BufR.readLine()) != null)
		        	{
		        		sb.append(line + "\n");
		        	}
		        	
		        	
		        	result = sb.toString();
		        	
		        	Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
		        	if(result.equals("User Found"))
		        	{
		        		Toast.makeText(getApplicationContext(), "User Found ", Toast.LENGTH_LONG).show();
		        	}
		        	else
		        	{
		        		Toast.makeText(getApplicationContext(), "User Not Found ", Toast.LENGTH_LONG).show();
		        	}
		        }
		        catch (Exception e) {
		        	Log.e("log_tag", "Error in convert String" + e.toString());
				}
		        
			
			
	    	       	
		
		}

	       
	}