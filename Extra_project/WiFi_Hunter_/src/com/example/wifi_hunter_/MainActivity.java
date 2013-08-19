package com.example.wifi_hunter_;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mainText;
	WifiManager mainWifi;
	//WifiReceiver receiverWifi;
	List<ScanResult> wifiList;
	ArrayList<String> aryWifi = new ArrayList<String>();
	ArrayList<Integer> aryLevel = new ArrayList<Integer>();
	private String[] listArr ;
	StringBuilder sb = new StringBuilder();
	String[] stockArr;
	private ListView listView;
	private LinearLayout layoutHack;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//mainText = (TextView) findViewById(R.id.mainText);
		
		layoutHack = (LinearLayout) findViewById(R.id.parent);
	

		layoutHack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

			    alert.setTitle("Wings");
			alert.setMessage("Do you want to Hack It?");

			// Set an EditText view to get user input 
			//final EditText input = new EditText(MainActivity.this);
			//alert.setView(input);

			alert.setPositiveButton("Hack It !!", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  //input.setText("");
				Intent intent = new Intent(MainActivity.this,hack.class);
				startActivity(intent);
				
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {

			  }
			});

			alert.show();
			}
		});
	
	}

	
}