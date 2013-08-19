package com.example.storage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Android Internal and External storage example - Store and Retrieve Data
 Android Internal Storage 
 Internal storage are private to your application and other applications cannot access them (nor can the user). 
 When the user uninstalls your application, these files are removed. 
 Android External Storage
 External storage such as SD card can also store application data.
 There's no security enforced upon files you save to the external storage. 
 All applications can read and write files placed on the external storage and the user can remove them. 
 You need Read and Write permission in External Storage

 In this example we are going to save data from an EditText to both Internal Storage and External Storage, and then try to get the data back from the respective storage places. 

 FileOutputStream :For save Data
 FileInputStream  :For Display data
 */

public class MainActivity extends Activity implements OnClickListener {

	private String filename = "StorageFile.txt";
	private String filepath = "FileStorage";
	File myInternalFile;
	File myExternalFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ContextWrapper contextWrapper = new ContextWrapper(
				getApplicationContext());
		File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
		myInternalFile = new File(directory, filename);

		Button saveToInternalStorage = (Button) findViewById(R.id.InternalStorageSave);
		saveToInternalStorage.setOnClickListener(this);

		Button readFromInternalStorage = (Button) findViewById(R.id.InternalStorageGet);
		readFromInternalStorage.setOnClickListener(this);

		Button saveToExternalStorage = (Button) findViewById(R.id.ExternalStorageSave);
		saveToExternalStorage.setOnClickListener(this);

		Button readFromExternalStorage = (Button) findViewById(R.id.ExternalStorageGet);
		readFromExternalStorage.setOnClickListener(this);

		// check if external storage is available and not read only
		if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
			saveToExternalStorage.setEnabled(false);
		} else {
			myExternalFile = new File(getExternalFilesDir(filepath), filename);
		}

	}

	public void onClick(View v) {

		EditText myInputText = (EditText) findViewById(R.id.InputText);
		TextView responseText = (TextView) findViewById(R.id.responseText);
		String myData = "";

		switch (v.getId()) {
		case R.id.InternalStorageSave:
			try {
				FileOutputStream fos = new FileOutputStream(myInternalFile); // save
				fos.write(myInputText.getText().toString().getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText("");
			responseText.setText("Saved to Internal Storage.(StorageFile.txt)");
			break;

		case R.id.InternalStorageGet:
			try {
				FileInputStream fis = new FileInputStream(myInternalFile); // display
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					myData = myData + strLine;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText(myData);
			responseText
					.setText("Data retrieved from Internal Storage.(StorageFile.txt)");

			break;

		case R.id.ExternalStorageSave:
			try {
				FileOutputStream fos = new FileOutputStream(myExternalFile);
				fos.write(myInputText.getText().toString().getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText("");
			responseText.setText("Saved to External Storage.(StorageFile.txt)");
			break;

		case R.id.ExternalStorageGet:
			try {
				FileInputStream fis = new FileInputStream(myExternalFile);
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					myData = myData + strLine;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText(myData);
			responseText
					.setText("Data retrieved from Internal Storage.(StorageFile.txt)");
			break;

		}
	}

	private static boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	private static boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			return true;
		}
		return false;
	}

}