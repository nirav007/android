package com.example.androidversion;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;



public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

	  TextView OSinfo = (TextView) findViewById(R.id.OSinfo);
	  OSinfo.setText(ReadOSinfo());

	}

	private String ReadOSinfo()
	{
	ProcessBuilder cmd;
	ProcessBuilder cmd1;
	try
	{
		cmd1 = new ProcessBuilder("mkdir aa");
		cmd1.start();
	}
	catch(Exception e)
	{
		
	}
	String result="";

	try{
	 String[] args = {"/system/bin/cat", "/proc/version"};
	 cmd = new ProcessBuilder(args);

	 Process process = cmd.start();
	 InputStream in = process.getInputStream();
	 byte[] re = new byte[1024];
	 while(in.read(re) != -1){
	  System.out.println(new String(re));
	  result = result + new String(re);
	 }
	 in.close();
	} catch(IOException ex){
	 ex.printStackTrace();
	}
	return result;
	}
	}