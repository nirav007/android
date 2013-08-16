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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.newproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class changepassword extends Activity {

	private EditText oldpass;
	private EditText newpass;
	private EditText confirmpass;
	private TextView btn_pass;
	private String globle_email;
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	private String email_j;
	private String user_name;
	private JSONArray jArray;
	DataHendleCls obS = new DataHendleCls();
	private TextView btnback;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.changepassword);
		
		init();
		
		
		btnback.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
		btn_pass.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				//Toast.makeText(getApplicationContext(), globle_email, Toast.LENGTH_LONG).show();
				String _oldpass = oldpass.getText().toString();
				String _newpass = newpass.getText().toString();
				String _confirmpass = confirmpass.getText().toString();
				
				if(_oldpass.length()<=0 || _newpass.length()<=0 || _confirmpass.length()<=0)
				{
					new AlertDialog.Builder(changepassword.this).setMessage("Please Enter Old/New Password ")
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
					if(_newpass.equals(_confirmpass))
					{
						updatepass(_oldpass,_newpass);
					}
					else
					{
						new AlertDialog.Builder(changepassword.this).setMessage("Password Doesn't Match")
						.setTitle("Hotly Application")  
						.setCancelable(true)
						.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							}
						}).show();
					}
				}
			}

			
		});
		
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK)) 
		{
			finish();
		}
			
		return super.onKeyDown(keyCode, event);
	}

	private void init()
	{
		// TODO Auto-generated method stub
		
		oldpass = (EditText) findViewById(R.id.oldpass);
		newpass = (EditText) findViewById(R.id.newpass);
		confirmpass = (EditText) findViewById(R.id.confirmpass);
		
		btnback = (TextView) findViewById(R.id.btnback);
		
		btn_pass = (TextView) findViewById(R.id.btnchangepass);
		
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		globle_email = sp.getString("globle_email","");
	}
	
	private void updatepass(String oldpass, String newpass) {
		
		 try
	        {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.passUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	nameValue.add(new BasicNameValuePair("email",""+ globle_email));
	        	//Toast.makeText(getApplicationContext(), globle_email, Toast.LENGTH_LONG).show();
	        	nameValue.add(new BasicNameValuePair("oldpass",""+ oldpass));
	        	nameValue.add(new BasicNameValuePair("newpass",""+ newpass));
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
	        
	        String owner_name;
	        String password;
	        
	        try{
	        	//jArray = new JSONArray(result);
	        	JSONObject json_data = null;
	        	
	        	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG ).show();
	        	json_data = new JSONObject(result);
	        	
	        	String status = json_data.getString("Status");
	        	
	        	//Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG ).show();
	        	
	        
	          	if(status.equals("OK"))
	         
	        	{
	        		
	        		new AlertDialog.Builder(changepassword.this).setMessage("Password Changed Successfully  ")
					.setTitle("Hotly Application")  
					.setCancelable(true)
					.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
					
						
						}
					}).show();
	        	}
	        	else
	        	{
	        		new AlertDialog.Builder(changepassword.this).setMessage("Please enter Old password correct")
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
