package me.chayut.santaslittlehelper;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.chayut.SantaHelperLogic.SantaAction;
import me.chayut.SantaHelperLogic.SantaLocation;
import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskAppoint;
import me.chayut.SantaHelperLogic.SantaTaskBattery;
import me.chayut.SantaHelperLogic.SantaTaskLocation;

public class SetupTaskLocationActivity extends AppCompatActivity {


    static final int REQUEST_ACTION =1;
    private final static String TAG = "SetupTaskLocationAct";
    SantaService mService;
    SantaLogic mLogic;
    boolean mBound = false;
    SantaTaskLocation mTask;
    private Button btnOK, btnCancel,btnSetAction,btnSelectLocation;
    private TextView tvActionDetail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_task_location);

        //Setup UI
        tvActionDetail = (TextView) findViewById(R.id.tvActionDetails);


        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();

                        //TODO[UI]: read value from UI before return

                        //TODO[UI]: verify the the user input is valid

                        intent.putExtra(SantaLogic.EXTRA_SANTA_TASK_LOC,mTask);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
        );
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                }
        );

        btnSetAction = (Button) findViewById(R.id.btnSetAction);
        btnSetAction.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(SetupTaskLocationActivity.this,SelectActionActivity.class);
                        intent.putExtra(SantaLogic.EXTRA_SANTA_ACTION,mTask.getAction());
                        startActivityForResult(intent,REQUEST_ACTION);
                    }
                });


        btnSelectLocation = (Button) findViewById(R.id.btnChooseLocation);
        btnSelectLocation.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        final ArrayList<SantaLocation> locList = mLogic.getLocationList();

                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SetupTaskLocationActivity.this);
                        builderSingle.setIcon(R.mipmap.ic_launcher);
                        builderSingle.setTitle("Select One Name:-");

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                SetupTaskLocationActivity.this,
                                android.R.layout.select_dialog_singlechoice);

                        for (SantaLocation loc:locList)
                        {
                            arrayAdapter.add(loc.getName());
                        }

                        builderSingle.setNegativeButton(
                                "cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builderSingle.setAdapter(
                                arrayAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String strName = arrayAdapter.getItem(which);

                                        SantaLocation loc = locList.get(which);

                                        //TODO load location to UI
                                        //dont need convert back to SantaLocation object, simpler
                                        double latitude = loc.getLatitude();
                                        double longitude = loc.getLongitude();

                                    }
                                });
                        builderSingle.show();


                    }
                });


        //Try Get parcelable
        if(getIntent().hasExtra(SantaLogic.EXTRA_SANTA_TASK_LOC))
        {
            mTask =getIntent().getParcelableExtra(SantaLogic.EXTRA_SANTA_TASK_LOC);

            //if there is parcelable, load value to UI

            //TODO[UI], load this value to UI

            //dont need convert back to SantaLocation object, simpler
            double latitude = mTask.getLatitude();
            double longitude = mTask.getLongitude();
            double range = mTask.getRange();


            tvActionDetail.setText(mTask.getAction().getTaskTypeString());

        }
        else
        {
            //if no intent parcelable, create new
            mTask = new SantaTaskLocation(new SantaAction(),43.0f,43.0f,20.2f);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,String.format("onActivityResult(): %d: %d", requestCode,resultCode));

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_ACTION:

                    SantaAction returnAction = (SantaAction) data.getParcelableExtra(SantaLogic.EXTRA_SANTA_ACTION);
                    mTask.setAction(returnAction);

                    // update UI according to returned action
                    tvActionDetail.setText(returnAction.getTaskTypeString());
                    Log.d(TAG, SantaLogic.EXTRA_SANTA_ACTION);
                    break;
                default:
                    break;
            }
        }


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

}
