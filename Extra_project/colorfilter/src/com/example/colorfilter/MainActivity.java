package com.example.colorfilter;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 
	 ImageView imageView;
	 SeekBar Bar1, Bar2, Bar3;
	 Spinner redSpinner, greenSpinner, blueSpinner;
	 TextView colorInfo;
	 
	 String[] optColor  = { "Red", "Green", "Blue","Red","Green","Blue"};

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        imageView = (ImageView)findViewById(R.id.iv);
	        
	        Bar1 = (SeekBar)findViewById(R.id.bar1);
	        Bar2 = (SeekBar)findViewById(R.id.bar2);
	        Bar3 = (SeekBar)findViewById(R.id.bar3);
	        
	        redSpinner = (Spinner)findViewById(R.id.ropt);
	        greenSpinner = (Spinner)findViewById(R.id.gopt);
	        blueSpinner = (Spinner)findViewById(R.id.bopt);
	        
	        colorInfo = (TextView)findViewById(R.id.colorinfo);
	        
	        ArrayAdapter<String> redArrayAdapter = new ArrayAdapter<String>(
	                this, 
	                android.R.layout.simple_spinner_item, 
	                optColor);
	        redArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
	        redSpinner.setAdapter(redArrayAdapter);
	        redSpinner.setSelection(0);
	        
	        ArrayAdapter<String> greenArrayAdapter = new ArrayAdapter<String>(
	                this, 
	                android.R.layout.simple_spinner_item, 
	                optColor);
	        greenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
	        greenSpinner.setAdapter(greenArrayAdapter);
	        greenSpinner.setSelection(1);
	        
	        ArrayAdapter<String> blueArrayAdapter = new ArrayAdapter<String>(
	                this, 
	                android.R.layout.simple_spinner_item, 
	                optColor);
	        blueArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
	        blueSpinner.setAdapter(blueArrayAdapter);
	        blueSpinner.setSelection(2);
	        
	        Bar1.setOnSeekBarChangeListener(colorBarChangeListener);
	        Bar2.setOnSeekBarChangeListener(colorBarChangeListener);
	        Bar3.setOnSeekBarChangeListener(colorBarChangeListener);
	        
	        redSpinner.setOnItemSelectedListener(colorSpinnerSelectedListener);
	        greenSpinner.setOnItemSelectedListener(colorSpinnerSelectedListener);
	        blueSpinner.setOnItemSelectedListener(colorSpinnerSelectedListener);
	        
	        setColorFilter(imageView);
	    }
	    
	    OnSeekBarChangeListener colorBarChangeListener
	    = new OnSeekBarChangeListener(){

	  @Override
	  public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
	   setColorFilter(imageView);
	  }

	  @Override
	  public void onStartTrackingTouch(SeekBar seekBar) {
	   // TODO Auto-generated method stub
	   
	  }

	  @Override
	  public void onStopTrackingTouch(SeekBar seekBar) {
	   // TODO Auto-generated method stub
	   
	  } 
	    };
	  
	 OnItemSelectedListener colorSpinnerSelectedListener
	 = new OnItemSelectedListener(){

	  @Override
	  public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	    long arg3) {
	   setColorFilter(imageView);
	   
	  }

	  @Override
	  public void onNothingSelected(AdapterView<?> arg0) {
	   // TODO Auto-generated method stub
	   
		  Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_LONG).show();
		  
		  
	  } 
	 };
	 
	 private void setColornewFilter(ImageView iv)
	 {
		 float rvalue1 = ((float)Bar1.getProgress())/255;
		 float gvalue1 = ((float)Bar2.getProgress())/255;
		 float bvalue1 = ((float)Bar3.getProgress())/255;
		 
		 int redcolor = redSpinner.getSelectedItemPosition();
		 int greencolor = greenSpinner.getSelectedItemPosition();
		 int bluecolor = blueSpinner.getSelectedItemPosition();
		 
		 float a, b, c, f, g, h, k, l, m;
	     a = b = c = f = g = h = k = l = m = 0;
		 
		 
	 }
	    
	    private void setColorFilter(ImageView iv){
	     
	       /*
	        * 5x4 matrix for transforming the color+alpha components of a Bitmap. 
	        * The matrix is stored in a single array, and its treated as follows: 
	        * [  a, b, c, d, e, 
	        *   f, g, h, i, j, 
	        *   k, l, m, n, o, 
	        *   p, q, r, s, t ] 
	        * 
	        * When applied to a color [r, g, b, a], the resulting color is computed 
	        * as (after clamping) 
	        * R' = a*R + b*G + c*B + d*A + e; 
	        * G' = f*R + g*G + h*B + i*A + j; 
	        * B' = k*R + l*G + m*B + n*A + o; 
	        * A' = p*R + q*G + r*B + s*A + t;
	        */
	     
	     
	     float value1 = ((float)Bar1.getProgress())/255;
	     float value2 = ((float)Bar2.getProgress())/255;
	     float value3 = ((float)Bar3.getProgress())/255;
	     
	     int redColorSource = redSpinner.getSelectedItemPosition();
	     int greenColorSource = greenSpinner.getSelectedItemPosition();
	     int blueColorSource = blueSpinner.getSelectedItemPosition();
	     
	     float a, b, c, f, g, h, k, l, m;
	     a = b = c = f = g = h = k = l = m = 0;
	     
	     String colorCombination = "";
	     
	     colorCombination += "RED = ";
	     switch(redColorSource){
	     case 0: a = value1;
	       colorCombination += "red x " + String.valueOf(value1) +"\n";
	       break;
	     case 1: b = value1;
	       colorCombination += "green x " + String.valueOf(value1) +"\n";
	    break;
	     case 2: c = value1;
	       colorCombination += "blue x " + String.valueOf(value1) +"\n";
	    break;
	     }
	     
	     colorCombination += "GREEN = ";
	     switch(greenColorSource){
	     case 0: f = value2;
	       colorCombination += "red x " + String.valueOf(value2) +"\n";
	       break;
	     case 1: g = value2;
	       colorCombination += "green x " + String.valueOf(value2) +"\n";
	    break;
	     case 2: h = value2;
	       colorCombination += "blue x " + String.valueOf(value2) +"\n";
	    break;
	     }
	     
	     colorCombination += "BLUE = ";
	     switch(blueColorSource){
	     case 0: k = value3;
	       colorCombination += "red x " + String.valueOf(value3) +"\n";
	       break;
	     case 1: l = value3;
	       colorCombination += "green x " + String.valueOf(value3) +"\n";
	    break;
	     case 2: m = value3;
	       colorCombination += "blue x " + String.valueOf(value3) +"\n";
	    break;
	     }
	     
	     float[] colorMatrix = { 
	       a, b, c, 0, 0, //red
	       f, g, h, 0, 0, //green
	       k, l, m, 0, 0, //blue
	       0, 0, 0, 1, 0  //alpha    
	     };
	     
	     colorInfo.setText(colorCombination);
	     
	     ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
	     
	     iv.setColorFilter(colorFilter);
	    }

	}