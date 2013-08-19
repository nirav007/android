package com.example.instamour_test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends ListFragment {

	private ListView listView;

	private String[] listArr = { "User name", "Pending Amours", "Your Amours",
			"Browse Profiles", "Instant chat", "Messages", "Create Videos",
			"Custom Search", "Purchase Gifts", "Account Setting", "Privacy",
			"Help & Support", "Logout" };
	private int[] img = { R.drawable.pendingamours_selected,
			R.drawable.pendingamours_selector, R.drawable.youramours_selector,
			R.drawable.browseprofile_selector, R.drawable.chat_selector,
			R.drawable.messages_selector, R.drawable.createvideo_selector,
			R.drawable.customsearch_selector, R.drawable.purchasegift_selector,
			R.drawable.accountsettings_selector, R.drawable.privacy_selector,
			R.drawable.helpsupport_selector, R.drawable.logout_selector };

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new MyArrayAdapter(getActivity(), listArr));

		getListView().setBackgroundColor(
				getResources().getColor(R.color.backcolornonselect));
		getListView().setSelector(
				getResources().getDrawable(R.drawable.list_item_selector));

		// getListView().setCacheColorHint(Color.BLUE);
		// getListView().requestFocus(0);
		getListView().setCacheColorHint(0);
		// getListView().setPadding(0, 0, 0, 0);
		// MyArrayAdapter adapter = new MyArrayAdapter(MenuFragment.this,
		// listArr);
		// listView.setAdapter(adapter);

		// getListView().get
		// getListView().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.usercolor));

		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		if (position == 0) {
			Intent intent = new Intent(getActivity(), userprofileview_.class);
			startActivity(intent);
		}
		if (position == 1) {
			Intent intent = new Intent(getActivity(), userpendingamours.class);
			startActivity(intent);
		}
		if (position == 2) {
			Intent intent = new Intent(getActivity(), useryouramours.class);
			startActivity(intent);
		}
		if (position == 3) {
			Intent intent = new Intent(getActivity(), editprofile.class);
			startActivity(intent);

		}
		if (position == 4) {

		}
		if (position == 5) {
			Intent intent = new Intent(getActivity(), msglist.class);
			startActivity(intent);
		}

		if (position == 6) {
			Intent intent = new Intent(getActivity(), editvideo.class);
			startActivity(intent);
		}
		if (position == 7) {

			Intent intent = new Intent(getActivity(), usersearch.class);
			startActivity(intent);

		}

		if (position == 8) {
			Intent intent = new Intent(getActivity(), usernotification.class);
			startActivity(intent);
		}

		if (position == 9) {
			Intent intent = new Intent(getActivity(), useraccountsetting.class);
			startActivity(intent);

		}

		if (position == 10) {
			Intent intent = new Intent(getActivity(), privacy.class);
			startActivity(intent);
		}
		if (position == 11) {
			Intent intent = new Intent(getActivity(), helpsupport.class);
			startActivity(intent);
		}
		if (position == 12) {

			new LoginTask(getActivity()).execute();
			//Intent intent = new Intent(getActivity(), signin.class);
			//startActivity(intent);
		}
		// v.setBackgroundColor(Color.CYAN);

	}

	public class LoginTask extends AsyncTask<Void, Void, Boolean> {
		private Activity activity;
		private ProgressDialog pd;

		public LoginTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(activity, "Sign out",
					"Please wait while we are signing out.");
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			pd.dismiss();
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Toast.makeText(activity, Boolean.toString(result),
					Toast.LENGTH_LONG).show();
			 activity.startActivity(new Intent(activity, signin.class));
		}
	}

	public class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;

		private TextView btnchkout;

		// private final integer[] image;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.ticket_detail, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		private int selectedItem;

		public void setSelectedItem(int position) {
			selectedItem = position;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.ticket_detail, null, true);

			TextView textView = (TextView) view.findViewById(R.id.txt);
			ImageView imgicon = (ImageView) view.findViewById(R.id.imgicon);

			if (position==0) {
				//MyApp appState = ((MyApp) getActivity().getApplicationContext());
				//String uname = appState.getState();
				

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity()
								.getApplicationContext());

				String photo = preferences.getString("photo", "");
				String uname = preferences.getString("uname", "");

				textView.setText(uname);
				ImageLoader imageLoader;
				imageLoader = new ImageLoader(getActivity()
						.getApplicationContext());
				imageLoader.DisplayImage(
						"http://www.instamour.com/APP_files/User_files/"
								+ photo, imgicon);
			} else {
				textView.setText(listArr[position]);
				Drawable d = getResources().getDrawable(img[position]);
				imgicon.setImageDrawable(d);

			}

			textView.setText(listArr[position]);
			Drawable d = getResources().getDrawable(img[position]);
			imgicon.setImageDrawable(d);
			textView.setTextColor(getResources().getColorStateList(
					R.color.textcolor_selector));

			ImageView imgarrow = (ImageView) view.findViewById(R.id.imgarrow);
			Drawable darrow = getResources().getDrawable(
					R.drawable.arrow_selector);
			imgarrow.setImageDrawable(darrow);

			LinearLayout ActiveItem = (LinearLayout) view;
			if (position == 0) {

				Drawable d1 = getResources().getDrawable(
						R.drawable.user_list_item_selector);
				ActiveItem.setBackgroundDrawable(d1);
				// for focus on it
				int top = (ActiveItem == null) ? 0 : ActiveItem.getTop();
				((ListView) parent).setSelectionFromTop(position, top);
			}

			return view;
		}
	}

}
