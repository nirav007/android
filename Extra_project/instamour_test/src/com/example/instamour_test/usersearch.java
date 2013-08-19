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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

//Age/ Height/ Identity/ Body type/ Looking for/ Location

public class usersearch extends Activity {
	static ListView listView;
	private EditText edtGender;
	private EditText edtSmoker;
	private EditText edtVideo;
	private EditText edtRelation;
	private EditText edtLocation;
	private EditText edtAge;
	
	String  user_gender,user_smoker,user_video,user_relation,user_location,user_age1,user_age2,user_ethnicity;
	private EditText edtEthnicity;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usersearch);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().hide();
		}

		TextView txtheadtext = (TextView) findViewById(R.id.txtheadtext);
		txtheadtext.setText("Search");

		findViewById(R.id.sample_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int width = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
										.getDisplayMetrics());
						SlideoutActivity.prepare(usersearch.this,
								R.id.inner_content, width);
						startActivity(new Intent(usersearch.this,
								MenuActivity.class));
						overridePendingTransition(0, 0);
					}
				});

		Button btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(usersearch.this,
						usersearchresult.class);
				
				intent.putExtra("user_gender", user_gender);
				intent.putExtra("user_smoker", user_smoker);
				intent.putExtra("user_video", user_video);
				intent.putExtra("user_relation", user_relation);
				intent.putExtra("user_ethnicity", user_ethnicity);
				intent.putExtra("user_age1", user_age1);
				intent.putExtra("user_age2", user_age2);
				
				
				startActivity(intent);
			}
		});
		// MyArrayAdapter adapter = new MyArrayAdapter(usersearch.this,
		// listArr);
		// listView.setAdapter(adapter);

		edtGender = (EditText) findViewById(R.id.edtGender);

		edtGender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Male");
				list.add("Female");
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								
								edtGender
										.setText(list.get(position).toString());
								
								user_gender=list.get(position).toString();

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		edtSmoker = (EditText) findViewById(R.id.edtSmoker);

		edtSmoker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Yes");
				list.add("No");
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								

								edtSmoker
										.setText(list.get(position).toString());
								
								user_smoker = list.get(position).toString();

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		edtVideo = (EditText) findViewById(R.id.edtVideo);

		edtVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("At least 1");
				list.add("At least 2");
				list.add("At least 3");
				list.add("At least 4");
				
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();

								edtVideo
		
								.setText(list.get(position).toString());
								
								if(position==0)
								{
									user_video="1";
								}
								else if(position==1)
								{
									user_video="2";
								}
								else if(position==2)
								{
									user_video="3";
								}
								else if(position==3)
								{
									user_video="4";
								}

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		edtRelation = (EditText) findViewById(R.id.edtRelation);

		edtRelation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("friends first");
				list.add("love");
				list.add("marriage");
				list.add("a partner in crime");
				list.add("a relationship");
				list.add("some fun");
				list.add("a soulmate");
				
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();

								edtRelation
										.setText(list.get(position).toString());
								
								user_relation=list.get(position).toString();

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		edtEthnicity = (EditText) findViewById(R.id.edtEthnicity);

		edtEthnicity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("Caucasian");
				list.add("Indian");
				list.add("Hispanic");
				list.add("Middle Eastern");
				list.add("Native American");
				
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
						

								edtEthnicity
										.setText(list.get(position).toString());
								
								user_ethnicity=list.get(position).toString();

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});
		
		edtAge = (EditText) findViewById(R.id.edtAge);

		edtAge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

				final List<String> list = new ArrayList<String>();
				list.add("16-20");
				list.add("21-25");
				list.add("26-30");
				list.add("above");
				
				
				
				

				ArrayAdapter<String> adp1 = new ArrayAdapter<String>(
						usersearch.this, android.R.layout.simple_list_item_1,
						list);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mySpinner.setAdapter(adp1);
				mySpinner.performClick();

				mySpinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),
										list.get(position), Toast.LENGTH_SHORT)
										.show();

								edtAge
										.setText(list.get(position).toString());
								
								if(position==0)
								{
									user_age1="16";
									user_age2="20";
									
								}
								else if(position==1)
								{
									user_age1="21";
									user_age2="25";
								}
								else if(position==2)
								{
									user_age1="26";
									user_age2="30";
								}
								else if(position==3)
								{
									user_age1="31";
									user_age2="100";
								}

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

			}
		});

	}

}
