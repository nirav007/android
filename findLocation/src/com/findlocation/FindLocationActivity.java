package com.findlocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindLocationActivity extends Activity {
	 String DestinationAddress;
     EditText destinationText;
     Button cancelRecordButton;
     String str;
 
	public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.main);
          
          destinationText = (EditText)findViewById(R.id.editbox);
          cancelRecordButton = (Button) findViewById(R.id.Button);
                    cancelRecordButton.setOnClickListener(new OnClickListener() {
                           
                            @Override
							public void onClick(View v) {
                            checkAddress();
                            }
                    });
      }
      void checkAddress()
            {
                    Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                    DestinationAddress = destinationText.getText().toString();
                    List<Address> addressList = null;
                  //  List<Address> addressList1 = null;
                    try {
                            addressList = geoCoder.getFromLocationName("Rajkot , Gujrat",1600);
                        //   addressList1 = geoCoder.getFromLocation(18.5204303,73.8567437, 1);
                    } catch (IOException e) {
                            Toast.makeText(FindLocationActivity.this,"Location not found!!! Please change address and try again.",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                    }
                   
                   
                    {
                    Address address = addressList.get(0);
                 //                    
                    //if(address.hasLatitude() && address.hasLongitude())
                    {
                           
                            Toast.makeText(FindLocationActivity.this,"Address : "+address.getAddressLine(0)+"\nCountry : "+address.getCountryName()+"\nLatitute : "+address.getLatitude()+"\nLongitute : "+address.getLongitude(),Toast.LENGTH_LONG).show();
                    
                    
                    }
                    }
            }
}