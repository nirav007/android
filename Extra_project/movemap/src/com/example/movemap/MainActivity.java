package com.example.movemap;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MainActivity extends MapActivity {

	private TextView myLongitude, myLatitude;
	private CheckBox mySatellite;

	private MapView myMapView;
	private MapController myMapController;

	private void SetSatellite() {
		myMapView.setSatellite(mySatellite.isChecked());
	};

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);

		//Bundle bundle = this.getIntent().getExtras();
		//int Mode = bundle.getInt("Mode");

		myMapView = (MapView) findViewById(R.id.mapview);
		myMapController = myMapView.getController();
		myMapView.setBuiltInZoomControls(true);

		myLongitude = (TextView) findViewById(R.id.longitude);
		myLatitude = (TextView) findViewById(R.id.latitude);
		mySatellite = (CheckBox) findViewById(R.id.satellite);
		mySatellite.setOnClickListener(mySatelliteOnClickListener);

		SetSatellite();

		/*if (Mode == 0) {
			GeoPoint initGeoPoint = myMapView.getMapCenter();
			CenterLocation(initGeoPoint);
		} else if (Mode == 1) {*/
			int intLatitude = (int) 37.422006;
			int intLongitude = (int) -122.084095;
			GeoPoint initGeoPoint = new GeoPoint(intLatitude, intLongitude);
			CenterLocation(initGeoPoint);
		//}
	}

	private void placeMarker(int markerLatitude, int markerLongitude) {
		Drawable marker = getResources().getDrawable(
				android.R.drawable.ic_menu_myplaces);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		myMapView.getOverlays().add(
				new InterestingLocations(marker, markerLatitude,
						markerLongitude));
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void CenterLocation(GeoPoint centerGeoPoint) {
		myMapController.animateTo(centerGeoPoint);

		myLongitude
				.setText("Longitude: "
						+ String.valueOf((float) centerGeoPoint
								.getLongitudeE6() / 1000000));
		myLatitude
				.setText("Latitude: "
						+ String.valueOf((float) centerGeoPoint.getLatitudeE6() / 1000000));
		placeMarker(centerGeoPoint.getLatitudeE6(),
				centerGeoPoint.getLongitudeE6());
	};

	private CheckBox.OnClickListener mySatelliteOnClickListener = new CheckBox.OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			SetSatellite();
		}
	};

	class InterestingLocations extends ItemizedOverlay<OverlayItem> {

		private List<OverlayItem> locations = new ArrayList<OverlayItem>();
		private Drawable marker;
		private OverlayItem myOverlayItem;

		boolean MoveMap;

		public InterestingLocations(Drawable defaultMarker, int LatitudeE6,
				int LongitudeE6) {
			super(defaultMarker);
			// TODO Auto-generated constructor stub
			this.marker = defaultMarker;
			// create locations of interest
			GeoPoint myPlace = new GeoPoint(LatitudeE6, LongitudeE6);
			myOverlayItem = new OverlayItem(myPlace, "My Place", "My Place");
			locations.add(myOverlayItem);

			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return locations.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return locations.size();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);

			boundCenterBottom(marker);
		}

		@Override
		public boolean onTouchEvent(MotionEvent arg0, MapView arg1) {
			// TODO Auto-generated method stub
			// super.onTouchEvent(arg0, arg1);

			int Action = arg0.getAction();
			if (Action == MotionEvent.ACTION_UP) {

				if (!MoveMap) {
					Projection proj = myMapView.getProjection();
					GeoPoint loc = proj.fromPixels((int) arg0.getX(),
							(int) arg0.getY());

					// remove the last marker
					myMapView.getOverlays().remove(0);

					CenterLocation(loc);
				}

			} else if (Action == MotionEvent.ACTION_DOWN) {

				MoveMap = false;

			} else if (Action == MotionEvent.ACTION_MOVE) {
				MoveMap = true;
			}

			return super.onTouchEvent(arg0, arg1);
			// return false;
		}
	}
}