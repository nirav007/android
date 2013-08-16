package com.example.cameraupload1;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private Uri selectedImageUri;
	private String selectedImagePath;
	private ImageView imageView;
	private String imagePath;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button photoButton = (Button) this.findViewById(R.id.button1);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        
        photoButton.setOnClickListener(new OnClickListener() {
			
			
			private Uri outputFileUri;

			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				  File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
				  outputFileUri = Uri.fromFile(file);
				  intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				  startActivityForResult(intent, 0);
				
			}

			
		});
        
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("Test", "IGA");
        if (resultCode == RESULT_OK) {
            {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imagePath=selectedImagePath;

                Log.v("Test","IGA1 "+imagePath);
               imageView.setImageBitmap(thumbnail);
            }
        }
        if (resultCode == RESULT_CANCELED) {

            return;
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
    

    protected void onPhotoTaken()
    {
      //  doFileUpload(MediaStore.EXTRA_OUTPUT);
    }

   /* private void doFileUpload(String extraOutput)
    {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inStream = null;        
        String urlServer = "http:XXXXXXXXXXXXXXXXXXXXXX/upload.php";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File(extraOutput) );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        URL url;
		try {
			url = new URL(urlServer);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
        try {
			outputStream = new
			DataOutputStream( connection.getOutputStream() );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			outputStream.writeBytes("Content-Disposition: form-data; name=\"userfile\";filename=\"" + extraOutput +"\"" + lineEnd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        outputStream.writeBytes(lineEnd);
        try {
			bytesAvailable = fileInputStream.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while (bytesRead > 0)
        {
            outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens +lineEnd);
        int serverResponseCode = connection.getResponseCode();
        String serverResponseMessage = connection.getResponseMessage();       
        fileInputStream.close();
        outputStream.flush();
        outputStream.close();
     }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
