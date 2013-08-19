package com.example.toast;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toast t = Toast.makeText(MainActivity.this, "TOP | RIGHT",
				Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
		t.show();

		t = Toast.makeText(MainActivity.this, "CENTER", Toast.LENGTH_LONG);
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();

		t = Toast.makeText(MainActivity.this, "BOTTOM | LEFT",
				Toast.LENGTH_LONG);
		t.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		t.show();

		t = Toast.makeText(MainActivity.this, "CENTER | LEFT",
				Toast.LENGTH_LONG);
		t.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
		t.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
