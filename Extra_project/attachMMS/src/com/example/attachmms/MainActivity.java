package com.example.attachmms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	ByteArrayBuffer baf = new ByteArrayBuffer(1024);
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        		Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("audio/mp3");
                sendIntent.putExtra("sms_body",
                        getResources().getText(R.string.Message));
                sendIntent.setClassName("com.android.mms",
                        "com.android.mms.ui.ComposeMessageActivity");
                AssetManager mngr = getAssets();
                InputStream path = null;
                try {
                    path = mngr.open("song.mp3");
                } 
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                BufferedInputStream bis = new BufferedInputStream(path, 1024);

                // get the bytes one by one
                int current = 0;
                //
                
                
				try {
                    while ((current = bis.read()) != -1) {

                        baf.append((byte) current);
                    }
                } 
                catch (IOException e) {
                    // /TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                byte[] bitmapdata = baf.toByteArray();

                File file = new File(Environment.getExternalStorageDirectory(),
                        "song.mp3");

                

                FileOutputStream fos;

                try {
                    fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    // handle exception
                } catch (IOException e) {
                    // handle exception
                }

                final File file1 = new File(Environment
                        .getExternalStorageDirectory().getAbsolutePath(),
                        "song.mp3");
                Uri uri = Uri.fromFile(file1);
                Log.e("Path", "" + uri);

                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

                // sendIntent.putExtra(Intent.EXTRA_STREAM, mms_uri.toString());

                startActivity(Intent.createChooser(sendIntent, ""));

            } 
    
    


}
