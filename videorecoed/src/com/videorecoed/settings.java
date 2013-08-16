package com.videorecoed;

import java.util.List;
import java.lang.String;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Size;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class settings extends Activity {
	Camera cam;
	List<Size> sizes;
	List<Integer> fpsRates;
	Intent answer;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		answer = new Intent();
		cam = Camera.open();
		sizes = cam.getParameters().getSupportedPreviewSizes();/**/
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO/*>8*/){
			fpsRates.add( 15);
//			List<int[]> fpsRates;
//			fpsRates = cam.getParameters().getSupportedPreviewFpsRange();
		}else{
			fpsRates = cam.getParameters().getSupportedPreviewFrameRates();
		}
		cam.release();
		String[] sizeSpinnerTexts = new String[sizes.size()];
		int i=0;
		for (Size size : sizes) {
			sizeSpinnerTexts[i++] = size.width + "x" + size.height;
		}
		ArrayAdapter<String> adapter;
		ArrayAdapter<Integer> adapterInt;
		((Spinner)findViewById(R.id.outputFormatSpinner)).setAdapter( new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item,	
				new String[]{"MPEG 4", "3GP"}));
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item,	
				sizeSpinnerTexts);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterInt = new ArrayAdapter<Integer>(getApplicationContext(),
				android.R.layout.simple_spinner_item,	
				fpsRates);
		adapterInt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner)findViewById(R.id.previewSize)).setAdapter( adapter);
		((Spinner)findViewById(R.id.fpsRate)).setAdapter( adapterInt);
		SharedPreferences settings = getSharedPreferences("videoReg", MODE_WORLD_WRITEABLE);
//		((RadioButton)findViewById(settings.getInt("chosenMode", R.id.timeLimited))).setChecked(true);
		((Spinner)findViewById(R.id.previewSize)).setSelection(settings.getInt("previewSize", 0));
		answer.putExtra( "previewSize", settings.getInt("previewSize", 0));
		((Spinner)findViewById(R.id.fpsRate)).setSelection(settings.getInt("fpsRate", 0));
		answer.putExtra( "fpsRate", settings.getInt("fpsRate", 0));
		((EditText)findViewById(R.id.timeLimitedParts)).setText( settings.getString("timeLimitedParts", "10"));
		((EditText)findViewById(R.id.timeLimitedMinutes)).setText( settings.getString("timeLimitedMinutes", "10"));
		((EditText)findViewById(R.id.sizeLimitedParts)).setText( settings.getString("sizeLimitedParts", "10"));
		((EditText)findViewById(R.id.sizeLimitedSize)).setText( settings.getString("sizeLimitedSize", "2000"));
		((Spinner)findViewById(R.id.outputFormatSpinner)).setSelection( settings.getInt("outputFormatSpinner", 0));
		
		((Spinner)findViewById(R.id.previewSize)).setOnItemSelectedListener( spinnerListener);
		((Spinner)findViewById(R.id.fpsRate)).setOnItemSelectedListener( spinnerListener);
		answer.putExtra("preTest", true);
		setResult(0, answer);	

	}
	OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			switch( parent.getId()){
			case R.id.fpsRate:
				answer.putExtra( "fpsRate", position);
				break;
			case R.id.previewSize:
				answer.putExtra( "previewSize", position);
				break;
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences("videoReg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = settings.edit();
		//editor.putInt("chosenMode", ((RadioGroup)findViewById(R.id.chosenMode)).getCheckedRadioButtonId());
		editor.putString("timeLimitedParts", ((EditText)findViewById(R.id.timeLimitedParts)).getText().toString());
		editor.putString("timeLimitedMinutes", ((EditText)findViewById(R.id.timeLimitedMinutes)).getText().toString());
		editor.putString("sizeLimitedParts", ((EditText)findViewById(R.id.sizeLimitedParts)).getText().toString());
		editor.putString("sizeLimitedSize", ((EditText)findViewById(R.id.sizeLimitedSize)).getText().toString());
		editor.putInt("outputFormatSpinner",((Spinner)findViewById(R.id.outputFormatSpinner)).getSelectedItemPosition());
		editor.putInt("previewSize",((Spinner)findViewById(R.id.previewSize)).getSelectedItemPosition());
		editor.putInt("fpsRate", ((Spinner)findViewById(R.id.fpsRate)).getSelectedItemPosition());
		editor.commit();
	}
}
