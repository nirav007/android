package com.example.fpm;




import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;


public class tab extends TabActivity {
    /** Called when the activity is first c
     * reated. */
	
	int pheight,pheight_;
	
	int pwidth;
	
	 // private final static int TAB_HEIGHT = 20;
	 Resources res  ;// Resource object to get Drawables
   TabHost tabHost ;  // The activity TabHost
      // Resusable TabSpec for each tab
    Intent intent;  // Reusable Intent for each tab
    
   
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.tab);
       

	
      
       
      res = getResources();
    tabHost = getTabHost();
    TabHost.TabSpec spec; 
    tabHost.setCurrentTab(0);
       
    
    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
		
		
			public void onTabChanged(String arg0) {
				
				GPSTracker gps;
				 gps = new GPSTracker(tab.this);
					
				   if(gps.canGetLocation()){
			        	
			        	double latitude = gps.getLatitude();
			        	double longitude = gps.getLongitude();
			        	
			        	
			        	SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(tab.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("lat", String.valueOf(latitude) );
						editor.putString("lng", String.valueOf(longitude) );
						
						editor.commit();
			        	// \n is for new line
			        //	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
				   }
			/*	if(tabHost.getCurrentTab()==0)
				{
					
					
					
					tabHost.getTabWidget().setBackgroundResource(R.color.tabcolor);
					
				}
				else
				{
					tabHost.getTabWidget().setBackgroundResource(R.color.heading);
					
				}
				
				if(tabHost.getCurrentTab()==1)
				{
					
					
					
					tabHost.getTabWidget().setBackgroundResource(R.color.heading);
					
				}
				else
				{
					tabHost.getTabWidget().setBackgroundResource(R.color.tabcolor);
					
				}*/
			//	setTabColors(tabHost);	
			}

			
		
		});
       
   
       intent = new Intent().setClass(this, listticket.class);
    		   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		   

       spec = tabHost.newTabSpec("location").setIndicator(prepareTabView("All"))
                     .setContent(intent);
       
       tabHost.addTab(spec);
       
       
       
       intent = new Intent().setClass(tab.this,locationticket.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   

       //intent.putExtra("url","http://www.youtube.com/watch?v=JUHOedjFHCg" );
       spec = tabHost.newTabSpec("all").setIndicator(prepareTabView("Location"))
                     .setContent(intent);
	
       tabHost.addTab(spec);
     
  


       Display display = getWindowManager().getDefaultDisplay(); 
       pwidth = display.getWidth();
       pheight = display.getWidth();
       tabHost.getTabWidget().getLayoutParams().width = pwidth ;
       pheight_=pheight/5;
       tabHost.getTabWidget().getLayoutParams().height = pheight_;
       
       
       
       
       //int maintabwidth = width/2;
       
       //maintabwidth = maintabwidth-1;
       
       tabHost.setCurrentTab(0);
       
      // TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
       
      // tabHost.getTabWidget().getChildAt(2).getLayoutParams().width = 	   15; 
       
      // final int tabChildrenCount = tabWidget.getChildCount();
      // View currentView;
      // for (int i = 0; i < tabChildrenCount; i++) {
        //   currentView = tabWidget.getChildAt(i);
        //   LinearLayout.LayoutParams currentLayout =
      //         (LinearLayout.LayoutParams) currentView.getLayoutParams();
       //    currentLayout.setMargins(0, 0, 0, 0);
           
           
       //}
     //  tabWidget.requestLayout();
       
       //tabHost.getTabWidget().getLayoutParams().height=(int)30;
      // tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.icon);
       /*tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(0).getLayoutParams().
       tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(3).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(4).getLayoutParams().height=-40;*/
     //  tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ic_launcher);
       //tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.two2);
       Drawable d = getResources().getDrawable(R.drawable.myselector);
       Drawable d1 = getResources().getDrawable(R.drawable.myselector);
       tabHost.getTabWidget().getChildAt(0).setBackgroundDrawable(d1);
       tabHost.getTabWidget().getChildAt(1).setBackgroundDrawable(d);
       //tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.three3);
       //tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bgeffect);
       //tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.four4);
       
       
      // tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.home2);
       //tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=-60;
       //TabWidget tw = (TabWidget)tabHost.findViewById(R.id.tabhost);
       //View tabView = tabHost.getChildAt(0);
       //TextView tv = (TextView)tabView.findViewById(android.R.id.title);
       //tv.setTextSize(20);
       
       
		
       
   }
   
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_BACK) {
           Intent a = new Intent(tab.this,symbol.class);
           a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(a);
           return true;
       }
       return super.onKeyDown(keyCode, event);
   }   

   private View prepareTabView(String text) {
	    View view = LayoutInflater.from(this).inflate(R.layout.tabdesign, null);
	   
	    Typeface tf = Typeface.createFromAsset(
				getBaseContext().getAssets(), "fonts/GOTHIC.TTF");
	    
	    TextView tv = (TextView) view.findViewById(R.id.TabTextView);
	    //iv.setImageResource(resId);
	    tv.setText(text);
	    tv.setTypeface(tf);
	    return view;
	}
   
	
	@Override
protected void onResume() {
	// TODO Auto-generated method stub
		getTabHost().invalidate();
	super.onResume();
}

	protected void onRestart() {
	/*	Bundle bb = getIntent().getExtras();
		String s= bb.getString("tab");
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		if(s=="2")
		{
			tabHost.setCurrentTab(1);
		}
		else
		{
			tabHost.setCurrentTab(3);
		}*/
		
		getTabHost().invalidate();
		
		super.onRestart();
	}

	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	
	    	   Display display = getWindowManager().getDefaultDisplay(); 
	           int width = display.getWidth();
	           //int height = display.getWidth();
	           
	           int lheight = pheight_=pheight/5;
	           
	           //Toast.makeText(getApplicationContext(), String.valueOf(width), Toast.LENGTH_LONG).show();
	           //Toast.makeText(getApplicationContext(), String.valueOf(height), Toast.LENGTH_LONG).show();
	           tabHost.getTabWidget().getLayoutParams().width = width;
	           tabHost.getTabWidget().getLayoutParams().height = lheight;
	           
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	   Display display = getWindowManager().getDefaultDisplay(); 
	           int width = display.getWidth();
	           int height = display.getHeight();
	           //tabHost.getTabWidget().getLayoutParams().width = width ;
	          // tabHost.getTabWidget().getLayoutParams().height = height/5;
	        //   Toast.makeText(getApplicationContext(), String.valueOf(width), Toast.LENGTH_LONG).show();
	       //    Toast.makeText(getApplicationContext(), String.valueOf(height), Toast.LENGTH_LONG).show();
	       // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	  }
}
