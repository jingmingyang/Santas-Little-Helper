package me.chayut.santaslittlehelper;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskAdapter;


public class TaskListActivity extends AppCompatActivity {

    private final static String TAG = "TaskListActivity";

    SantaService mService;
    SantaLogic mLogic;
    boolean mBound = false;

    private ListView lvTasks;
    private SantaTaskAdapter mAdapter;
    private ArrayList<SantaTask> list = new ArrayList<SantaTask>();

    //request Code
    static final int REQUEST_TASK_ALARM =1;
    static final int REQUEST_TASK_LOCATION= 2;
    static final int REQUEST_TASK_BATTERY =3;

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

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Select Type:");

        final String[] array = {"Alarm Task", "Location Task","Battery Task"};
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, String.format("Choose: %d", which));
                switch (which) {
                    case 0:
                        Intent startAct = new Intent(TaskListActivity.this, SetupTaskAlarmActivity.class);
                        startActivityForResult(startAct,REQUEST_TASK_ALARM);

                        break;
                    case 1:
                        Intent startAct2 = new Intent(TaskListActivity.this, SetupTaskLocationActivity.class);
                        startActivityForResult(startAct2,REQUEST_TASK_LOCATION);

                        break;
                    case 2:
                        Intent startAct3 = new Intent(TaskListActivity.this, SetupTaskBatteryActivity.class);
                        startActivityForResult(startAct3,REQUEST_TASK_BATTERY);

                        break;
                }

            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();

        UIRefresh();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,String.format("onActivityResult(): %d", requestCode));


        if(resultCode == RESULT_OK) {

            SantaTask mTask;

            switch (requestCode) {
                case REQUEST_TASK_ALARM:
                    mTask =  data.getParcelableExtra(SantaLogic.EXTRA_SANTA_TASK_APPOINT);
                    mLogic.addTask(mTask);
                    UIRefresh();
                    break;
                case REQUEST_TASK_LOCATION:
                     mTask =  data.getParcelableExtra(SantaLogic.EXTRA_SANTA_TASK_LOC);
                    mLogic.addTask(mTask);
                    UIRefresh();
                    break;
                case REQUEST_TASK_BATTERY:
                    mTask =  data.getParcelableExtra(SantaLogic.EXTRA_SANTA_TASK_BATT);
                    mLogic.addTask(mTask);
                    UIRefresh();
                    break;
                default:
                    break;
            }
        }
    }



}
