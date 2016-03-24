package me.chayut.santaslittlehelper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SantaService extends Service {

    private final static String TAG = "SantaService";


    public SantaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Log.i(TAG, "Service onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand()");

        if (intent.hasExtra("Extra")){
            Log.i(TAG, "Has Extra");
        }

        return 0;
    }

}
