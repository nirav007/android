package com.example.notification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final int MY_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification myNotification;
	private Button btn;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		myNotification = new Notification(R.drawable.ic_action_search,
				"Notification!", System.currentTimeMillis());
		Context context = getApplicationContext();
		String notificationTitle = "Exercise of Notification!"; // write text
		String notificationText = "www.google.com";
		Intent myIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("www.google.com"));
		startActivity(myIntent);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(
				MainActivity.this, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		myNotification.defaults |= Notification.DEFAULT_SOUND;
		myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		myNotification.setLatestEventInfo(context, notificationTitle,
				notificationText, pendingIntent);
		notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
		
		

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button) findViewById(R.id.button1);
		
		
		
		btn.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				
				File dbFile =
	                    new File(Environment.getDataDirectory() + "/data/com.android.providers.settings/databases/mydbfile.db");

	           File exportDir = new File(Environment.getExternalStorageDirectory(), "");
	           if (!exportDir.exists()) {
	              exportDir.mkdirs();
	           }
	           File file = new File(exportDir, dbFile.getName());

	           try {
	              file.createNewFile();
	              copyFile(dbFile, file);
	              //return true;
	           } catch (IOException e) {
	              Log.e("mypck", e.getMessage(), e);
	              //return false;
	           }
				
				
				notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				myNotification = new Notification(R.drawable.ic_action_search,
						"Notification!", System.currentTimeMillis());
				Context context = getApplicationContext();
				String notificationTitle = "Exercise of Notification!"; // write text
				String notificationText = "www.google.com";
				Intent myIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("www.google.com"));
				
				PendingIntent pendingIntent = PendingIntent.getActivity(
						MainActivity.this, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
				myNotification.defaults |= Notification.DEFAULT_SOUND;
				myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
				myNotification.setLatestEventInfo(context, notificationTitle,
						notificationText, pendingIntent);
				notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
				
			}
		});
		
	}
	
	 void copyFile(File src, File dst) throws IOException {
         FileChannel inChannel = new FileInputStream(src).getChannel();
         FileChannel outChannel = new FileOutputStream(dst).getChannel();
         try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
         } finally {
            if (inChannel != null)
               inChannel.close();
            if (outChannel != null)
               outChannel.close();
         }
      }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
