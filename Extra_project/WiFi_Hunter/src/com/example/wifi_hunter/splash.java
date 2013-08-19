package com.example.wifi_hunter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class splash extends android.app.Activity{

	private TextView txtScan;
	private TextView txtMain;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		Typeface tf = Typeface.createFromAsset(
		        getBaseContext().getAssets(), "fonts/GOTHIC.TTF");
		
		//mainText.setText("Starting Scan");
		
		
		txtScan = (TextView) findViewById(R.id.txtScan);
		txtScan.setTypeface(tf);
		
		//txtMain = (TextView) findViewById(R.id.txtMain);
		//txtMain.setTypeface(tf);
		
		txtScan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(splash.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        //Log.d(this.getClass().getName(), "back button pressed");
	    	//finish();
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
