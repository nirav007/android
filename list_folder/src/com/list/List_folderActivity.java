package com.list;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_folderActivity extends Activity {
    private ListView list;
    String a [] ;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView) findViewById(R.id.listView1);
        File extStore = Environment.getExternalStorageDirectory();
        a  =  extStore.list();
        
        list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,a));
        
        for(int i=0;i<a.length;i++)
        {
        	
        	File f = new File(a[i]);
        	//Toast.makeText(getApplicationContext(), f.getPath(), Toast.LENGTH_SHORT).show();
        	f.renameTo(new File(a[i]+"a" ));
        }
        
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int i = arg2;
				File f = new File(Environment.getExternalStorageDirectory() + "// " + 	a[i]);
				
				if(f.isFile()==true)
				{
					Toast.makeText(List_folderActivity.this, "File", Toast.LENGTH_SHORT).show();
				}
				if(f.isDirectory()==true)
				{
					Toast.makeText(List_folderActivity.this, "Dir", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(getApplicationContext(), f.getPath(), Toast.LENGTH_SHORT).show();
			}
		});
    }
}