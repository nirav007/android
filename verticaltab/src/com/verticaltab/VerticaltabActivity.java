package com.verticaltab;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

public class VerticaltabActivity extends TabActivity {
    private Button artistButton;
	private Button albumButton;
	private Button songButton;
	TabHost tabHost;
	/** Called when the activity is first created. */
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);

	        init();
	        

	        Resources res = getResources();
	        tabHost  = getTabHost();
	        Configuration cfg = res.getConfiguration();
	        boolean hor = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE;

	        if(hor)
	        {
	            TabWidget tw = tabHost.getTabWidget();
	            tw.setOrientation(LinearLayout.VERTICAL);
	        }

	        TabHost.TabSpec spec;

	        Intent intent;

	     // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, demo.class);

	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("artists").setIndicator("Artists",
	                          res.getDrawable(R.drawable.icon))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        // Do the same for the other tabs
	        intent = new Intent().setClass(this, demo.class);
	        spec = tabHost.newTabSpec("albums").setIndicator("Albums",
	                          res.getDrawable(R.drawable.icon))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        intent = new Intent().setClass(this, demo.class);
	        spec = tabHost.newTabSpec("songs").setIndicator("Songs",
	                          res.getDrawable(R.drawable.icon))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        tabHost.setCurrentTab(0);
	    }
	 private void init() {
		// TODO Auto-generated method stub
		
		 artistButton = (Button) findViewById(R.id.artist_id);
		 albumButton = (Button) findViewById(R.id.album_id);
		 songButton = (Button) findViewById(R.id.song_id);
		 
	}
	public void tabHandler(View target){
		    artistButton.setSelected(false);
		    albumButton.setSelected(false);
		    songButton.setSelected(false);
		    if(target.getId() == R.id.artist_id){
		    	tabHost.setCurrentTab(0);
		        artistButton.setSelected(true);
		    } else if(target.getId() == R.id.album_id){
		        tabHost.setCurrentTab(1);
		        albumButton.setSelected(true);
		    } else if(target.getId() == R.id.song_id){
		        tabHost.setCurrentTab(2);
		        songButton.setSelected(true);
		    }
		}
	 
}