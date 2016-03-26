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
import android.widget.ListView;

import java.util.ArrayList;

import me.chayut.SantaHelperLogic.EndPoint;
import me.chayut.SantaHelperLogic.EndPointAdapter;
import me.chayut.SantaHelperLogic.SantaHelperLogic;

public class manageEndpointActivity extends AppCompatActivity {


    private final static String TAG = "manageEndpointActivity";

    SantaService mService;
    SantaHelperLogic mLogic;
    boolean mBound = false;

    ListView lvEndpoints;
    EndPointAdapter mAdapter;
    ArrayList<EndPoint> list = new ArrayList<EndPoint>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_endpoint);


        //Listview setup
        lvEndpoints = (ListView) findViewById(R.id.listViewEndPoints);
        mAdapter = new EndPointAdapter(this,R.layout.row_endpoint, list);

    }

    private void ListUpdate(){
        lvEndpoints.setAdapter(mAdapter);
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


    /** Methods */

    public void onBtnClicked (View v){
        Log.d(TAG,"onBtnClicked");

        if(mBound){
            mLogic = mService.getSantaLogic();
            ArrayList<EndPoint> mLogicEndPoints = mLogic.getEndPoints();
            Log.d(TAG, String.format("Endpoints = %d", mLogicEndPoints.size()) );
            EndPoint mEndpoint = new EndPoint(EndPoint.TYPE_EMAIL, "HELLO", "HELLO@ITS.ME");
            mLogic.addEndPoint(mEndpoint);

        }
    }

}
