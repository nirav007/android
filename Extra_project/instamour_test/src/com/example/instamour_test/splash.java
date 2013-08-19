package com.example.instamour_test;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class splash extends Activity {
	private int delay = 5000;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		try {

			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(splash.this);
					
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("session_login", String.valueOf("false"));
					
					
					editor.commit();
					Intent intent = new Intent(splash.this, middleoverlay.class);
					startActivity(intent);
					finish();
				}

			};

			timer.schedule(task, delay);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}