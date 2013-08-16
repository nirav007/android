package com.sqllite;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SqlliteActivity extends Activity {

	private ArrayList<String> results = new ArrayList<String>();
	SQLiteDatabase db;
	DBHelper data ;
	private SQLiteDatabase newDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       data = new DBHelper(SqlliteActivity.this);
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
		newDB = dbHelper.getWritableDatabase();
		
		
       /* deleteDatabase("cashbookdata.db");
        db = openOrCreateDatabase(
            "cashbookdata.db"
            , SQLiteDatabase.CREATE_IF_NECESSARY
            , null
            );
        
        

        final String Create_CashBook =
            "CREATE TABLE CashData ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "Description TEXT," 
            + "Amount REAL,"
            + "Trans INTEGER," 
            + "EntryDate TEXT);";

        db.execSQL(Create_CashBook);  
        final String Insert_Data="INSERT INTO CashData VALUES(2,'Electricity',500,1,'04/06/2011')";
        db.execSQL(Insert_Data);
        */
        Button btn = (Button) findViewById(R.id.insert);
        
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			//String 	Insert_Data="INSERT INTO CashData VALUES('Electricity',500,1,'04/06/2011')";
		     //   db.execSQL(Insert_Data);
				
				EditText desc = (EditText) findViewById(R.id.editText1);
				String edit1 = desc.getText().toString();
				
				EditText amt = (EditText) findViewById(R.id.editText2);
				String edit2 = amt.getText().toString();
				
				EditText date = (EditText) findViewById(R.id.editText3);
				String edit3 = date.getText().toString();
				
				//ContentValues values = new ContentValues();
				//values.put("Description",edit1);
				//values.put("Amount",edit2);
				//values.put("Trans",1);
				//values.put("EntryDate",edit3);
				
				//db.insertOrThrow("CashData", null,values);*/
				data.insdata(edit1, edit2, edit3, 20);
				}
			
		});
        
        Button show = (Button) findViewById(R.id.show);
        
        show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				openAndQueryDatabase();
				
				ListView showdata = (ListView) findViewById(R.id.listshow);
				
				showdata.setAdapter(new ArrayAdapter<String>
				(getApplicationContext(), android.R.layout.simple_list_item_1,results));
			}
		});
       
        
    }
    private void openAndQueryDatabase() {
		try {
			DBHelper dbHelper = new DBHelper(this.getApplicationContext());
			newDB = dbHelper.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT FirstName, Age FROM " +
	    			"Resource" +
	    			" where Age > 10 LIMIT 4", null);

	    	if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String firstName = c.getString(c.getColumnIndex("FirstName"));
	    				int age = c.getInt(c.getColumnIndex("Age"));
	    				results.add("Name: " + firstName + ",Age: " + age);
	    			}while (c.moveToNext());
	    		} 
	    	}			
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	if (newDB != null) 
        		newDB.execSQL("DELETE FROM " + "Resource" );
        		newDB.close();
        }
 

}
}