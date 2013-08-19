package com.example.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	 }

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  
	  switch(item.getItemId()){
	  case R.id.action_settings:
	   Toast.makeText(getApplicationContext(), 
	     "Setting...", 
	     Toast.LENGTH_SHORT).show();
	   break;
	  case R.id.action_up:
	   Toast.makeText(getApplicationContext(), 
	     "Up...", 
	     Toast.LENGTH_SHORT).show();
	   break;
	  case R.id.action_down:
	   Toast.makeText(getApplicationContext(), 
	     "Down...", 
	     Toast.LENGTH_SHORT).show();
	   break;
	  case R.id.action_other:
	   Toast.makeText(getApplicationContext(), 
	     "Other...", 
	     Toast.LENGTH_SHORT).show();
	   break;
	  default:
	   Toast.makeText(getApplicationContext(), 
	     "Unknown...", 
	     Toast.LENGTH_SHORT).show();
	   break;
	  }
	  
	  //Return false to allow normal menu processing to proceed, 
	  //true to consume it here.
	  return false;
	 }
	 
	}