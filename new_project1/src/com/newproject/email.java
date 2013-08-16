package com.newproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.newproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class email extends Activity {
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	DataHendleCls obS = new DataHendleCls();
	
	private TextView btnback;
	private EditText editemail;
	private EditText editphone;
	private TextView btnsave;
	private String oldemail,newmail,phn,phone;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.email);
		
		init();
		
		
		btnback.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				finish();
			}
		});
		
		btnsave.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				newmail = editemail.getText().toString();
				phn = editphone.getText().toString();
				
				if(newmail.length()<=0  )
				{
					new AlertDialog.Builder(email.this).setMessage("Please Enter All Field")
					.setTitle("Hotly Application")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
				else
				{
					updateinfo(newmail,phn);
				}
			}
		});
		
		
	}

	private void init() {
		
		btnback = (TextView) findViewById(R.id.btnback);
		btnsave = (TextView) findViewById(R.id.btnchangeemail);
		editemail = (EditText) findViewById(R.id.editmail);
		editphone = (EditText) findViewById(R.id.editphn);
		
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		//user_id = sp.getInt("user_id",0);
		oldemail = sp.getString("globle_email", " ");
		phone = sp.getString("phone", " ");
		editemail.setText(oldemail);
		editphone.setText(phone.toString());
		
	}
	
	
	
	private void updateinfo(String newmail,String phn)
	{
		 try
	        {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.passUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	nameValue.add(new BasicNameValuePair("oldemail",""+ oldemail));
	        	nameValue.add(new BasicNameValuePair("newemail",""+ newmail));
	        	nameValue.add(new BasicNameValuePair("phone",""+ phn));
	        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	        	HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();       	
	        	
	        }
	        catch (Exception e) {
	        	Log.e("log_tag", "Error in HTTP connection" + e.toString()); 	
			}
	        
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
	        	
	        	is.close();
	        	result = sb.toString();
	        }
	        catch (Exception e) {
	        	Log.e("log_tag", "Error in convert String" + e.toString());
			}
	        
	        
	        try{
	        	
	        	JSONObject json_data = null;
	        	
	        	json_data = new JSONObject(result);
	        	
	        	String status = json_data.getString("Status");
	        
	          	if(status.equals("OK"))
	         
	        	{
	        		
	        		new AlertDialog.Builder(email.this).setMessage("Profile Update Successfully .")
					.setTitle("Hotly Application")  
					.setCancelable(true)
					.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
					
						//Intent intent = new Intent(email.this,setting.class);
						//startActivity(intent);
						
						}
					}).show();
	        	}
	        	else
	        	{
	        		new AlertDialog.Builder(email.this).setMessage("Don't Update")
					.setTitle("Hotly Application")  
					.setCancelable(true)
					.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						}
					}).show();
	        	}
	        	
	               	
	            
	        }
	        catch (JSONException e){} 	
			
	        catch (Exception e){	}
		
	}

	

}
