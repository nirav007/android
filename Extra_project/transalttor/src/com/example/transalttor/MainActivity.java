package com.example.transalttor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class MainActivity extends Activity {

	EditText MyInputText;
	Button MyTranslateButton;
	TextView MyOutputText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MyInputText = (EditText) findViewById(R.id.InputText);
		MyTranslateButton = (Button) findViewById(R.id.TranslateButton);
		MyOutputText = (TextView) findViewById(R.id.OutputText);

		MyTranslateButton.setOnClickListener(MyTranslateButtonOnClickListener);
	}

	private Button.OnClickListener MyTranslateButtonOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String InputString;
			String OutputString = null;
			InputString = MyInputText.getText().toString();

			try {
			    
		         
				GoogleAPI.setHttpReferrer("http://www.google.com");
				GoogleAPI.setKey("AIzaSyCr9C8xNq0uODvZMNH_0Jb7BZpBCZMagOo");
			
				OutputString = Translate.DEFAULT. execute(InputString, Language.ENGLISH,
						Language.FILIPINO);
			} catch (Exception ex) {
				Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
				ex.printStackTrace();
				OutputString = "Error";
			}

			MyOutputText.setText(OutputString);

		}

	};

}