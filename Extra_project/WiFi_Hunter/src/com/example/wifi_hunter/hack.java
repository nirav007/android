package com.example.wifi_hunter;

//http://www.apkinstall.com/files/wifi_hunter_11.apk
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class hack extends Activity {

	private TextView txt;
	String txtMsg = ">Wings root ff00Cx .. \n> ff00xz \n> 02ffcx -h \n  \n> root -h  wings \n> ff00zc \n> ff0mnx\n> process Done !!";

	private Handler mHandler = new Handler();

	Runnable mUpdateTextView;

	int length;
	
	

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        //Log.d(this.getClass().getName(), "back button pressed");
	    	//moveTaskToBack(true);
	    	Intent intent = new Intent(hack.this,splash.class);
	    	startActivity(intent);
	    	//finish();
	    }
	    if(keyCode == KeyEvent.KEYCODE_HOME )
	    {

	    	Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.hack);

		txt = (TextView) findViewById(R.id.txtMsg);
		
		txtMsg="";
		
		Bundle getData = getIntent().getExtras();
		String wiFiname = getData.getString("wifiName");
		//String wiFiname="as";
		txtMsg = ">"+wiFiname+" root ff00Cx .. \n> Sending data..." +
				" \n> 02ffcx 0349ccX -h \n> Injecting packets..  \n> root -h  ffc9x ff0xz0 \n>" +
				" ff00zc \n> ff00Mc -c ff00zc \n> ff0mnx\n> password  decrypted\n> process Done !!";

		Typeface tf = Typeface.createFromAsset(getBaseContext().getAssets(),
				"fonts/GOTHIC.TTF");

		txt.setTypeface(tf);
		length = txtMsg.length();

		final Handler handler = new Handler();
		handler.post(new Runnable() {

			int i = 0;

			public void run() {
				// change your text here

				char a = txtMsg.charAt(i);

				txt.append(String.valueOf(a));
				i++;
				if (i < length) {
					handler.postDelayed(this, 1 * 150L);
				}
				else
				{
					getPassword();
				}
				// 1000L for one second

			}

			private void getPassword() {
				
				
				new Handler().postDelayed(new Runnable() {

					public void run() {
						//Bundle getData = getIntent().getExtras();
						//String wiFiname = getData.getString("wifiName");

						/*AlertDialog.Builder alert = new AlertDialog.Builder(hack.this);

						alert.setTitle("Hacked!");
						alert.setMessage("Connection Name : "+wiFiname+"\n Password : Hack");

						// Set an EditText view to get user input //final EditText input
						// =
						// new EditText(MainActivity.this); //alert.setView(input);

						alert.setPositiveButton("Connect to this ",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										//input.setText("");
										Intent intent = new Intent(hack.this, msg.class);
										startActivity(intent);

									}
								});

						alert.show();*/
						
						 Dialog dialog = new Dialog(hack.this);
						 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			                dialog.setContentView(R.layout.custome_dialog1);
			                
			                Bundle getData = getIntent().getExtras();
							String wiFiname = getData.getString("wifiName");
			             
			               // TextView text = (TextView) dialog.findViewById(R.id.ctlayout_main);
			              //  text.setText(wiFiname);
			                
			                //ctlayout_msg
			                
			               TextView text = (TextView) dialog.findViewById(R.id.ctlayout_msg);
			                text.setText("Connection Name : "+wiFiname+"\n Password : Hack");
			                
			                
			                TextView button = (TextView) dialog.findViewById(R.id.ctlayout_hack);
			                button.setOnClickListener(new OnClickListener() {
			                
			                    public void onClick(View v) {
			                        //finish();
			                    	Intent intent = new Intent(hack.this, msg.class);
									startActivity(intent);
			                    	
			                    }
			                });
			               
			                dialog.show();
			                
			                
					}
				}, 1000);
				
				

			
				
			}
		});

		/*
		 * for(int i=0;i<txtMsg.length();i++) { mHandler.postDelayed(new
		 * Runnable() {
		 * 
		 * 
		 * public void run() { // TODO Auto-generated method stub
		 * 
		 * char a= txtMsg.charAt(i);
		 * 
		 * txt.append(String.valueOf(a));
		 * 
		 * } }, 50000);
		 * 
		 * 
		 * }
		 */

	/*	new Handler().postDelayed(new Runnable() {

			public void run() {
				Bundle getData = getIntent().getExtras();
				String wiFiname = getData.getString("wifiName");

				AlertDialog.Builder alert = new AlertDialog.Builder(hack.this);

				alert.setTitle("Hacked!");
				alert.setMessage("Connection Name : "+wiFiname+"\n Password : Hack");

				// Set an EditText view to get user input //final EditText input
				// =
				// new EditText(MainActivity.this); //alert.setView(input);

				alert.setPositiveButton("Connect to this ",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								//input.setText("");
								Intent intent = new Intent(hack.this, msg.class);
								startActivity(intent);

							}
						});

				alert.show();
			}
		}, 1000);*/

	}

}
