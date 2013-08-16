package com.Verticaltab1;



import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class Verticaltab1Activity extends TabActivity {
    /** Called when the activity is first created. */
	 // private final static int TAB_HEIGHT = 20;
	 Resources res  ;// Resource object to get Drawables
  TabHost tabHost ;  // The activity TabHost
     // Resusable TabSpec for each tab
   Intent intent;  // Reusable Intent for each tab
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
     
      
     res = getResources();
   tabHost = getTabHost();
   TabHost.TabSpec spec; 
   tabHost.setCurrentTab(1);
      
      tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			
			public void onTabChanged(String arg0) {
				
				
			/*	if(tabHost.getCurrentTab()==3)
				{
					Intent intent = new Intent(tab.this,setting.class);
					startActivity(intent);
				}*/
				setTabColors(tabHost);	
			}

			
			private void setTabColors(TabHost tabHost) {
				
				 TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
			        
				 for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
						//tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 40;
					//	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.headerbg);
						
						
						
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
			        
				

					
					
					currentView = tabWidget.getChildAt(tabHost.getCurrentTab());
					 LinearLayout.LayoutParams currentLayout =
			                (LinearLayout.LayoutParams) currentView.getLayoutParams();
			            currentLayout.setMargins(0, 0, 0, 0);
			}
		});
  
      intent = new Intent().setClass(this, demo.class);
      spec = tabHost.newTabSpec("Yoga").setIndicator("you'll",res.getDrawable(R.drawable.icon))
                    .setContent(intent);
      tabHost.addTab(spec);
      //tabHost.setBackgroundResource(R.color.color);

      // Do the same for the other tabs
      
      intent = new Intent().setClass(Verticaltab1Activity.this,demo.class);
      //intent.putExtra("url","http://www.youtube.com/watch?v=JUHOedjFHCg" );
      spec = tabHost.newTabSpec("video").setIndicator("Share",res.getDrawable(R.drawable.icon))
                    .setContent(intent);
	
      tabHost.addTab(spec);

      intent = new Intent().setClass(Verticaltab1Activity.this, demo.class);
      spec = tabHost.newTabSpec("tips").setIndicator("Feedback",res.getDrawable(R.drawable.icon))
      
                    .setContent(intent);
      tabHost.addTab(spec);
      
      intent = new Intent().setClass(Verticaltab1Activity.this, demo.class);
      spec = tabHost.newTabSpec("tips").setIndicator("Osi",res.getDrawable(R.drawable.icon))
      
                    .setContent(intent);
      tabHost.addTab(spec);
      

      Display display = getWindowManager().getDefaultDisplay(); 
      int width = display.getWidth();
      tabHost.getTabWidget().getLayoutParams().width = width ;
      tabHost.getTabWidget().getLayoutParams().height = 60;
      
      tabHost.setCurrentTab(0);
      
      TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
      
      final int tabChildrenCount = tabWidget.getChildCount();
      View currentView;
      for (int i = 0; i < tabChildrenCount; i++) {
          currentView = tabWidget.getChildAt(i);
          LinearLayout.LayoutParams currentLayout =
              (LinearLayout.LayoutParams) currentView.getLayoutParams();
          currentLayout.setMargins(0, 0, 1, 0);
          
          
      }
      tabWidget.requestLayout();
      
      
      //TabWidget tw = (TabWidget)tabHost.findViewById(R.id.tabhost);
      //View tabView = tabHost.getChildAt(0);
      //TextView tv = (TextView)tabView.findViewById(android.R.id.title);
      //tv.setTextSize(20);
      
      
		
      
  }

	@Override
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
