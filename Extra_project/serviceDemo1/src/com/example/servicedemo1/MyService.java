package com.example.servicedemo1;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class MyService extends Service {
	public static final String BROADCAST_ACTION = "com.example.servicedemo1";
	private final Handler handler = new Handler();
	Intent intent;

	int counter = 0;

	@Override
	public void onCreate() {
		// Called on service created
		intent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public void onDestroy() {
		// Called on service stopped
	}

	@Override
	public void onStart(Intent intent, int startid) {
		int i = 0;
		while (i < 101) {
			if (i > 100) {
				this.onDestroy();
			} else {
				counter = i;
				i++;
				handler.removeCallbacks(sendUpdatesToUI);
				handler.postDelayed(sendUpdatesToUI, 1 * 1000); // 1 sec
			}

		}

	}

	private Runnable sendUpdatesToUI = new Runnable() {
		public void run() {
			DisplayLoggingInfo();
			handler.postDelayed(this, 1 * 1000); // 1 sec
		}

	};

	private void DisplayLoggingInfo() {

		intent.putExtra("time", new Date().toLocaleString());
		intent.putExtra("counter", String.valueOf(counter));
		sendBroadcast(intent);
	}

	public static boolean isRunning() {
		return true;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}