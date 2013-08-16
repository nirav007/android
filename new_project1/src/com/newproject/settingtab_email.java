package com.newproject;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;


public class settingtab_email extends TabActivity {
    /** Called when the activity is first created. */
	 // private final static int TAB_HEIGHT = 20;
	 Resources res  ;// Resource object to get Drawables
   TabHost tabHost ;  // The activity TabHost
      // Resusable TabSpec for each tab
    Intent intent;  // Reusable Intent for each tab
	
   
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.settingtab);
       
      
       
      res = getResources();
    tabHost = getTabHost();
    TabHost.TabSpec spec; 
    tabHost.setCurrentTab(4);
       
       tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			
			public void onTabChanged(String arg0) {
				
				
				if(tabHost.getCurrentTab()==1)
				{
					//Intent intent = new Intent(tab.this,setting.class);
					//startActivity(intent);
					//tabHost.setBackgroundColor(R.color.tabcolor);
					//tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.CYAN);
					
					
					tabHost.getTabWidget().setBackgroundResource(R.color.tabcolor);
					
				}
				else
				{
					tabHost.getTabWidget().setBackgroundResource(android.R.color.white);
					
				}
			//	setTabColors(tabHost);	
			}

			
			private void setTabColors(TabHost tabHost) {
				
				 TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
			        
				 for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
						//tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 40;
						tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.headerbg);
						
						
						
						} 
				 final int tabChildrenCount = tabWidget.getChildCount();
			        View currentView;
				 for (int i = 0; i < tabChildrenCount; i++) {
			            currentView = tabWidget.getChildAt(i);
			            LinearLayout.LayoutParams currentLayout =
			                (LinearLayout.LayoutParams) currentView.getLayoutParams();
			            currentLayout.setMargins(1, 0, 1, 1);
			            
			            
			        }
			        tabWidget.requestLayout();
			        
				

					tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
					.setBackgroundResource(R.color.selecttab);
					
					currentView = tabWidget.getChildAt(tabHost.getCurrentTab());
					 LinearLayout.LayoutParams currentLayout =
			                (LinearLayout.LayoutParams) currentView.getLayoutParams();
			            currentLayout.setMargins(0, 0, 0, 0);
			}
		});
   
       intent = new Intent().setClass(this, user_profile.class);
       spec = tabHost.newTabSpec("Yoga").setIndicator(prepareTabView("You'll"))
                     .setContent(intent);
       tabHost.addTab(spec);
       //tabHost.setBackgroundResource(R.color.color);

       // Do the same for the other tabs
       
       intent = new Intent().setClass(settingtab_email.this,profile.class);
       //intent.putExtra("url","http://www.youtube.com/watch?v=JUHOedjFHCg" );
       spec = tabHost.newTabSpec("video").setIndicator(prepareTabView("Spread The"))
                     .setContent(intent);
	
       tabHost.addTab(spec);
       
       intent = new Intent().setClass(settingtab_email.this,profile.class);
       //intent.putExtra("url","http://www.youtube.com/watch?v=JUHOedjFHCg" );
       spec = tabHost.newTabSpec("video").setIndicator("")
                     .setContent(intent);
	
       tabHost.addTab(spec);
       
      
       
       //tabHost.getTabWidget().getChildAt(2).getLayoutParams().height =    70; 

       intent = new Intent().setClass(settingtab_email.this, feedback.class);
       spec = tabHost.newTabSpec("tips").setIndicator(prepareTabView("Feedback"))
       
                     .setContent(intent);
       tabHost.addTab(spec);
       
       intent = new Intent().setClass(settingtab_email.this, email.class);
       SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		
	String	first_name = sp.getString("user_name", " ");
       spec = tabHost.newTabSpec("tips").setIndicator(prepareTabView(first_name))
   
       
                     .setContent(intent);
       tabHost.addTab(spec);
       

       Display display = getWindowManager().getDefaultDisplay(); 
       int width = display.getWidth();
       tabHost.getTabWidget().getLayoutParams().width = width ;
    //   tabHost.getTabWidget().getLayoutParams().height = 60;
       
       tabHost.setCurrentTab(4);
       
       TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
       
       final int tabChildrenCount = tabWidget.getChildCount();
       View currentView;
       for (int i = 0; i < tabChildrenCount; i++) {
           currentView = tabWidget.getChildAt(i);
           LinearLayout.LayoutParams currentLayout =
               (LinearLayout.LayoutParams) currentView.getLayoutParams();
           currentLayout.setMargins(0, 0, 0, 0);
           
           
       }
       tabWidget.requestLayout();
       

       tabHost.getTabWidget().getChildAt(2).getLayoutParams().width = 	   15; 
       tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.one1);
       /*tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(0).getLayoutParams().
       tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(3).getLayoutParams().height=-40;
       tabHost.getTabWidget().getChildAt(4).getLayoutParams().height=-40;*/
       tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.two2);
       tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.two2);
       Drawable d = getResources().getDrawable(R.drawable.home);
       tabHost.getTabWidget().getChildAt(2).setBackgroundDrawable(d);
       tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.three3);
       //tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bgeffect);
       tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.four4);
       
       
      // tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.home2);
       //tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=-60;
       //TabWidget tw = (TabWidget)tabHost.findViewById(R.id.tabhost);
       //View tabView = tabHost.getChildAt(0);
       //TextView tv = (TextView)tabView.findViewById(android.R.id.title);
       //tv.setTextSize(20);
       
       
		
       
   }
   private View prepareTabView(String text) {
	    View view = LayoutInflater.from(this).inflate(R.layout.tabdesign, null);
	   
	    TextView tv = (TextView) view.findViewById(R.id.TabTextView);
	    //iv.setImageResource(resId);
	    tv.setText(text);
	    return view;
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
		super.onRestart();
	}

}
