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



  public class SMS extends Activity {
    Button btnSendSMS;
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
       

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = "911";// you can set the specific telephone number,such as 911
                String message = "Hello World";// you can set the specific message text,such as "Hello World"
                if (phoneNo.length() > 0 && message.length() > 0)
                    sendSMS(phoneNo, message);
                else
                    Toast.makeText(getBaseContext(), "Please enter both number and message.",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
  private void sendSMS(String phoneNumber,String message)
    {
       // PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SMS.class), 0);
        SmsManager sms = SmsManager.getDefault();
       // sms.sendTextMessage(phoneNumber,null,message,pi,null);

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),"SMS sent",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),"Generic faiture",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(),"No service",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(),"Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(),"Radio off",Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        },new IntentFilter(SENT));
registerReceiver(new BroadcastReceiver() {
    @Override
    public void onReceive(Context arg0, Intent arg01) {
        switch(getResultCode())
        {
            case Activity.RESULT_OK:
                Toast.makeText(getBaseContext(),"SMS delivered",
                        Toast.LENGTH_SHORT).show();
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(getBaseContext(),"SMS not delivered",
                        Toast.LENGTH_SHORT).show();
                break;

        }
    }
},new IntentFilter(DELIVERED));
        sms.sendTextMessage(phoneNumber,null,message,sentPI,deliveredPI);
    }

}


