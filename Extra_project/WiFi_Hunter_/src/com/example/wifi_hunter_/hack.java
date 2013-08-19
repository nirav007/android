package com.example.wifi_hunter_;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class hack extends Activity {
	
	private TextView txt;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.hack);
		
		 txt = (TextView) findViewById(R.id.txt);
		 
		 new Handler().postDelayed(new Runnable() {
	       
	            public void run() {
	                
	            	AlertDialog.Builder alert = new AlertDialog.Builder(hack.this);

				    alert.setTitle("Hacked!");
				alert.setMessage("Connection Name : Wings \n Password : Hack");

				// Set an EditText view to get user input 
				//final EditText input = new EditText(MainActivity.this);
				//alert.setView(input);

				alert.setPositiveButton("Connect to this ", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  //input.setText("");
					Intent intent = new Intent(hack.this,msg.class);
					startActivity(intent);
					
				  }
				});

				

				alert.show();
	            }
	        }, 3000);
		 
	}

}
