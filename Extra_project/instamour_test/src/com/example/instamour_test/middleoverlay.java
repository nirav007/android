package com.example.instamour_test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class middleoverlay extends Activity {



	private ImageView img_middel1;
	private ImageView img_middel2;
	
	ImageView m_textbox1;
	
	String flag="a";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.middleoverlay);
		
		
		ImageView m_signup = (ImageView) findViewById(R.id.m_signup);
		m_signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=  new Intent(middleoverlay.this,login.class);
				startActivity(intent);
			}
		});
		
		ImageView m_browseup = (ImageView) findViewById(R.id.m_browseup);
		m_browseup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=  new Intent(middleoverlay.this,SampleActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		ImageView img_signin = (ImageView) findViewById(R.id.img_signinlogin);
		img_signin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intent=  new Intent(middleoverlay.this,signin.class);
				startActivity(intent);
			}
		});

		img_middel1 = (ImageView) findViewById(R.id.img_middle1);
		
		
		m_textbox1  = (ImageView) findViewById(R.id.m_textbox1);
		m_textbox1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(flag.equals("a"))
				{
					Animation lefttoright = AnimationUtils.loadAnimation(getApplicationContext(),
				            R.anim.righttoleft);
				    
					img_middel1.startAnimation(lefttoright);
					
					img_middel1.setImageResource(R.drawable.middleoverlay_2);
					
					m_textbox1.setImageResource(R.drawable.m_textbox2);
					
					flag="b";
				}
				else if(flag.equals("b"))
				{
					Animation lefttoright = AnimationUtils.loadAnimation(getApplicationContext(),
				            R.anim.righttoleft);
				    
					img_middel1.startAnimation(lefttoright);
					
					img_middel1.setImageResource(R.drawable.middleoverlay_3);
					
					m_textbox1.setImageResource(R.drawable.m_textbox3);
					
					flag="c";
				}
				
				else if(flag.equals("c"))
				{
					Animation lefttoright = AnimationUtils.loadAnimation(getApplicationContext(),
				            R.anim.righttoleft);
				    
					img_middel1.startAnimation(lefttoright);
					
					img_middel1.setImageResource(R.drawable.middleoverlay_1);
					
					m_textbox1.setImageResource(R.drawable.m_textbox1);
					
					flag="a";
				}
				
			}
		});
		
		
		
		
	}
	
	public void onBackPressed() {
		   
		   Intent setIntent =  new Intent(Intent.ACTION_MAIN);
		   setIntent.addCategory(Intent.CATEGORY_HOME);
		   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(setIntent);
		}

	

}

