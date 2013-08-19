package com.example.chronometer;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends Activity {
	   /** Called when the activity is first created. */
	   
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_main);
	      
	       final Chronometer myChronometer = (Chronometer)findViewById(R.id.chronometer);
	       Button buttonStart = (Button)findViewById(R.id.buttonstart);
	       Button buttonStop = (Button)findViewById(R.id.buttonstop);
	       Button buttonReset = (Button)findViewById(R.id.buttonreset);
	      
	       buttonStart.setOnClickListener(new Button.OnClickListener(){

	   
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    myChronometer.start();
	    
	   }});
	      
	       buttonStop.setOnClickListener(new Button.OnClickListener(){

	   
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    myChronometer.stop();
	    
	   }});
	      
	       buttonReset.setOnClickListener(new Button.OnClickListener(){

	   
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    myChronometer.setBase(SystemClock.elapsedRealtime());

	   }});
	      
	      
	   }
	}