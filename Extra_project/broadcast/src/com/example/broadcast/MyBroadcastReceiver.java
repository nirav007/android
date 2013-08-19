package com.example.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MyBroadcastReceiver  extends BroadcastReceiver {
	  @SuppressLint({ "ServiceCast", "ResourceAsColor" })
	@Override
	  public void onReceive(Context context, Intent intent) {
	    Toast.makeText(context, "Don't panik but your time is up!!!!.",
	        Toast.LENGTH_LONG).show();
	    // Vibrate the mobile phone
	    
	    EditText edt =  (EditText) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    edt.setBackgroundColor(android.R.color.black);
	    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(2000);
	  }

	}