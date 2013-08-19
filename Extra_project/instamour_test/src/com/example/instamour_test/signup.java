package com.example.instamour_test;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class signup extends Activity {

	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath = "";

	private ImageView img_signupgo;
	private EditText edt_signupusername;
	private EditText edt_signuppassword;
	private EditText edt_signupemail;
	private ImageView img_signupimgselect;
	private ImageView img_signupimgdisplay;

	private ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signup);

		img_signupgo = (ImageView) findViewById(R.id.img_signupgo);
		edt_signupusername = (EditText) findViewById(R.id.edt_signupusername);
		edt_signuppassword = (EditText) findViewById(R.id.edt_signuppassword);
		edt_signupemail = (EditText) findViewById(R.id.edt_signupemail);
		
		img_back = (ImageView) findViewById(R.id.imgback);
		img_back.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intent = new Intent(signup.this,login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		img_signupimgselect = (ImageView) findViewById(R.id.img_signupimgselect);
		img_signupimgdisplay = (ImageView) findViewById(R.id.img_signupimgdisplay);

		//edt_signupusername.setText("nirav");
		//edt_signuppassword.setText("password");
		//edt_signupemail.setText("nirav.r@gmail.com");
		img_signupgo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ((edt_signupemail.getText().length() > 0)
						&& (edt_signuppassword.getText().length() > 0)
						&& (edt_signupusername.getText().length() > 0)
						&& (selectedImagePath.length() > 0)) {
					Intent intent = new Intent(signup.this, signupnext.class);
					intent.putExtra("signupusername", edt_signupusername
							.getText().toString());
					intent.putExtra("signuppassword", edt_signuppassword
							.getText().toString());
					intent.putExtra("signupemail", edt_signupemail.getText()
							.toString());
					intent.putExtra("signupimgpath", selectedImagePath);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Fill complete detail", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		img_signupimgselect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);

			}
		});
	}
	
	@Override
	public void onBackPressed() {
	   
	   Intent setIntent = new Intent(signup.this,login.class);
	   //setIntent.addCategory(Intent.CATEGORY_HOME);
	   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(setIntent);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				/*Toast.makeText(getApplicationContext(), selectedImagePath,
						Toast.LENGTH_LONG).show(); */
				// img.setVisibility(0);
				img_signupimgdisplay.setImageURI(selectedImageUri);
				// ss = selectedImagePath.replace("/mnt", "");

			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
