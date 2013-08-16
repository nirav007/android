package com.example.contactautocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

private ArrayList<Map<String, String>> mPeopleList;
private SimpleAdapter mAdapter;
private MultiAutoCompleteTextView mTxtPhoneNo;


public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mPeopleList = new ArrayList<Map<String, String>>();
    PopulatePeopleList();
    mTxtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.mmWhoNo);
    mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.custcontview,
            new String[] { "Name", "Phone", "Type" }, new int[] {
                    R.id.ccontName, R.id.ccontNo, R.id.ccontType });
    mTxtPhoneNo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    mTxtPhoneNo.setAdapter(mAdapter);
    

	
    mTxtPhoneNo.setOnItemClickListener(new OnItemClickListener() {

        
        public void onItemClick(AdapterView<?> av, View arg1, int index,
                long arg3) {
            Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

            String name  = map.get("Name");
            String number = map.get("Phone");
            //mTxtPhoneNo.setText(""+name+"<"+number+">");
            mTxtPhoneNo.setText(name);

        }



    });

}

public void PopulatePeopleList() {
    mPeopleList.clear();
    Cursor people = getContentResolver().query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    while (people.moveToNext()) {
        String contactName = people.getString(people
                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String contactId = people.getString(people
                .getColumnIndex(ContactsContract.Contacts._ID));
        String hasPhone = people
                .getString(people
                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        if ((Integer.parseInt(hasPhone) > 0)){
            // You know have the number so now query it like this
            Cursor phones = getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
            null, null);
            while (phones.moveToNext()){
                //store numbers and display a dialog letting the user select which.
                String phoneNumber = phones.getString(
                phones.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER));
                String numberType = phones.getString(phones.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.TYPE));
                Map<String, String> NamePhoneType = new HashMap<String, String>();
                NamePhoneType.put("Name", contactName);
                NamePhoneType.put("Phone", phoneNumber);
                if(numberType.equals("0"))
                    NamePhoneType.put("Type", "Work");
                    else
                    if(numberType.equals("1"))
                    NamePhoneType.put("Type", "Home");
                    else if(numberType.equals("2"))
                    NamePhoneType.put("Type",  "Mobile");
                    else
                    NamePhoneType.put("Type", "Other");
                    //Then add this map to the list.
                    mPeopleList.add(NamePhoneType);
            }
            phones.close();
        }
    }
    people.close();
    startManagingCursor(people);
}

public void onItemClick(AdapterView<?> av, View v, int index, long arg){
    Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);
    Iterator<String> myVeryOwnIterator = map.keySet().iterator();
    while(myVeryOwnIterator.hasNext()) {
        String key=(String)myVeryOwnIterator.next();
        String value=(String)map.get(key);
       // mTxtPhoneNo.setText(value);
    }
}


}