package com.example.wifi_hunter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class msg extends Activity {
	
	private TextView txt;
	int length;
	String txtMsg = "xDD...\n\n   Oppps! That was just for fun.\n   Go back to work!!    ";
	private TextView txtExit;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.msg);
		
		 txt = (TextView) findViewById(R.id.txtMain);
		 
		 txtExit = (TextView) findViewById(R.id.txtExit);
		 
		 txtExit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		 

			Typeface tf = Typeface.createFromAsset(
			        getBaseContext().getAssets(), "fonts/GOTHIC.TTF");
			
	
			txt.setTypeface(tf); 
			txtExit.setTypeface(tf);
			
			length = txtMsg.length();
			
			final Handler handler = new Handler();
			
			handler.post(new Runnable() {

				int i = 0;

				public void run() {
					// change your text here

					char a = txtMsg.charAt(i);

					txt.append(String.valueOf(a));
					i++;
					if (i < length) {
						handler.postDelayed(this, 1 * 150L);
					}
					// 1000L for one second

				}
			});
		 
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        //Log.d(this.getClass().getName(), "back button pressed");
	    	//moveTaskToBack(true);
	    	Intent intent = new Intent(msg.this,splash.class);
	    	startActivity(intent);
	    	//finish();
	    }
	    if(keyCode == KeyEvent.KEYCODE_HOME   )
	    {
	    		
	    	Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

	    }
	    return super.onKeyDown(keyCode, event);
	}

}
