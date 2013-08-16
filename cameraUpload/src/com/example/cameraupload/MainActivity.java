package com.example.cameraupload;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity  extends Activity {
    private static final int CAMERA_REQUEST = 1888; 
    private ImageView imageView;
    private Uri mCapturedImageURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

          

			@Override
            public void onClick(View v) {
             //   Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
             //   startActivityForResult(cameraIntent, CAMERA_REQUEST); 
            	
				String fileName = "temp.jpg";  
				ContentValues values = new ContentValues();  
				values.put(MediaStore.Images.Media.TITLE, fileName);  
				mCapturedImageURI=getContentResolver().insert(MediaStore.Images.Media.
				                                              EXTERNAL_CONTENT_URI, values);  

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
				intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);  
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // add EXTRA_VIDEO_QUALITY
				startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == 0 && resultCode == RESULT_OK) {  
            //Bitmap photo = (Bitmap) data.getExtras().get("data"); 
           // imageView.setImageBitmap(photo);
            
           // Uri u = data.getData().gete;
           // String s = u.getPath();
        	
        	Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
           Uri selectedImageUri = data.getData();
            String selectedImagePath = getPath(selectedImageUri);
           String imagePath=selectedImagePath;
           Toast.makeText(getApplicationContext(), imagePath.toString(), Toast.LENGTH_LONG).show();
            Log.e("Test","IGA1 "+imagePath);
            
        	// String[] projection = { MediaStore.Images.Media.DATA}; 
           //  Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null); 
            // int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
            // cursor.moveToFirst(); 
           //  String capturedImageFilePath = cursor.getString(column_index_data);
             
           //  Toast.makeText(getApplicationContext(), capturedImageFilePath.toString(), Toast.LENGTH_LONG).show();
            
            //String selectedImageUri = data.getData().getPath();
            
            //selectedImagePath = getPath(selectedImageUri);
            //System.out.println("Image Path : " + selectedImagePath);
           // img.setVisibility(0);
            //img.setImageURI(selectedImageUri);
           // ss = selectedImagePath.replace("/mnt", "");
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
    public String getPath1(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow("userImage"/*MediaStore.Images.Media.DATA*/);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}