package com.example.piccontact;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final int CONTACT_PICKER_RESULT = 1001;
	private EditText emailEntry;
	private QuickContactBadge badgeLarge;

	public void onCreate(Bundle saveinst) {
		super.onCreate(saveinst);
		setContentView(R.layout.activity_main);

		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri result = null;

		
				Uri contactUri = data.getData();
				String s = contactUri.getPath();
				Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
				long id = Long.parseLong(contactUri.getLastPathSegment());
				badgeLarge.assignContactUri(contactUri);
				Bitmap image = loadPhoto(id);
				if (image != null) {
					badgeLarge.setImageBitmap(loadPhoto(id));
				} else {
					badgeLarge.setImageResource(R.drawable.ic_action_search);
				}
				
			
		
	}

	private Bitmap loadPhoto(long id) {
		
		Uri photoUri = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, id);
		InputStream photoInput = ContactsContract.Contacts
				.openContactPhotoInputStream(this.getContentResolver(),
						photoUri);
		if (photoInput != null) {
			return BitmapFactory.decodeStream(photoInput);
		}
		return null;
	}

}
