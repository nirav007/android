package com.chat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class login extends Activity {

	private Button btn;
	private EditText txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		init();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String str = txt.getText().toString();
				
				if(str.length()>0)
				{
					Intent intent = new Intent(login.this,ChatActivity.class);
					intent.putExtra("name", str);
					startActivity(intent);
				}
			}
		});
		
	}

	private void init() {
		// TODO Auto-generated method stub
		
		btn = (Button) findViewById(R.id.button1);
		txt = (EditText)  findViewById(R.id.editText1);
	}
}
