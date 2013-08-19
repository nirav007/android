package com.example.getcolorfromimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TouchView extends ImageView {

	Bitmap bitmap;
	double bmWidth, bmHeight;

	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public TouchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {

		bitmap = ((BitmapDrawable) getBackground()).getBitmap();
		bmWidth = (double) bitmap.getWidth();
		bmHeight = (double) bitmap.getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY();

			int color = getColor(x, y);
			((MainActivity) getContext()).updateMsg("Touched@" + x + " : " + y,
					color);

			break;
		case MotionEvent.ACTION_UP:
			((MainActivity) getContext()).updateMsg("", 0);
			break;
		}

		return true;
	}

	private int getColor(float x, float y) {

		if (x < 0 || y < 0 || x > (float) getWidth() || y > (float) getHeight()) {
			return 0; // Invalid, return 0
		} else {
			// Convert touched x, y on View to on Bitmap
			int xBm = (int) (x * (bmWidth / (double) getWidth()));
			int yBm = (int) (y * (bmHeight / (double) getHeight()));

			return bitmap.getPixel(xBm, yBm);
		}

	}

}