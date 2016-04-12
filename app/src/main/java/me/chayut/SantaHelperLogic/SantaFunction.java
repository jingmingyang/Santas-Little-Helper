package me.chayut.SantaHelperLogic;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by chayut on 12/04/16.
 */
 public class SantaFunction {


    static public void sendSMS(Context context, String phoneNumber,String message)
    {

        final String TAG = "sendSMS";

        SmsManager sms = SmsManager.getDefault();

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context,0,new Intent(SENT),0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context,0,new Intent(DELIVERED),0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "SMS sent");
                        //Toast.makeText(context,"SMS sent",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.d(TAG, "Generic faiture");
                        //Toast.makeText(context,"Generic faiture",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.d(TAG, "No service");
                        //Toast.makeText(getBaseContext(),"No service",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.d(TAG,"Null PDU");
                        //Toast.makeText(getBaseContext(),"Null PDU",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.d(TAG, "Radio off");
                        //Toast.makeText(getBaseContext(),"Radio off", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        },new IntentFilter(SENT));


        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg01) {
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "SMS delivered");
                        //Toast.makeText(getBaseContext(),"SMS delivered",Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d(TAG, "SMS not delivered");
                        //Toast.makeText(getBaseContext(),"SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        },new IntentFilter(DELIVERED));
        sms.sendTextMessage(phoneNumber,null,message,sentPI,deliveredPI);
    }
}
