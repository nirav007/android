package com.example.smsreceiver;



import android.content.*;
import android.os.Bundle;
import android.telephony.*;
import android.util.Log;
import android.widget.Toast;

public class SimpleSmsReciever extends BroadcastReceiver {

 private static final String TAG = "Message recieved";

 @Override
 public void onReceive(Context context, Intent intent) {    
     Bundle pudsBundle = intent.getExtras();
     Object[] pdus = (Object[]) pudsBundle.get("pdus");
     SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);    
     Log.i(TAG,  messages.getMessageBody());
     Toast.makeText(context, "SMS Received : "+messages.getMessageBody(),
     Toast.LENGTH_LONG).show();
 }

}