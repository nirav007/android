package com.example.wifi_hunter_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class splash extends android.app.Activity{

	private TextView txtScan;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		txtScan = (TextView) findViewById(R.id.txtScan);
		
		txtScan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(splash.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
}
