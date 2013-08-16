package com.newproject;

import java.util.regex.Pattern;

import com.newproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class signup_next extends Activity {

	private TextView btn_signup_login;
	private TextView btn_signup_next;
	private TextView txt_signup_email;
	private EditText txt_signup_location;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.signup_next);
		
		init();
		
		btn_signup_login.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
				Intent intent  = new Intent(signup_next.this,New_project1Activity.class);
				startActivity(intent);
				
			}
		});
		
		btn_signup_next.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String email = txt_signup_email.getText().toString();
				String location = txt_signup_location.getText().toString();
				
				if(email.length()<=0 || location.length()<=0  )
				{
					new AlertDialog.Builder(signup_next.this).setMessage("Some Field are Missing !!")
					.setTitle("Hotly Application")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
				else if(checkEmail(email)==true)
				{
					String email1 = txt_signup_email.getText().toString();
					String location1 = txt_signup_location.getText().toString();
			
					Intent intent = new Intent(signup_next.this,signup.class);
					intent.putExtra("email",email1);
					intent.putExtra("location",location1 );
					startActivity(intent);
				}
				else
				{
					new AlertDialog.Builder(signup_next.this).setMessage("Enter Valid Email Address !!")
					.setTitle("Hotly Application")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		
		btn_signup_login = (TextView) findViewById(R.id.btn_signup_login);
		
		btn_signup_next = (TextView) findViewById(R.id.btn_signup_next);
		
		txt_signup_email = (TextView) findViewById(R.id.txt_signup_email);
		
		txt_signup_location = (EditText) findViewById(R.id.editlocation);
		
		
	}
	
	public boolean checkEmail(String inputMail) 
	{   
	    Pattern pattern= Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
	    return pattern.matcher(inputMail).matches();
	}
	
}
