package me.chayut.santaslittlehelper;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import me.chayut.SantaHelperLogic.SantaLogic;

public class SantaService extends Service {

    private final static String TAG = "SantaService";

    private SantaLogic mSantaLogic;
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

        //init monitoring service
        initBattMon();
        initLocMon();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand()");

        if (intent.hasExtra("Extra")){
            Log.i(TAG, "Has Extra");
        }

        if(!mLogicInitialized) {
            mSantaLogic = new SantaLogic(this);
            mLogicInitialized = true;
        }

        return 0;
    }

    /** method for clients */

    public String getHello(){
        return "Santa Service Hello";
    }

    /**
     * return app logic to any binding activity
     * @return SantaLogic
     */
    public SantaLogic getSantaLogic(){
        if (!mLogicInitialized) {
            mSantaLogic = new SantaLogic(this);

        }
        return mSantaLogic;
    }


    /** untested */
    private void initBattMon(){

        BroadcastReceiver batteryLevel = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);

                //Log.d(TAG,"Batt: " + currentLevel + "%");

                //report battery level to app logic
                mSantaLogic.onBatteryPercentageReceived(currentLevel);
            }
        };

        IntentFilter batteryLevelFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevel, batteryLevelFilter);
    }


    private void initLocMon(){

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        /* ********************************************************************************************************************************************************* */
        // Register the listener LocationManager
        try
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            mSantaLogic.onLocationUpdateReceived(location);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };

    
}
