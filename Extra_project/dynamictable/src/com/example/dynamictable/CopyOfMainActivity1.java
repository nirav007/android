package com.example.dynamictable;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfMainActivity1 extends Activity {

	TableLayout report_table, report_table1;
	TableRow tr_data, tr_data1;

	int j = 0;
	private ScrollView scrollview1;
	private ScrollView scrollview2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		report_table = (TableLayout) findViewById(R.id.report_table);
		report_table1 = (TableLayout) findViewById(R.id.report_table1);
		
		scrollview1 = (ScrollView) findViewById(R.id.scrollview1);
		scrollview2 = (ScrollView) findViewById(R.id.scrollview2);
		
		

		// ---------------Table
		// Header-----------------------------------------------
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		
		for (int i = 1; i <= 15; i++) {
			tr_data = new TableRow(this);
			tr_data.setId(10);
			tr_data.setBackgroundColor(Color.TRANSPARENT);
			tr_data.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));

			tr_data1 = new TableRow(this);
			tr_data1.setId(10);
			tr_data1.setBackgroundColor(Color.TRANSPARENT);
			tr_data1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));

			final TextView sr_no = new TextView(this);
			sr_no.setId(20);
			sr_no.setText("" + i);
			sr_no.setTextColor(Color.BLACK);
			sr_no.setPadding(5, 5, 5, 5);
			tr_data1.addView(sr_no);// add the column to the table row here

			final TextView test_name = new TextView(this);
			test_name.setId(21);// define id that must be unique
			test_name.setText("Speed Test 60(min) Demo-ST-01"); // set the text
																// for the
																// header
			test_name.setTextColor(Color.BLACK); // set the color
			test_name.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_name); // add the column to the table row here

			final TextView test_date = new TextView(this);
			test_date.setId(21);// define id that must be unique
			test_date.setText("12 Mar 2013"); // set the text for the header
			test_date.setTextColor(Color.BLACK); // set the color
			test_date.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_date); // add the column to the table row here

			final TextView test_ro = new TextView(this);
			test_ro.setId(21);// define id that must be unique
			test_ro.setText("2"); // set the text for the header
			test_ro.setTextColor(Color.BLACK); // set the color
			test_ro.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_ro); // add the column to the table row here

			final TextView test_wo = new TextView(this);
			test_wo.setId(21);// define id that must be unique
			test_wo.setText("3"); // set the text for the header
			test_wo.setTextColor(Color.BLACK); // set the color
			test_wo.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_wo); // add the column to the table row here

			final TextView test_lo = new TextView(this);
			test_lo.setId(21);// define id that must be unique
			test_lo.setText("85"); // set the text for the header
			test_lo.setTextColor(Color.BLACK); // set the color
			test_lo.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_lo); // add the column to the table row here

			final TextView test_max = new TextView(this);
			test_max.setId(21);// define id that must be unique
			test_max.setText("101"); // set the text for the header
			test_max.setTextColor(Color.BLACK); // set the color
			test_max.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_max); // add the column to the table row here

			final TextView test_tm = new TextView(this);
			test_tm.setId(21);// define id that must be unique
			test_tm.setText("-1.5"); // set the text for the header
			test_tm.setTextColor(Color.BLACK); // set the color
			test_tm.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_tm); // add the column to the table row here

			final TextView test_rank = new TextView(this);
			test_rank.setId(21);// define id that must be unique
			test_rank.setText("5810"); // set the text for the header
			test_rank.setTextColor(Color.BLACK); // set the color
			test_rank.setPadding(5, 5, 5, 5); // set the padding (if required)
			tr_data.addView(test_rank); // add the column to the table row here

			final TextView test_exam_set = new TextView(this);
			test_exam_set.setId(21);// define id that must be unique
			test_exam_set.setText("123456"); // set the text for the header
			test_exam_set.setTextSize((float) 0.01);
			test_exam_set.setTextColor(Color.TRANSPARENT); // set the color
			test_exam_set.setPadding(5, 5, 5, 5); // set the padding (if
													// required)
			tr_data.addView(test_exam_set); // add the column to the table row
											// here

			report_table.addView(tr_data, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			report_table1.addView(tr_data1, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			// ----------------------On click table
			// row---------------------------------------

			tr_data.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Toast.makeText(getApplicationContext(),
					// test_name.getText().toString(),
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),
					// test_date.getText().toString(),
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),
					// sr_no.getText().toString(), Toast.LENGTH_LONG).show();
					// Toast.makeText(getApplicationContext(),
					// test_exam_set.getText().toString(),
					// Toast.LENGTH_LONG).show();

					// Intent intent = new Intent(ReportListActivity.this,
					// ReportListDetailsActivity.class);
					// finish();
					// ReportListActivity.this.startActivity(intent);
				}
			});
			// ----------------------On click table
			// row---------------------------------------

		}

		// --------------------Table Body---------------------------

	}

	// onclick device back button
	@Override
	public void onBackPressed() {
		// do something on back.

		// Intent intent = new Intent(ReportListActivity.this,
		// TakeTestActivity.class);
		// finish();
		// ReportListActivity.this.startActivity(intent);
		return;
	}

	// method to show toast message
	public void makeAToast(String str) {
		// yet to implement
		Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}