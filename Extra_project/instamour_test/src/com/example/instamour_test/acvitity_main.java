package com.example.instamour_test;

import android.app.Activity;
import android.os.Bundle;

public class acvitity_main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		/*ffmpeg –i video1.avi –sameq video1.mpg
		ffmpeg –i video2.avi –sameq video2.mpg
		ffmpeg –i video3.avi –sameq video3.mpg
		# so on for every file to be merged
		cat video1.mpg video2.mpg video3.mpg | ffmpeg –f mpeg –i - -sameq video.avi*/
		
	}

}
