package com.example.miscall;

import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int MISSED_CALL_TYPE = 0;
	private TextView txtcall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtcall = (TextView) findViewById(R.id.call);

		StringBuffer sb = new StringBuffer();
		Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
				null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		sb.append("Call Details :");
		while (managedCursor.moveToNext()) {
			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = managedCursor.getString(duration);
			String dir = null;
			int dircode = Integer.parseInt(callType);
			switch (dircode) {

			case CallLog.Calls.OUTGOING_TYPE:
				dir = "OUTGOING";
				break;

			case CallLog.Calls.INCOMING_TYPE:
				dir = "INCOMING";
				break;

			case CallLog.Calls.MISSED_TYPE:
				dir = "MISSED";
				break;
			}
			sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
					+ dir + " \nCall Date:--- " + callDayTime
					+ " \nCall duration in sec :--- " + callDuration);
			sb.append("\n----------------------------------");
		}
		managedCursor.close();
		txtcall.setText(sb);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
