package com.newproject;

import com.newproject.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity {

	protected int _splashTime = 100; 
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		//Intent intent = new Intent(splash.this,StopVidoWatch_Activity.class);
		//startActivity(intent);
		
		final splash sPlashScreen = this; 
		
		Thread splashTread;
		

        // thread for displaying the SplashScreen
		splashTread = new Thread() {
            
            public void run() {
                try {
                    synchronized(this){

                            //wait 5 sec
                            wait(_splashTime);
                    }

                } catch(InterruptedException e) {}
                finally {
                    finish();

                    //start a new activity
                    Intent i = new Intent();
                    i.setClass(sPlashScreen, signup_next.class);
                            startActivity(i);

                    stop();
                }
            }
        };

        splashTread.start();
    }

	
}
