package com.example.viewflipper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

    private Button btn;
	private ViewFlipper flip;



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn=(Button)findViewById(R.id.btn);
       
                flip=(ViewFlipper)findViewById(R.id.flip);
                
                flip.setInAnimation(this,android.R.anim.fade_in);
                
                     flip.setOutAnimation(this, android.R.anim.fade_out);    

        
            }
        
            public void ClickHandler(View v)
        
            {
        
            	flip.setFlipInterval(3000);
            	
            	//     flip.startFlipping();

             flip.showNext();
        
            }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
