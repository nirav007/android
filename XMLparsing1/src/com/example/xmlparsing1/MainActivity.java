package com.example.xmlparsing1;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	// String[] countryName ={"sd","sd","SD"};
	ArrayList<String> countryName = new ArrayList<String>();
	private ListView listView;
	private Button btnP;
	private Button btnN;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnP = (Button) findViewById(R.id.btnP);
		btnN = (Button) findViewById(R.id.btnN);

		try {
			URL url = new URL("http://bestandroid.webs.com/getcountry.xml");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Table");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				Element fstElement = (Element) node;

				NodeList nameList = fstElement.getElementsByTagName("Name");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();

				countryName.add(((Node) nameList.item(0)).getNodeValue()
						.toString());

				// Toast.makeText(getApplicationContext(),
				// ((Node)nameList.item(0)).getNodeValue().toString(),
				// Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG)
					.show();
		}

		listView = (ListView) findViewById(R.id.itemList);

		// String[] arr = new String[countryName.size()]; // full list
		String[] arr = new String[5]; // first five
		arr = countryName.toArray(arr);
		// for(String s : stockArr)
		// System.out.println(s);

		MyArrayAdapter adapter = new MyArrayAdapter(this, arr);
		listView.setAdapter(adapter);

		btnN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				String[] arr = { countryName.get(6).toString(),
						countryName.get(7).toString() };
				// next two record

				// arr = countryName.toArray(arr);
				// for(String s : stockArr)
				// System.out.println(s);

				MyArrayAdapter adapter = new MyArrayAdapter(MainActivity.this,
						arr);
				listView.setAdapter(adapter);
			}
		});

		btnP.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				String[] arr = { countryName.get(5).toString(),
						countryName.get(4).toString() };
				// previous two record

				// arr = countryName.toArray(arr);
				// for(String s : stockArr)
				// System.out.println(s);

				MyArrayAdapter adapter = new MyArrayAdapter(MainActivity.this,
						arr);
				listView.setAdapter(adapter);
			}
		});

		/* Find Tablelayout defined in main.xml */

		/* Create a new row to be added. */

		/* Add row to TableLayout. */

	}

	class MyArrayAdapter extends ArrayAdapter<String> {

		Activity context;
		String[] listArr;

		// private final integer[] image;

		public MyArrayAdapter(Activity context, String[] objects) {
			super(context, R.layout.homelistview, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			listArr = objects;

		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.homelistview, null, true);

			TextView textView = (TextView) view.findViewById(R.id.l);
			textView.setText("1");

			textView = (TextView) view.findViewById(R.id.c);
			textView.setText(listArr[position]);

			textView = (TextView) view.findViewById(R.id.r);
			textView.setText("100");

			return view;

		}

	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
