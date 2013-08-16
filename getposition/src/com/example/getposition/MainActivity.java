package com.example.getposition;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	FrameLayout frmLayout;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		frmLayout = (FrameLayout) findViewById(R.id.frameLayout1);
		frmLayout.setFocusable(true);
		LinearLayout et = new LinearLayout(this);

		EditText editText = new EditText(getApplicationContext());
		editText.setText("Test");
		et.addView(editText);

		frmLayout.addView(et, 100, 100);
		frmLayout.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				Log.i("TESTING",
						"touch x,y == " + event.getX() + "," + event.getY());
				frmLayout.setPadding(Math.round(event.getX() - 20),
						Math.round(event.getY()), 0, 0);
				return false;
			}
		});

	}

	/*
	 * image = (ImageView) findViewById(R.id.image);
	 * 
	 * View gameView = findViewById(R.id.gameboard); tv = new
	 * TextView(gameView.getContext()); tv.setText("A"); // tv.setWidth(w);
	 * tv.setHeight(h);
	 * 
	 * 
	 * RelativeLayout al = (RelativeLayout)findViewById(R.id.gb_layout); tv =
	 * new TextView(this);
	 * 
	 * @SuppressWarnings("deprecation") RelativeLayout.LayoutParams params = new
	 * RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	 * LayoutParams.WRAP_CONTENT); //params.x = 50; //params.y = 50;
	 * al.addView(tv, params);
	 */
}

/*
 * public boolean onTouchEvent(MotionEvent event) { // MotionEvent object holds
 * X-Y values if(event.getAction() == MotionEvent.ACTION_DOWN) { String text =
 * "You click at x = " + event.getX() + " and y = " + event.getY();
 * Toast.makeText(this, text, Toast.LENGTH_LONG).show();
 * RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
 * (int)event.getX(), (int)event.getY()); tv.setLayoutParams (p);
 * 
 * View gameView = findViewById(R.id.gameboard); tv = new
 * TextView(gameView.getContext()); tv.setText("A"); // tv.setWidth(w);
 * tv.setHeight(h);
 * 
 * 
 * RelativeLayout al = (RelativeLayout)findViewById(R.id.gb_layout); tv = new
 * TextView(this);
 * 
 * @SuppressWarnings("deprecation") RelativeLayout.LayoutParams params = new
 * RelativeLayout.LayoutParams((int)event.getX(), (int)event.getY()); //params.x
 * = 50; //params.y = 50; al.addView(tv, params);
 * 
 * 
 * 
 * /*CharSequence[] items = {"Set as Ringtone", "Set as Alarm"};
 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
 * 
 * 
 * 
 * AlertDialog dialog = builder.create(); dialog.setTitle("Demo");
 * //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 * WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
 * 
 * 
 * WMLP.x = (int) event.getX(); //x position WMLP.y = (int) event.getY();
 * 
 * dialog.getWindow().setAttributes(WMLP);
 * 
 * dialog.show(); }
 * 
 * return super.onTouchEvent(event); }
 */
