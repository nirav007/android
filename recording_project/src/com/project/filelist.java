package com.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class filelist extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root=Environment.getExternalStorageDirectory() + "/VideoRecord/";
	private TextView myPath;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
       
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
    	
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	
    	if(!dirPath.equals(root))
    	{

    		item.add(root);
    		path.add(root);
    		
    		item.add("../");
    		path.add(f.getParent());
            
    	}
    	
    	for(int i=0; i < files.length; i++)
    	{
    			File file = files[i];
    			path.add(file.getPath());
    			if(file.isDirectory())
    				item.add(file.getName() + "/");
    			else
    				item.add(file.getName());
    	}

    	ArrayAdapter<String> fileList =
    		new ArrayAdapter<String>(this, R.layout.row, item);
    	setListAdapter(fileList);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		final File file = new File(path.get(position));
		//Toast.makeText(getApplicationContext(), file.getName().toString(), Toast.LENGTH_LONG).show();
		Intent intent = new Intent(filelist.this,video.class);
		intent.putExtra("fname", file.getName().toString());
		startActivity(intent);
		
	}
}
