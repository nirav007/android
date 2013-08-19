package com.example.instamour_test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ImageView.ScaleType;


//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class usernotification extends Activity {
	static ListView listView;
	private String[] listArr = { "My heart gets pushed", "New match",
			"New message", "New chat", "New video chat", "I receive gift",
			"Someone checkes me out" };

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpsupport);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		listView = (ListView) findViewById(R.id.userList);

		TextView txtheadtext = (TextView) findViewById(R.id.txtheadtext);
		txtheadtext.setText("Notifications");

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(usernotification.this,
								R.id.inner_content, width);
						startActivity(new Intent(usernotification.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		MyArrayAdapter adapter = new MyArrayAdapter(usernotification.this,
				listArr);
		listView.setAdapter(adapter);

	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
		final List<String> videoPathes = new ArrayList<String>();
		int videocount = 0, totalvideo;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.userprivacytlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.usernotificationlist, null,
					true);
			view.performClick();
			TextView txthelp = (TextView) view.findViewById(R.id.txthelp);
			txthelp.setText(listArr[position]);

			return view;
		}
	}

}
