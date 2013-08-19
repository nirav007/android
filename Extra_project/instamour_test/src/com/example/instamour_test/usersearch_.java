package com.example.instamour_test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class usersearch_ extends Activity {
	static ListView listView;
	private String[] listArr = { "Gender", "Age range", "Looking for?",
			"Video", "Location", "Last online", "Ethnicity", "Smoker" };
	
	private String[] listArr1 = { "Female", "25-32", "Relationship",
			"At least 2", "Philadelphia", "Today", "White", "No" };

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
		txtheadtext.setText("Search");

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(usersearch_.this,
								R.id.inner_content, width);
						startActivity(new Intent(usersearch_.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		MyArrayAdapter adapter = new MyArrayAdapter(usersearch_.this, listArr);
		listView.setAdapter(adapter);

	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;
		final List<String> videoPathes = new ArrayList<String>();
		int videocount = 0, totalvideo;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.usersearchlist, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		@SuppressLint("CutPasteId")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.usersearchlist, null, true);
			view.performClick();
			TextView txthelp = (TextView) view.findViewById(R.id.txthelp);
			txthelp.setText(listArr[position]);
			
			TextView txthelp1 = (TextView) view.findViewById(R.id.txtusersearch);
			txthelp1.setText(listArr1[position]);

			return view;
		}
	}

}
