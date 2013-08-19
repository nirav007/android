package com.example.number;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
