package com.example.sharedency;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final SharedPreferences prefs = new ObscuredSharedPreferences( 
			    this, this.getSharedPreferences("aa", Context.MODE_PRIVATE) );

			// eg.    
			prefs.edit().putString("foo","bar").commit();
			prefs.edit().putString("username","nirav").commit();
			prefs.edit().putString("password","nirav@gmail.com").commit();
			String aa = prefs.getString("foo", null);
			Toast.makeText(getApplicationContext(), aa,Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
