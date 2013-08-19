package com.example.wifi_hunter;

/*
 * 
 * Excellent    > 80       > -55
 Good       40 to 80  -72 to -55
 Fair       20 to 40  -84 to -72
 Poor         < 20       < -84
 */
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView mainText;
	WifiManager mainWifi;
	WifiReceiver receiverWifi;
	List<ScanResult> wifiList;
	ArrayList<String> aryWifi = new ArrayList<String>();
	ArrayList<Integer> aryLevel = new ArrayList<Integer>();
	StringBuilder sb = new StringBuilder();
	private ListView listView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainText = (TextView) findViewById(R.id.mainText);
		mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();

		mainWifi.startScan();
		Typeface tf = Typeface.createFromAsset(getBaseContext().getAssets(),
				"fonts/GOTHIC.TTF");

		mainText.setText("Available WiFi Networks");
		mainText.setTypeface(tf);

		listView = (ListView) findViewById(R.id.itemList);

		MyArrayAdapter adapter = new MyArrayAdapter(this, aryWifi);
		listView.setAdapter(adapter);
		mainWifi.startScan();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int pos, long arg3) {
				// TODO Auto-generated method stub
				// String item = ((TextView)arg1).getText().toString();
				/*
				 * String item = aryWifi.get(pos); //
				 * Toast.makeText(getBaseContext(), item,
				 * Toast.LENGTH_LONG).show();
				 * 
				 * 
				 * AlertDialog.Builder alert = new
				 * AlertDialog.Builder(MainActivity.this);
				 * 
				 * 
				 * alert.setTitle(item);
				 * alert.setMessage("Do you want to Hack It?");
				 * 
				 * // Set an EditText view to get user input //final EditText
				 * input = new EditText(MainActivity.this);
				 * //alert.setView(input);
				 * 
				 * 
				 * alert.setPositiveButton("Hack It !!", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int whichButton) {
				 * //input.setText(""); Intent intent = new
				 * Intent(MainActivity.this,hack.class); String item =
				 * aryWifi.get(pos); intent.putExtra("wifiName", item);
				 * startActivity(intent);
				 * 
				 * } });
				 * 
				 * 
				 * alert.setNegativeButton("Cancel", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int whichButton) {
				 * 
				 * } });
				 * 
				 * alert.show();
				 */

				Dialog dialog = new Dialog(MainActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custome_dialog);

				// Bundle getData = getIntent().getExtras();
				// String wiFiname = getData.getString("wifiName");

				String item = aryWifi.get(pos);
				TextView text = (TextView) dialog
						.findViewById(R.id.ctlayout_main);
				text.setText(item);

				TextView button = (TextView) dialog
						.findViewById(R.id.ctlayout_hack);
				
				
				button.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								hack.class);
						String item = aryWifi.get(pos);
						intent.putExtra("wifiName", item);
						startActivity(intent);

					}
				});
				button = (TextView) dialog.findViewById(R.id.ctlayout_cancel);
				button.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						finish();

					}
				});
				dialog.show();

			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Refresh");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (aryWifi != null) {
			aryWifi.clear();
			((MyArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
		}
		mainWifi.startScan();
		mainText.setText("Available WiFi Networks");
		return super.onMenuItemSelected(featureId, item);
	}

	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiverWifi, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	protected void onPause() {
		unregisterReceiver(receiverWifi);
		super.onPause();
	}

	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			sb = new StringBuilder();
			wifiList = mainWifi.getScanResults();
			for (int i = 0; i < wifiList.size(); i++) {
				aryWifi.add((wifiList.get(i).SSID.toString()));
				// aryLevel.add(wifiList.get(i).level, null);
				sb.append(new Integer(i + 1).toString() + ".");
				
				sb.append((wifiList.get(i)).toString());
				sb.append("\n");
				((MyArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
			}
			// mainText.setText(sb);
		}
	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		ArrayList<String> listArr;

		// private final integer[] image;

		public MyArrayAdapter(Activity context, ArrayList<String> aryWifi) {
			super(context, R.layout.wifilist, aryWifi);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = aryWifi;

		}

		
		public int getCount() {
			// TODO Auto-generated method stub
			return aryWifi.size();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.wifilist, null, true);

			Typeface tf = Typeface.createFromAsset(
					getBaseContext().getAssets(), "fonts/GOTHIC.TTF");

			TextView textView = (TextView) view.findViewById(R.id.txt_Name);
			textView.setText(aryWifi.get(position));
			textView.setTypeface(tf);

			wifiList = mainWifi.getScanResults();
			Integer a = wifiList.get(position).level; // -1 to -100
			double percentage = WifiManager.calculateSignalLevel(a, 40) * 2.5;
			TextView textLevel = (TextView) view.findViewById(R.id.txt_Level);
			textLevel.setText(String.valueOf(percentage)+"%");
			textLevel.setTypeface(tf);

			ImageView imageView = (ImageView) view.findViewById(R.id.img_Wifi);
			if (a < -1 && a >= -50) {

				imageView.setBackgroundResource(R.drawable.l_a);
			} else if (a <= -51 && a >= -60) {

				imageView.setBackgroundResource(R.drawable.l_b);
			} else if (a <= -61 && a >= -70) {

				imageView.setBackgroundResource(R.drawable.l_c);
			} else if (a <= -71 && a >= -80) {

				imageView.setBackgroundResource(R.drawable.l_d);
			} else if (a <= -81 && a >= -90) {

				imageView.setBackgroundResource(R.drawable.l_e);
			} else {

				imageView.setBackgroundResource(R.drawable.l_f);
			}

			return view;
		}
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        //Log.d(this.getClass().getName(), "back button pressed");
	    	//moveTaskToBack(true);
	    	finish();
	    }
	    if(keyCode == KeyEvent.KEYCODE_HOME )
	    {

	    	Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

	    }
	    return super.onKeyDown(keyCode, event);
	}
}