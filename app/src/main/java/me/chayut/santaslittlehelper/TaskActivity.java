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
import me.chayut.SantaHelperLogic.SantaActionSendSMS;
import me.chayut.SantaHelperLogic.SantaHelperLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskAdapter;
import me.chayut.SantaHelperLogic.SantaTaskAppoint;
import me.chayut.SantaHelperLogic.SantaTaskBattery;
import me.chayut.SantaHelperLogic.SantaTaskLocation;


public class TaskActivity extends AppCompatActivity {

    private final static String TAG = "TaskActivity";

    SantaService mService;
    SantaHelperLogic mLogic;
    boolean mBound = false;

    private ListView lvTasks;
    private SantaTaskAdapter mAdapter;
    private ArrayList<SantaTask> list = new ArrayList<SantaTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //Listview setup
        lvTasks = (ListView) findViewById(R.id.listViewTasks);
    }

    private void ListUpdate(){
        Log.d(TAG,String.format("List Size: %d", list.size()));
        mAdapter = new SantaTaskAdapter(this,R.layout.row_task_appoint,list);
        lvTasks.setAdapter(mAdapter);
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

            UIRefresh();


            Log.d(TAG,mService.getHello());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    private void UIRefresh(){
        list = mService.getSantaLogic().getTaskList();
        ListUpdate();
    }

    public void btnAddTaskOnClick(View view){
        Log.d(TAG,"btnAddTaskOnClick()");

        mLogic.addTask(new SantaTaskAppoint(new SantaActionSendSMS()));
        mLogic.addTask(new SantaTaskLocation(new SantaActionSendSMS()));
        mLogic.addTask(new SantaTaskBattery(40,new SantaActionSendSMS()));

        UIRefresh();
    }



}
