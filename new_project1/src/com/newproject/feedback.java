package com.newproject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.newproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class feedback extends Activity implements OnClickListener {

	private TextView btnsave,btnback,txt_profileusername;
	private EditText editfeed;

	protected Integer globle_email;
	
	DataHendleCls obS = new DataHendleCls();
	private String first_name,last_name,image_name;
	private ImageView feedback_upload_img;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.feedback);
		
		init();
		
		
		feedback_upload_img.setImageBitmap(getImageBitmap(obS.ImageString+image_name));
		btnsave.setOnClickListener(this);
	
		
	}

	

	private void init() {
		// TODO Auto-generated method stub
		
		btnsave =  (TextView) findViewById(R.id.txtsave);
		
		editfeed = (EditText) findViewById(R.id.editfeedback);
		
		txt_profileusername = (TextView) findViewById(R.id.feedback_username);
		
		feedback_upload_img = (ImageView) findViewById(R.id.feedback_upload_img);
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		globle_email = sp.getInt("user_id", 0);
		first_name = sp.getString("user_name", " ");
		last_name = sp.getString("last_name", " ");
		image_name = sp.getString("image_name", "");
		
		txt_profileusername.setText("    "+first_name+" "+last_name);
	}

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v==btnsave)
		{
			String msg = editfeed.getText().toString();
			if(msg.length()<=0)
			{
				new AlertDialog.Builder(feedback.this).setMessage("Enter Feedback .")
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
				insdata(msg);
			}
		}
		
	
		
		
	}
	
	private void insdata(String fback) {
		
		try
        {
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(obS.fUrl);
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
		
			nameValue.add(new BasicNameValuePair("user_id", "" + globle_email));
			nameValue.add(new BasicNameValuePair("feedback", "" + fback));
		
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
			new AlertDialog.Builder(feedback.this).setMessage("Feedback send successfully .")
			.setTitle("Hotly Application ")  
	           .setCancelable(true)
	           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					//Intent intent = new Intent(feedback.this,counttab.class);
					//startActivity(intent);
					
				}
			}).show();
			editfeed.setText("");
        	
        }
        catch (Exception e) {
			
		} 	 	
	}
	
	 public Bitmap getImageBitmap(String url) {
	        Bitmap bm = null;
	        URLConnection conn = null;
	        InputStream is = null;
	        BufferedInputStream bis =null;
	        try {
	            URL aURL = new URL(url);
	            
	            conn = aURL.openConnection();
	            
	            conn.connect();
	          is  = conn.getInputStream();
	          bis  = new BufferedInputStream(is);
	            bm = BitmapFactory.decodeStream(bis);
	            bis.close();
	            is.close();
	       } catch (IOException e) { }
	       return bm;
	    } 
		
}
