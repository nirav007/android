package com.example.getcolorfromimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	 
	TextView msg;
	 TouchView touchView;
	 
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        msg = (TextView)findViewById(R.id.msg);
	        touchView = (TouchView)findViewById(R.id.touchview);

	    }

	    public void updateMsg(String tMsg, int color){
	     msg.setTextColor(color);
	     msg.setText(tMsg);
	    }
	   
	}