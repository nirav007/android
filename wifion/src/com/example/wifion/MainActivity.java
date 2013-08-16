package com.example.wifion;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    private WifiManager wifiManager; 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE); 
       TextView text = (TextView)findViewById(R.id.txtWifi);
          wifiManager.setWifiEnabled(true); 
          if(wifiManager.isWifiEnabled()){
              text.setText("Wifi State Enabled");
          }
        
    }
}
