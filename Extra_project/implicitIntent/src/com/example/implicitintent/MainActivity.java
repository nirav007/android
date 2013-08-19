package com.example.implicitintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressWarnings("rawtypes")
public class MainActivity extends Activity {
	  private Spinner spinner;

	  
	  /** Called when the activity is first created. */

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      spinner = (Spinner) findViewById(R.id.spinner);
	      
	      ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_dropdown_item_1line);
	      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      spinner.setAdapter(adapter);
	      
	      ArrayAdapter adpAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.items, android.R.layout.simple_spinner_item);
	      adpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
	      spinner.setAdapter(adpAdapter);
	    }

	    public void onClick(View view) {
	      int position = spinner.getSelectedItemPosition();
	      Intent intent = null;
	      switch (position) {
	      case 0:
	        intent = new Intent(Intent.ACTION_VIEW,
	            Uri.parse("http://www.vogella.com"));
	        break;
	      case 1:
	        intent = new Intent(Intent.ACTION_CALL,
	            Uri.parse("tel:(+49)12345789"));
	        break;
	      case 2:
	        intent = new Intent(Intent.ACTION_DIAL,
	            Uri.parse("tel:(+49)12345789"));
	        startActivity(intent);
	        break;
	      case 3:
	        intent = new Intent(Intent.ACTION_VIEW,
	            Uri.parse("geo:50.123,7.1434?z=19"));
	        break;
	      case 4:
	        intent = new Intent(Intent.ACTION_VIEW,
	            Uri.parse("geo:0,0?q=query"));
	        break;
	      case 5:
	        intent = new Intent("android.media.action.IMAGE_CAPTURE");
	        break;
	      case 6:
	        intent = new Intent(Intent.ACTION_VIEW,
	            Uri.parse("content://contacts/people/"));
	        break;
	      case 7:
	        /*intent = new Intent(Intent.ACTION_EDIT,
	            Uri.parse("content://contacts/people/1"));*/
	        //break;
	        
	        intent = new Intent(Intent.ACTION_BATTERY_OKAY);
	        

	      }
	      if (intent != null) {
	        startActivity(intent);
	      }
	    }
	   

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      if (resultCode == Activity.RESULT_OK && requestCode == 0) {
	       // String 
	    	  
	    	  Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
	      }
	    }
}