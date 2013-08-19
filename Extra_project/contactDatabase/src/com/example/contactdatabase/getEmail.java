package com.example.contactdatabase;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class getEmail extends Activity {
	 
	 ListView listContacts;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  
	  listContacts = (ListView)findViewById(R.id.conactlist);
	  
	  Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

	  //Get All contacts
	  CursorLoader cursorLoader = new CursorLoader(
	            this, 
	            queryUri, 
	            null, 
	            null, 
	            null, 
	            null);
	  
	  Cursor cursor = cursorLoader.loadInBackground();
	  
	  String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
	        int[] to = {android.R.id.text1};
	        
	        ListAdapter adapter = new SimpleCursorAdapter(
	                this, 
	                android.R.layout.simple_list_item_1, 
	                cursor, 
	                from, 
	                to, 
	                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	        listContacts.setAdapter(adapter);
	        
	        listContacts.setOnItemClickListener(myOnItemClickListener);
	 }
	 
	 OnItemClickListener myOnItemClickListener
	 = new OnItemClickListener(){

	  @Override
	  public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	   Cursor cursor = (Cursor)parent.getItemAtPosition(position);
	   String item_LookUp = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
	   
	   Uri lookUpUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
	   
	   String[] projection = new String[] {
	     ContactsContract.CommonDataKinds.Email.DATA};
	   
	   String selection = ContactsContract.CommonDataKinds.Email.LOOKUP_KEY + "=?";
	   String[] selectionArgs = new String[]{item_LookUp};
	   
	   CursorLoader cursorLoader_LookUp = new CursorLoader(
	     getEmail.this, 
	     lookUpUri, 
	     projection, 
	     selection, 
	     selectionArgs, 
	     null);
	   Cursor cursor_LookUp = cursorLoader_LookUp.loadInBackground();

	   int lookUpCol_Data = cursor_LookUp.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

	   String stringEmail = item_LookUp + "\n";
	   while(cursor_LookUp.moveToNext()){

	    String stringData = cursor_LookUp.getString(lookUpCol_Data);
	    stringEmail += stringData + "\n";
	   }
	   
	   Toast.makeText(getApplicationContext(), 
	     stringEmail, 
	     Toast.LENGTH_LONG).show();

	  }
	  
	 };

	}