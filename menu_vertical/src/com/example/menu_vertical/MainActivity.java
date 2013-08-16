package com.example.menu_vertical;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements AnimationListener {

	private Button btn_ex;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_ex = (Button) findViewById(R.id.btn_ex);

		btn_ex.setOnClickListener(new OnClickListener() {

			private LinearLayout layout_ex;

			public void onClick(View v) {

				Animation movement5;
				layout_ex = (LinearLayout) findViewById(R.id.layout_ex);

				layout_ex.setVisibility(View.VISIBLE);

				// layout3.setVisibility(true); //USE THIS LINE //EDITED

				movement5 = AnimationUtils.loadAnimation(MainActivity.this,
						R.layout.top);
				movement5.reset();
				movement5.setFillAfter(true);
				movement5.setAnimationListener(MainActivity.this);
				layout_ex.startAnimation(movement5);

			}
		});
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub

	}

	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}
}
