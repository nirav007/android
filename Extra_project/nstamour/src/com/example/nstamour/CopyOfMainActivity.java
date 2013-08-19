package com.example.nstamour;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfMainActivity extends Activity {

    TableLayout report_table;
    TableRow tr_data;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        report_table=(TableLayout) findViewById(R.id.report_table);


        //---------------Table Header-----------------------------------------------
        TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new LayoutParams(
        LayoutParams.FILL_PARENT,
        LayoutParams.WRAP_CONTENT));


        TextView label_sr_no = new TextView(this);
        label_sr_no.setId(20);
        label_sr_no.setText("S.No.");
        label_sr_no.setTextColor(Color.WHITE);
        label_sr_no.setPadding(5,5,5,5);
        tr_head.addView(label_sr_no);// add the column to the table row here

        TextView label_test_name = new TextView(this);
        label_test_name.setId(21);// define id that must be unique
        label_test_name.setText("Test Name"); // set the text for the header 
        label_test_name.setTextColor(Color.WHITE); // set the color
        label_test_name.setPadding(5,5,5,5); // set the padding (if required)
        tr_head.addView(label_test_name); // add the column to the table row here

        TextView label_test_date = new TextView(this);
        label_test_date.setId(21);// define id that must be unique
        label_test_date.setText("Date"); // set the text for the header 
        label_test_date.setTextColor(Color.WHITE); // set the color
        label_test_date.setPadding(5,5,5,5); // set the padding (if required)
        tr_head.addView(label_test_date); // add the column to the table row here



        report_table.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

      //---------------Table Header-----------------------------------------------



        //--------------------Table Body---------------------------
        for (int i=1; i<=10; i++)
        {
            tr_data = new TableRow(this);
            tr_data.setId(10);
            tr_data.setBackgroundColor(Color.TRANSPARENT);
            tr_data.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT));


            final TextView sr_no = new TextView(this);
            sr_no.setId(20);
            sr_no.setText(""+i);
            sr_no.setTextColor(Color.BLACK);
            sr_no.setPadding(5,5,5,5);
            tr_data.addView(sr_no);// add the column to the table row here

            final TextView test_name = new TextView(this);
            test_name.setId(21);// define id that must be unique
            test_name.setText("Speed Test 60(min) Demo-ST-01"); // set the text for the header 
            test_name.setTextColor(Color.BLACK); // set the color
            test_name.setPadding(5,5,5,5); // set the padding (if required)
            tr_data.addView(test_name); // add the column to the table row here

            final TextView test_date = new TextView(this);
            test_date.setId(21);// define id that must be unique
            test_date.setText("12 Mar 2013"); // set the text for the header 
            test_date.setTextColor(Color.BLACK); // set the color
            test_date.setPadding(5,5,5,5); // set the padding (if required)
            tr_data.addView(test_date); // add the column to the table row here



            report_table.addView(tr_data, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            
            tr_data.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					Toast.makeText(getApplicationContext(), test_name.getText().toString(), Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), test_date.getText().toString(), Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), sr_no.getText().toString(), Toast.LENGTH_LONG).show();
				}
			});
        }

      //--------------------Table Body---------------------------


    }


}