package com.example.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class CalleeHelperActivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent receivedIntent) {
        // TODO Auto-generated method stub


        Bundle b = receivedIntent.getExtras();
        SmsMessage[] msgs = null;
        String str ="";

        if(b!=null)
        {
            Object[] pdus = (Object[])b.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for(int i=0; i<msgs.length; i++)
            {
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS from "+ msgs[i].getOriginatingAddress();
                str += " : ";
                str += msgs[i].getMessageBody().toString();
                str += "\n";
            }

            Toast.makeText(arg0, str, 50).show();

        }
        else{
        Toast.makeText(arg0, "There is a problem", 50).show();
        }
    }
}