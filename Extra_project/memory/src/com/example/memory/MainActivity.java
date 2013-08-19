package com.example.memory;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView memInfo = (TextView)findViewById(R.id.meminfo);
        
        String info = "";
        
        
        
        info += "Total memory: " + Runtime.getRuntime().totalMemory() + "\n";
        info += "Free memory: " + Runtime.getRuntime().freeMemory() + "\n";
        info += "Max memory: " + Runtime.getRuntime().maxMemory() + "\n";
        
        try
        {
        	Runtime.getRuntime().exec("mkdir aa");
        }
        catch(Exception e)
        {
        	
        }
        memInfo.setText(info);
    }

}