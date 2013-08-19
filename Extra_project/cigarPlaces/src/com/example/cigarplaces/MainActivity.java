package com.example.cigarplaces;

import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends MapActivity {

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
