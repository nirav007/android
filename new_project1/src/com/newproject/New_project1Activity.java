package com.newproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class New_project1Activity   extends Activity  {
   
	private TextView btnsignup;
	private EditText username;
	private EditText pass;
	private TextView btnsignin;

	DataHendleCls obS = new DataHendleCls();
	int flag=0;
	int user_id;
	
	private InputStream is;
	private StringBuilder sb;
	private String result;
	private String email_j;
	private String globle_email,user_name,phone,last_name,image_name,location;
	private JSONArray jArray;
	SharedPreferences.Editor pre;
	
	String email,pass1;
	/** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
        
       
        btnsignin.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
			//	btnsignin.setBackgroundColor(R.)
				
				//btnsignin.setBackgroundResource(R.drawable.btn_click);
				
				flag=0;
				email= username.getText().toString();
				pass1 =  pass.getText().toString();
				
				if(email.length()<=0 || pass1.length()<=0 )
				{
					new AlertDialog.Builder(New_project1Activity.this).setMessage("Some Field are Missing !!")
					.setTitle("Hotly Application ")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
				else
				{
					
					select();
				}
			}

			private void select() {
				 try
			        {
			        	HttpClient httpsClient = new DefaultHttpClient();
			        	HttpPost httpPost = new HttpPost(obS.lUrl);
			        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			        	nameValue.add(new BasicNameValuePair("email",""+ email));
			        	nameValue.add(new BasicNameValuePair("pass",""+ pass1));
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
			        
			        String owner_name,user_email,user_pass;
			        String password;
			        
			        try{
			        	
			        	
			        	 	JSONObject json_data = new JSONObject(result);
			        	
			        
			        	
			        	String status = json_data.getString("Status");
			        	Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			        
			        	
			        	if(status.equals("OK"))
			        	{
			        		showDialog(1);
			        		
			        		//Thread.sleep(5000);
			        		String data = json_data.getString("Data");
				        	JSONArray jArray1= new JSONArray(data);
				        	JSONObject json_data1 = null;
				        	json_data1 = jArray1.getJSONObject(0);
				        	
				        	email_j = json_data1.getString("email");
				        //	Toast.makeText(getApplicationContext(), email_j, Toast.LENGTH_LONG).show();
				        	user_name =json_data1.getString("firstname");
				        //	Toast.makeText(getApplicationContext(), user_name, Toast.LENGTH_LONG).show();
				        	phone =json_data1.getString("phone");
				        	
				        	last_name=json_data1.getString("lastname");
				       // 	Toast.makeText(getApplicationContext(), last_name, Toast.LENGTH_LONG).show();
				        	image_name=json_data1.getString("image");
				        	location = json_data1.getString("location");
				         //	Toast.makeText(getApplicationContext(), location, Toast.LENGTH_LONG).show();
				        	user_id = json_data1.getInt("id");
			        		valid_login();
			        		
			        	}
			        	else
			        	{
			        		invalid_login();
			        	}
			        	
			        	
		            
			        }
			        catch (JSONException e){} 	
					
			        catch (Exception e){	}
			}
		});
        
       btnsignup.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(New_project1Activity.this,signup_next.class);
			startActivity(intent);
		}
	});
        
    }

	private void init() {
		btnsignup = (TextView) findViewById(R.id.btnsignup);
		btnsignin = (TextView) findViewById(R.id.btnsignin);
		username = ( EditText) findViewById(R.id.edituser);
		pass = ( EditText) findViewById(R.id.editlocation);
		
		
	}
	
	public void valid_login()
	{
		globledata();
		Intent intent = new Intent(New_project1Activity.this,tab.class);
		dismissDialog(1);
		startActivity(intent);
		
		/*
		new AlertDialog.Builder(New_project1Activity.this).setMessage("Login Success!!")
		.setTitle("Success")  
           .setCancelable(true)
           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {

				
				
				
			}
		}).show();*/
		
		 
		
	}
	
	protected Dialog onCreateDialog(int id) {
		
	    switch (id) {
	        case 1: {
	        	ProgressDialog dialog = new ProgressDialog(this);	
	            dialog.setMessage("Login ..");
	            dialog.setIndeterminate(true);
	            dialog.setCancelable(false);
	            return dialog;
	        }
	        
	    }
	    return null;
	}
	public void invalid_login()
	{

		new AlertDialog.Builder(New_project1Activity.this).setMessage("Invalid Login Information .")
		.setTitle("Hotly Application")  
           .setCancelable(true)
           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {		
			}
		}).show();
	}
	public void globledata()
	{
		
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_WRITEABLE);
		pre = sp.edit();
		pre.clear();
		pre.commit();
		pre.putString("globle_email", email_j);
		pre.putString("user_name", user_name);
		pre.putString("last_name", last_name);
		pre.putString("image_name", image_name);	
		pre.putString("phone", phone);
		pre.putString("location", location);
		//Toast.makeText(getApplicationContext(), user_name, Toast.LENGTH_LONG).show();
		pre.putInt("user_id", user_id);
		pre.commit();
		
	}
	
        
	
}