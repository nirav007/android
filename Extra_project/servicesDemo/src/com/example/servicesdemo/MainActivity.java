package com.example.servicesdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    private Intent inetnt;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.start:
            
            inetnt=new Intent(this,ServiceExample.class);
            startService(inetnt);
            Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
            break;
        case R.id.stop:
            
            inetnt=new Intent(this,ServiceExample.class);
            stopService(inetnt);
            Toast.makeText(getApplicationContext(), "Service Stoped", Toast.LENGTH_LONG).show();
            break;
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        
    }
}