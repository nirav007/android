package com.example.fpm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class property extends Activity {

	
	private LinearLayout firstLayout;
	
	private TextView btnSubmit;
	private TextView txtTicketNo;
	private TextView txtAlert;
	private TextView txtStore;
	private TextView txtAddress;
	private TextView txtTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.property);
		
		firstLayout= (LinearLayout) findViewById(R.id.firstLayout);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(property.this);
		  String id = preferences.getString("id","");
		  String username = preferences.getString("username","");
		  
		 /* Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
		  Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
		  */
		  
			txtTicketNo = (TextView) findViewById(R.id.txtTicketNo);
			txtAlert = (TextView) findViewById(R.id.txtAlert);
			txtStore = (TextView) findViewById(R.id.txtStore);
			txtAddress = (TextView) findViewById(R.id.txtAddress);
			txtTask =  (TextView) findViewById(R.id.txtTask);
			
			Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GOTHIC.TTF");
			txtTicketNo.setTypeface(tf);
			txtAlert.setTypeface(tf);
			txtStore.setTypeface(tf);
			
			txtAddress.setTypeface(tf);
			txtTask.setTypeface(tf);
	}

}
