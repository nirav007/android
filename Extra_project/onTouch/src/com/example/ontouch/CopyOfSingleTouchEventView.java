package com.example.ontouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CopyOfSingleTouchEventView extends View {

	Paint paint = new Paint();
	Path path = new Path();
	
	public CopyOfSingleTouchEventView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		
		 //paint.setAntiAlias(true);
		  //  paint.setStrokeWidth(60f);
		  //  paint.setColor(Color.RED);
		  //  paint.setStyle(Paint.Style.STROKE);
		  //  paint.setStrokeJoin(Paint.Join.ROUND);
		
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(10f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawPath(path, paint);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		float eventX = event.getX();
		float eventY = event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			
				path.moveTo(eventX, eventY);
				return true;
			
			case MotionEvent.ACTION_MOVE:
			      path.lineTo(eventX, eventY);
			      break;
		}
		
		invalidate();
		return true;
	}
	
	 /*float eventX = event.getX();
	    float eventY = event.getY();

	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
	      path.moveTo(eventX, eventY);
	      return true;
	    case MotionEvent.ACTION_MOVE:
	      path.lineTo(eventX, eventY);
	      break;
	    case MotionEvent.ACTION_UP:
	      // nothing to do
	      break;
	    default:
	      return false;
	    }

	    // Schedules a repaint.
	    invalidate();
	    return true; */
 
} 