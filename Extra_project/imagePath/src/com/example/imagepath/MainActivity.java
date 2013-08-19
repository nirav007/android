package com.example.imagepath;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.app.ListActivity;
import android.database.Cursor;

public class MainActivity  extends ListActivity {
	 
	 //define source of MediaStore.Images.Media, internal or external storage
	 Uri sourceUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	 //Uri sourceUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

	 SimpleCursorAdapter mySimpleCursorAdapter;
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.activity_main);
	        
	        String[] from = {MediaStore.MediaColumns.TITLE};
	        int[] to = {android.R.id.text1};

	        CursorLoader cursorLoader = new CursorLoader(
	          this, 
	          sourceUri, 
	          null, 
	          null, 
	          null, 
	          MediaStore.Audio.Media.TITLE);
	        
	        Cursor cursor = cursorLoader.loadInBackground();
	        
	        mySimpleCursorAdapter = new SimpleCursorAdapter(
	          this, 
	          android.R.layout.simple_list_item_1, 
	          cursor, 
	          from, 
	          to, 
	          CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	        
	        setListAdapter(mySimpleCursorAdapter);
	        
	        getListView().setOnItemClickListener(myOnItemClickListener);
	    }
	    
	    OnItemClickListener myOnItemClickListener
	    = new OnItemClickListener(){

	  @Override
	  public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	   Cursor cursor = mySimpleCursorAdapter.getCursor();
	   cursor.moveToPosition(position);
	   String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
	   Uri selUri = Uri.withAppendedPath(sourceUri, _id);
	   
	   Toast.makeText(getApplicationContext(), 
	     selUri.getPath(), 
	     Toast.LENGTH_LONG).show();
	   
	  }};

	}
