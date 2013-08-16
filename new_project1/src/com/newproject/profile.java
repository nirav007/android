package com.newproject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newproject.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class profile extends TabActivity {
   
	 Resources res  ;// Resource object to get Drawables
  TabHost tabHost ;  // The activity TabHost
    
   Intent intent;  // Reusable Intent for each tab
   
	private InputStream is;
	private StringBuilder sb;
	private String result;
	private JSONArray jArray;
	DataHendleCls obS = new DataHendleCls();
	
	 Integer user_id;
	
public TextView txtnotification;
	
  
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.profile);
      
    init();
      
     res = getResources();
   tabHost = getTabHost();
   TabHost.TabSpec spec; 
      
   txtnotification = (TextView) findViewById(R.id.txtnotification);
   
   Timer myTimer;
   
   /*myTimer = new Timer();
	myTimer.schedule(new TimerTask() {
		
		public void run() {
			 countnotification();
		}

	}, 0, 1000);*/
   
  
   final Handler handler = new Handler();    
   /*Runnable runnable = new Runnable() {   
       public void run() {
           
         
    	   Toast.makeText(getApplicationContext(), "ff", Toast.LENGTH_LONG).show();
    	   countnotification();
           handler.postDelayed(this, 10000);
       } 
   };
   */
   
   /*
   new Thread(){
       public void run(){
           
           while(true)
           {
        	   	countnotification();
           }
       }
   }.start(); */
   
 
   CountDownTimer t;

	  t = new CountDownTimer( Long.MAX_VALUE , 1000) {

         public void onTick(long millisUntilFinished) {
             Log.d("test","Timer tick");
         	countnotification();
             
           
         }

         public void onFinish() {
             Log.d("test","Timer last tick");
             
         }
      }.start();

      

   
      intent = new Intent().setClass(this, you.class);


      spec = tabHost.newTabSpec("You").setIndicator("",res.getDrawable(R.drawable.tabyou))
                    .setContent(intent);
      tabHost.addTab(spec);
     
      
      intent = new Intent().setClass(profile.this,more.class);
      spec = tabHost.newTabSpec("video").setIndicator("",res.getDrawable(R.drawable.tabfollowing))
                    .setContent(intent);
	
      tabHost.addTab(spec);

      intent = new Intent().setClass(profile.this,favourite.class);
      spec = tabHost.newTabSpec("video").setIndicator("",res.getDrawable(R.drawable.tabexplore))
                    .setContent(intent);
	
      tabHost.addTab(spec);
    
    
      

      Display display = getWindowManager().getDefaultDisplay(); 
      int width = display.getWidth();
      tabHost.getTabWidget().getLayoutParams().width = width ;
      tabHost.getTabWidget().getLayoutParams().height = 40;
      
      tabHost.setCurrentTab(0);
      
      TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
      
      final int tabChildrenCount = tabWidget.getChildCount();
      View currentView;
      
      currentView = tabWidget.getChildAt(0);
      LinearLayout.LayoutParams currentLayout =
          (LinearLayout.LayoutParams) currentView.getLayoutParams();
      currentLayout.setMargins(0, 0, 1, 3);
      
      tabWidget.requestLayout();
      
      currentView = tabWidget.getChildAt(1);
      currentLayout =
          (LinearLayout.LayoutParams) currentView.getLayoutParams();
      currentLayout.setMargins(-3, 0, 5, 0);
      tabWidget.requestLayout();
      
      currentView = tabWidget.getChildAt(2);
      currentLayout =
          (LinearLayout.LayoutParams) currentView.getLayoutParams();
      currentLayout.setMargins(-6, 0, -3, 0);
      tabWidget.requestLayout();
      
      
      tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.myselector);
	    tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.myselector);
	    tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.myselector);
	    
  
	    tabHost.getTabWidget().setBackgroundColor(R.color.tabcolor);
	    
	      
	     tabHost.getTabWidget().setPadding(0, -2, 0, 0);
	      
	      tabHost.getTabWidget().getChildAt(0).setPadding(3, 4, 0, 2);
	      tabHost.getTabWidget().getChildAt(1).setPadding(0, 1, 0, 2);
	      tabHost.getTabWidget().getChildAt(2).setPadding(0, 1, 0, 2);
	 
      
  }

	private void init() {
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		user_id = sp.getInt("user_id",0);
}

	
	private void countnotification() {
	
		 try
		 {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.notificationUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
	        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	        	HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();       	
	        	
	        }
	        catch (Exception e) {		}
	        
	        try
	        {
	        	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	        	sb = new StringBuilder();
	        	sb.append(BufR.readLine() + "\n");
	        	String line = "0";
	        	
	        	while((line = BufR.readLine()) != null)
	        	{
	        		sb.append(line + "\n");
	        	}
	        	
	        	is.close();
	        	result = sb.toString();
	        }
	        catch (Exception e) {
				}
	        
	        String txt_count;
	        String user_name;
	        Integer id;
	        
	      
	        
	        
	        try{
	        		JSONObject json_data = null;
	        	json_data = new JSONObject(result);
	        	
	        	String data = json_data.getString("Data");
	        	JSONArray jArray1= new JSONArray(data);
	        	
	        	for(int i = 0 ; i <jArray1.length() ; i++)
	        	{
	        		json_data = jArray1.getJSONObject(i);
	        		
	        		txt_count = json_data.getString("counter");
	        		//user_name = json_data.getString("image_name");
	        		//ar.add("" + owner_name );
	        		//arr.add("" + user_name );
	        		
	        		Integer int_cnt = Integer.parseInt(txt_count.toString());
	        		
	        		if(int_cnt>=1)
	        		{
	        			txtnotification.setText(txt_count);
	        			Drawable d = getResources().getDrawable(R.drawable.notificationcircle);
	        			txtnotification.setBackgroundDrawable(d);
	        		}
	        		
	        		
	        	
	        		
	        	}
	       
	        		
	             }
	        catch (JSONException e) {
			}
	        catch (Exception e) {
	        	
			}
		
		
	
}

	
	protected void onRestart() {
		
		tabHost.setCurrentTab(0);
		
		super.onRestart();
	}

}