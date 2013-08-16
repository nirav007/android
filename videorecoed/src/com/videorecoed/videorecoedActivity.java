package com.videorecoed;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
/*
 * 
 * 800 480
 * 720 480
 * 640 480
 * 576 432
 * 480 320
 * 384 288
 * 352 288
 * 320 240
 * 240 160
 * 
 * */
public class videorecoedActivity extends Activity {
	private static final int OPTIONS_DIALOG = 0;
	private static final int DIALOG_ABOUT = 1;
	int chosenSize = 0;
	Size previewSize;
	String sessionNumber = String.format("%1$te%1$tm%1$tY%1$tH%1$tM", Calendar.getInstance());
	long counter = 0;
	SurfaceHolder mHolder;
	boolean recording = false;
	Camera cam;
	MediaRecorder recorder;
	SharedPreferences settings;
	int recordingmode;
	int piecesNumber = 10;
	int fps;
	Canvas mCanvas;
	Paint mPaint;
	Camera.Parameters parameters;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.mainmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch( item.getItemId()){
			case R.id.menuSettings:
				Intent question = new Intent(this, settings.class);
//				Camera.Size sizes = cam.getParameters().getPreviewSize();
//				Bundle extras = new Bundle();
//				question.putExtras(extras);
				startActivityForResult(question, OPTIONS_DIALOG);
				return true;
			case R.id.about:
				showDialog( DIALOG_ABOUT);
				return true;
		default:
				return super.onOptionsItemSelected(item);
		}
	}
	void loadSettings(){
		settings = getSharedPreferences("videoReg", MODE_WORLD_WRITEABLE);
		recordingmode = settings.getInt("chosenMode", R.id.timeLimited);
		chosenSize = settings.getInt("previewSize", 0);
/*		((RadioButton)findViewById(settings.getInt("chosenMode", R.id.radio1))).setChecked(true);
		((EditText)findViewById(R.id.timeLimitedParts)).setText( settings.getString("timeLimitedParts", "10"));
		((EditText)findViewById(R.id.timeLimitedMinutes)).setText( settings.getString("timeLimitedMinutes", "10"));
		((EditText)findViewById(R.id.sizeLimitedParts)).setText( settings.getString("sizeLimitedParts", "10"));
		((EditText)findViewById(R.id.sizeLimitedSize)).setText( settings.getString("sizeLimitedSize", "2000"));
		((Spinner)findViewById(R.id.outputFormatSpinner)).setSelection( settings.getInt("outputFormatSpinner", 0));
		((EditText)findViewById(R.id.fpsRate)).setText( settings.getString("fpsRate", "30"));
		((EditText)findViewById(R.id.width)).setText( settings.getString("width", "320"));
		((EditText)findViewById(R.id.height)).setText( settings.getString("height", "240"));/**/
	}
	void initWindow(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	void initApp(){
		((ListView)findViewById(R.id.recordsList)).setStackFromBottom(true);
		loadSettings();
		mHolder = ((SurfaceView) findViewById(R.id.surfaceView1)).getHolder();
		recorder = new MediaRecorder();	
		recorder.setOnInfoListener( infoListener);
		cam = Camera.open();
		fps = cam.getParameters().getSupportedPreviewFrameRates().get(settings.getInt("fpsRate", 0));
		previewSize = cam.getParameters().getSupportedPreviewSizes().get(chosenSize);
		cam.release();
		mHolder.setFixedSize( previewSize.width, previewSize.height);
		((TextView)findViewById(R.id.info)).setText(previewSize.width+"x"+previewSize.height+"\n"+
				fps+"fps");
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder arg0) {
//				cam.stopPreview();
//				cam.unlock();
//				cam.release();
			}

			public void surfaceCreated(SurfaceHolder arg0) {
				try {
					cam = Camera.open();
//					cam.reconnect();
					cam.setPreviewDisplay(mHolder);
				} catch (IOException e) {
					cam.release();
					e.printStackTrace();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int w,	int h) {
				parameters = cam.getParameters();
				parameters.setPreviewSize(w, h);
				//parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				cam.setParameters(parameters);
				if (getApplicationContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
					cam.setDisplayOrientation(90);
				} else {
					cam.setDisplayOrientation(0);
				}/**/
				cam.setPreviewCallbackWithBuffer( new PreviewCallback() {
					
					public void onPreviewFrame(byte[] data, Camera camera) {
						camera.addCallbackBuffer( data);						
					}
				});
				cam.startPreview();
				/*Camera.AutoFocusCallback afCallBack =new Camera.AutoFocusCallback() {
					
					public void onAutoFocus(boolean success, Camera camera) {
						Toast.makeText(getApplicationContext(), "focused", Toast.LENGTH_LONG).show();
					}
				};
				cam.autoFocus(afCallBack);/**/
			}
		});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initWindow();
		setContentView(R.layout.main);
		initApp();

		((ToggleButton) findViewById(R.id.recButton))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {

						if (recording) {
							stopRecording();
						} else {
							startRecording();
						}
					}
				});
		((Button)findViewById(R.id.zoomIn)).setOnClickListener( new OnClickListener() {
			
			public void onClick(View arg0) {
				Parameters param = cam.getParameters();
				if( param.getZoom() < param.getMaxZoom())
					param.setZoom( param.getZoom()+1);
				cam.setParameters(param);
			}
		});
		((Button)findViewById(R.id.zoomOut)).setOnClickListener( new OnClickListener() {
				
				public void onClick(View arg0) {
					Parameters param = cam.getParameters();
					if( param.getZoom() > 0)
						param.setZoom( param.getZoom()-1);
					cam.setParameters(param);
				}
			});
		if( !refreshVideosList()){
			Toast.makeText(getApplicationContext(), getText(R.string.errorFileSystemAccess), Toast.LENGTH_LONG).show();
			((ToggleButton) findViewById(R.id.recButton)).setEnabled(false);
		}
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode){
    		case OPTIONS_DIALOG:
    			previewSize = parameters.getSupportedPreviewSizes().get(data.getExtras().getInt("previewSize"));
    			fps = parameters.getSupportedPreviewFrameRates().get(data.getExtras().getInt("fpsRate"));
    			((TextView)findViewById(R.id.info)).setText(previewSize.width+"x"+previewSize.height+"\n"+
    					fps+"fps");
    			break;
    	}
    	//super.onActivityResult(requestCode, resultCode, data);
    }
	@Override
	protected void onPause() {
		cam.release();
		super.onPause();
	}
	private OnInfoListener infoListener = new OnInfoListener() {
		public void onInfo(MediaRecorder mr, int what, int extra) {
			switch( what){
				case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
				case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
					//Toast.makeText(getApplicationContext(), getText(R.string.maxDurationReached), Toast.LENGTH_LONG).show();
					stopRecording();
					//currentPart++;
					File file = new File(getExternalFilesDir(null).getAbsolutePath());
					long lastModified=Long.MAX_VALUE;
					int oldestItem = piecesNumber;
					FilenameFilter filenamefilter = new FilenameFilter() {
						
						public boolean accept(File dir, String filename) {
							if( filename.startsWith("video" +sessionNumber + "_"))
								return true;
							else
								return false;
						}
					};
					File[] filesList = file.listFiles( filenamefilter);
					if( filesList != null){
						if( filesList.length >= piecesNumber){
							for( int a=0; a<filesList.length; a++)
								if( filesList[a].lastModified() < lastModified){
									lastModified = filesList[a].lastModified();
									oldestItem = a;
								}
							try{
								filesList[oldestItem].delete();
							}catch( Exception e){
								Toast.makeText( getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
							}
						}
					}
					//currentPart=0;
					startRecording();
				default:
					break;
			}
		}
	};
	private void startRecording(){
		((ToggleButton) findViewById(R.id.recButton)).setEnabled(false);
		cam.unlock();
		recorder.setCamera(cam);

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setMaxDuration( Integer.parseInt( settings.getString("timeLimitedMinutes", "10"))*60*1000);

		recorder.setVideoSize( previewSize.width, previewSize.height);
		recorder.setVideoFrameRate(fps);
		recorder.setAudioChannels(1);
		
		recorder.setPreviewDisplay(mHolder.getSurface());

		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);

		recorder.setOutputFile(getVideoFileName());
		try {
			recorder.prepare();
			recorder.start();
			recording = true;
//			((ToggleButton) findViewById(R.id.recButton))
//					.setText(R.string.recording);
			((ToggleButton) findViewById(R.id.recButton)).setEnabled(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					e.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		}
	}
	private void stopRecording(){
		((ToggleButton) findViewById(R.id.recButton)).setEnabled(false);
		recorder.stop();
		recorder.reset();
		try {
			cam.reconnect();
			cam.unlock();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recording = false;
		refreshVideosList();
//		((ToggleButton) findViewById(R.id.recButton))
//				.setText(R.string.viewing);
		((ToggleButton) findViewById(R.id.recButton)).setEnabled(true);
	}
	boolean refreshVideosList(){
		try{
			File file = new File(getExternalFilesDir(null).getAbsolutePath());
			final String[] filesList = file.list();
			Arrays.sort( filesList);
			((ListView)findViewById(R.id.recordsList)).setAdapter( new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1,
					filesList));
			((ListView)findViewById(R.id.recordsList)).setOnItemClickListener( new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if( recording)
						return;
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					File file = new File(getExternalFilesDir(null).getAbsolutePath()+"/"+filesList[position]);
					intent.setDataAndType(Uri.fromFile(file), "video/*");
					startActivity(intent); /**/

				}
			});
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	String getVideoFileName() {
		File file = new File(getExternalFilesDir(null), "video"
				+sessionNumber + "_"+counter+++".mp4");
		try {
			boolean created = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					getText(R.string.fileCreateExternalFailed),
					Toast.LENGTH_LONG).show();
		}
		return file.getAbsolutePath();
	}
}
