package com.example.blockcall;

import java.lang.reflect.Method;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends BroadcastReceiver {
	Context context = null;
	private static final String TAG = "Phone call";
	private ITelephony telephonyService;

	
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG, "Receving....");
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class c = Class.forName(telephony.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
			// telephonyService.silenceRinger();
			telephonyService.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}