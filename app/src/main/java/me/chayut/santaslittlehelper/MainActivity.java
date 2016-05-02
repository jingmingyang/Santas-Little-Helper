package me.chayut.santaslittlehelper;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.chayut.SantaHelperLogic.SantaLogic;

public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    SantaService mService;
    SantaLogic mLogic;
    boolean mBound = false;

    Button button1,btnManageLocation,btnTestJSON,btnReadConf;
    Button btnSendEmail,btnSendSMS,btnManageTask,btnWifiOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);

        //start service
        Intent intent = new Intent(MainActivity.this, SantaService.class);
        startService(intent);

        button1 = (Button) findViewById(R.id.btnManageEndpoints);
        button1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ManageEndpointActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if(mLogic!=null) {
                            mLogic.sendEmailTest("FOO@BAR.com", "BAR"); //test function
                        }
                    }
                }
        );


        btnSendSMS= (Button) findViewById(R.id.buttonSendSMS);
        btnSendSMS.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if(mLogic!=null) {
                            mLogic.sendSMS("+498167509", "This is Santa's Message"); //test function
                        }
                    }
                }
        );

        btnManageTask= (Button) findViewById(R.id.btnManageTask);
        btnManageTask.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btnManageLocation = (Button) findViewById(R.id.btnManageLocation);
        btnManageLocation.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ManageLocationActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btnWifiOn = (Button) findViewById(R.id.btnWifiOn);
        btnWifiOn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mLogic.onWifi(); //test function
                    }
                }
        );

        btnTestJSON = (Button) findViewById(R.id.btnJSON);
        btnTestJSON.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mLogic.writeSantaConfig();
                    }
                }
        );

        btnReadConf = (Button) findViewById(R.id.btnConfRead);
        btnReadConf.setOnClickListener(

                new View.OnClickListener() {
                    public void onClick(View v) {
                        String json =  mLogic.readSantaConfig();
                        Toast.makeText(getBaseContext(),json,
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(TAG, "onResume()");


        if(mBound){
            Log.d(TAG,mService.getHello());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, SantaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SantaService.LocalBinder binder = (SantaService.LocalBinder) service;
            mService = binder.getService();
            mLogic = mService.getSantaLogic();
            mBound = true;


            Log.d(TAG,mService.getHello());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


}
