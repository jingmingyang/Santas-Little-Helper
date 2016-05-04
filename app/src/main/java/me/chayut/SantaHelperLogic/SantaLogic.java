package me.chayut.SantaHelperLogic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.chayut.santaslittlehelper.R;
import me.zhenning.EmailSender;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaLogic {

    private static final String TAG = "SantaLogic";

    public static final String EXTRA_SANTA_TASK = "EXTRA_SANTA_TASK";
    public static final String EXTRA_SANTA_TASK_APPOINT = "EXTRA_SANTA_TASK_APPOINT";
    public static final String EXTRA_SANTA_TASK_BATT = "EXTRA_SANTA_TASK_BATT";
    public static final String EXTRA_SANTA_TASK_LOC = "EXTRA_SANTA_TASK_LOC";
    public static final String EXTRA_SANTA_ACTION = "EXTRA_SANTA_ACTION";
    public static final String EXTRA_SANTA_LOCATION= "EXTRA_SANTA_LOCATION";

    public static final String JTAG_SANTA_TASK_TYPE = "SantaTaskType";
    public static final String JTAG_SANTA_ACTION= "Action";
    public static final String JTAG_SANTA_TASK_APPOINT = "SANTA_TASK_APPOINT";
    public static final String JTAG_SANTA_DATETIME = "DateTime";
    public static final String JTAG_SANTA_TASK_BATT = "SANTA_TASK_BATT";
    public static final String JTAG_SANTA_BATT_LEVEL = "BattLevel";
    public static final String JTAG_SANTA_TASK_LOC = "SANTA_TASK_LOC";
    public static final String JTAG_SANTA_LAT = "Lat";
    public static final String JTAG_SANTA_LONG = "Long";
    public static final String JTAG_SANTA_Range = "Range";
    public static final String JTAG_SANTA_TASK_LIST = "TaskList";

    private ArrayList<EndPoint> endPoints;

    private ArrayList<SantaLocation> locationList;
    private ArrayList<SantaTask> taskList;

    Context mContext;

    private LocationManager locationManager;


    public SantaLogic(Context context) {

        Log.d(TAG,"Santa Logic initiated");

        mContext = context;

        endPoints = new ArrayList<>();
        taskList = new ArrayList<>();
        locationList = new ArrayList<>();

        //init monitoring service

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        initBattMon();
        initLocMon();

    }

    public boolean addEndPoint (EndPoint mEP){
        Log.d(TAG, "addEndPoint");
        endPoints.add(mEP);
        return true;
    }

    public ArrayList<EndPoint> getEndPoints() {
        return endPoints;
    }

    public ArrayList<SantaTask> getTaskList() {
        return taskList;
    }

    public ArrayList<SantaLocation> getLocationList() {
        return locationList;
    }

    public boolean addTask (SantaTask task){
        taskList.add(task);
        return true;
    }

    public boolean addLocation (SantaLocation location){
        locationList.add(location);
        return true;
    }

    /** Test  Section */

    public void sendEmailTest(String email,String password)
    {
        try {
            EmailSender sender = new EmailSender(email, password);
            sender.sendMail("Test 2 ",
                    "Test 2",
                    "nonesecure@gmail.com",
                    "chayut_o@hotmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    public void sendSMS (String phoneNumber,String message){
        SantaFunction.sendSMS(mContext, phoneNumber,message);
    }

    public void onWifi (){
        SantaFunction.toggleWiFi(mContext,true);
    }

    /** on received update  Section */

    public void onLocationUpdateReceived(Location location){
        Log.d(TAG,String.format("Location: %s",location.toString()));

        //checking the tasks list
        for (SantaTask mTask : taskList)
        {
            if (mTask instanceof SantaTaskLocation) {
                SantaTaskLocation task = (SantaTaskLocation) mTask;

                if(task.isConditionMet(location)){
                    //execute action
                    SantaAction action = task.getAction();
                }
            }

        }
    }

    public void onBatteryPercentageReceived(int percentage){
        Log.d(TAG,String.format("Batt: %d",percentage));

        for (SantaTask mTask : taskList)
        {
            if (mTask instanceof SantaTaskBattery) {
                SantaTaskBattery task = (SantaTaskBattery) mTask;

                if(task.isConditionMet(percentage)){
                    //execute action
                    SantaAction action = task.getAction();
                }

            }

        }


    }

    public void onTimeUpDateReceived(){

    }

    /** JSON Section   */

    public JSONArray getTaskListJSON(){

        JSONArray jArray = new JSONArray();
        for (SantaTask mTask : taskList)
        {
            /*
            if (mTask instanceof SantaTaskAppoint) {

                SantaTaskAppoint task = (SantaTaskAppoint) mTask;
                jArray.put(task.toJSONObject());
            }
            else if  (mTask instanceof SantaTaskLocation){
                SantaTaskLocation task = (SantaTaskLocation) mTask;
                jArray.put(task.toJSONObject());
            }
            else if  (mTask instanceof SantaTaskBattery){
                SantaTaskBattery task = (SantaTaskBattery) mTask;
                jArray.put(task.toJSONObject());
            }
            */
            jArray.put(mTask.toJSONObject());

        }

        return jArray;
    }

    public void writeSantaConfig(){

        JSONObject mObject = new JSONObject();

        try {
            mObject.put(JTAG_SANTA_TASK_LIST, getTaskListJSON());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"writeSantaConfig()");
        Log.d(TAG,mObject.toString());
        SantaUtilities.saveConfigToFile(mObject);

    }

    public String readSantaConfig(){

        JSONObject mObject = SantaUtilities.readConfigFromFile();

        try {

            //TODO:load json into object list
            JSONArray taskArray = mObject.getJSONArray(JTAG_SANTA_TASK_LIST);

            taskList.clear();

            for(int n = 0; n < taskArray.length(); n++)
            {
                JSONObject object = taskArray.getJSONObject(n);

                String taskType = object.getString(JTAG_SANTA_TASK_TYPE);


                if(taskType == JTAG_SANTA_TASK_APPOINT){
                    String timeString = object.getString(JTAG_SANTA_DATETIME);


                    String actionString = object.getString(JTAG_SANTA_ACTION);
                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);

                    SantaTaskAppoint newTask = new SantaTaskAppoint(timeString,action);
                    addTask(newTask);
                }
                else if (taskType ==JTAG_SANTA_TASK_BATT){

                    int battPercent = object.getInt(JTAG_SANTA_BATT_LEVEL);

                    String actionString = object.getString(JTAG_SANTA_ACTION);
                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);
                    SantaTaskBattery newTask = new SantaTaskBattery(battPercent,action);
                    addTask(newTask);

                }
                else if(taskType ==JTAG_SANTA_TASK_LOC){

                    float lat = object.getLong(JTAG_SANTA_LAT);
                    float longitude = object.getLong(JTAG_SANTA_LONG);
                    float range = object.getLong(JTAG_SANTA_Range);

                    String actionString = object.getString(JTAG_SANTA_ACTION);
                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);

                    SantaTaskLocation newTask = new SantaTaskLocation(action,lat,longitude,range);
                    addTask(newTask);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mObject.toString();
    }

    //region updater
    private void initBattMon(){

        BroadcastReceiver batteryLevel = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);

                //Log.d(TAG,"Batt: " + currentLevel + "%");

                //report battery level to app logic
                SantaLogic.this.onBatteryPercentageReceived(currentLevel);
            }
        };

        IntentFilter batteryLevelFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(batteryLevel, batteryLevelFilter);
    }


    private void initLocMon(){


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
            SantaLogic.this.onLocationUpdateReceived(location);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };

    //endregion



}
