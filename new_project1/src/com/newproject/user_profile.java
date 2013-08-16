package com.newproject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.newproject.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class user_profile extends Activity {
	
	private TextView txtemail;
	private TextView txtphone;
	private TextView txtlocation;
	private TextView txt_profileusername;
	private String email,phone,location,first_name,last_name,image_name;
	private ImageView profile_upload_img;
	DataHendleCls obS = new DataHendleCls();

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_profile);
		
		init();
		
		if(image_name.length()>=2)
		{
			profile_upload_img.setImageBitmap(getImageBitmap(obS.ImageString+image_name));
		}
		else
		{
			profile_upload_img.setImageResource(R.drawable.profilepic_empty);
			//upload_img.setImageResource(android.R.drawable.btn_radio);
		}
		
	}	

	private void init() {
		// TODO Auto-generated method stub
		
		txtemail = (TextView) findViewById(R.id.txtprofileemail);
		txtphone = (TextView) findViewById(R.id.txtprofilephone);
		txtlocation = (TextView) findViewById(R.id.txtprofilelocation);
		txt_profileusername = (TextView) findViewById(R.id.txt_profileusername);
		profile_upload_img = (ImageView)findViewById(R.id.profile_upload_img);
		
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		email = sp.getString("globle_email", " ");
		phone = sp.getString("phone", " ");
		location = sp.getString("location", "");
		first_name = sp.getString("user_name", " ");
		last_name = sp.getString("last_name", " ");
		image_name = sp.getString("image_name", "");
		
		txtemail.setText("   "+email.toString());
		txtphone.setText("   " +phone);
		txtlocation.setText("   " +location);
		txt_profileusername.setText("   "+first_name+" "+last_name);
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
