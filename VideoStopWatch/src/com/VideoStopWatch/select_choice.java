package com.VideoStopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class select_choice extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root=Environment.getExternalStorageDirectory() + "/VideoRecord/";
	private TextView myPath;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_choice);
       
        item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	item.add("Record Video");
    	item.add("Play Video");
    	item.add("Upload Video");
    	
    	
    	ArrayAdapter<String> choiceList =
    		new ArrayAdapter<String>(this, R.layout.select_row, item);
    	setListAdapter(choiceList);
    	
    	
    	
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
		
		if(position==0)
		{
			Intent intent = new Intent(select_choice.this,select.class);
			startActivity(intent);
		}
		else if(position==1)
		{
			Intent intent = new Intent(select_choice.this,filelist.class);
			startActivity(intent);
		}
		else if(position==2)
		{
			
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.youtube.com/my_videos_upload"));
			startActivity(intent);
		}
		
	}
    
}