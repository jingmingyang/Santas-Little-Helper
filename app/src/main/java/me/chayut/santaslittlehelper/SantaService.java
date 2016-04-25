package me.chayut.santaslittlehelper;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import me.chayut.SantaHelperLogic.SantaFunction;
import me.chayut.SantaHelperLogic.SantaHelperLogic;

public class SantaService extends Service {

    private final static String TAG = "SantaService";

    private SantaHelperLogic mSantaLogic;
    private boolean mLogicInitialized = false;


    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    /** Constructor */
    public SantaService() {
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        SantaService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SantaService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public void onCreate() {
        Log.i(TAG, "Service onCreate()");
        getBatteryPercentage();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand()");

        if (intent.hasExtra("Extra")){
            Log.i(TAG, "Has Extra");
        }

        if(!mLogicInitialized) {
            mSantaLogic = new SantaHelperLogic(this);
            mLogicInitialized = true;
        }

        return 0;
    }

    /** method for clients */
    public String getHello(){
        return "Hello";
    }

    public SantaHelperLogic getSantaLogic(){
        if (!mLogicInitialized) {
            mSantaLogic = new SantaHelperLogic(this);

        }
        return mSantaLogic;
    }


    /** untested */
    private void getBatteryPercentage(){

        BroadcastReceiver batteryLevel = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);

                Log.d(TAG,"Batt: " + currentLevel + "%");
            }
        };

        IntentFilter batteryLevelFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevel, batteryLevelFilter);

    }




    
}
