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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.chayut.santaslittlehelper.AlarmActivity;
import me.zhenning.EmailSender;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaLogic {

    /** const */
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
    public static final String JTAG_UUID = "uuid";
    private static final String TAG = "SantaLogic";

    Context mContext;
    ScheduledThreadPoolExecutor sch;
    private ArrayList<EndPoint> endPoints;
    private ArrayList<SantaLocation> locationList;
    private ArrayList<SantaTask> taskList;
    final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            SantaLogic.this.onLocationUpdateReceived(location);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };
    final Runnable periodicTask = new Runnable(){

        Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void run() {
            try{
                Log.d(TAG,"periodicTask.run()");

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {

                        checkTimeOnTaskAppoint();
                        //TODO: check anything on UI thread
                    } // This is your code
                };
                mHandler.post(myRunnable);


            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    private LocationManager locationManager;
    private String mLoadedEmail = "";
    private String mLoadedPassword = "";
    private boolean creadentialLoaded = false;

    public SantaLogic(Context context) {

        Log.d(TAG,"Santa Logic initiated");

        mContext = context;

        endPoints = new ArrayList<>();
        taskList = new ArrayList<>();
        locationList = new ArrayList<>();

        //read config on start
        readSantaConfig();

        //load user credential on start
        loadUserCredential();


        //init monitoring service
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        initBattMon();
        initLocMon();

        //start periodical task
        // Create a scheduled thread pool with 5 core threads
        sch = (ScheduledThreadPoolExecutor)
                Executors.newScheduledThreadPool(5);
        //TODO: set repeatiion time according to condition.
        ScheduledFuture<?> periodicFuture = sch.scheduleAtFixedRate(periodicTask, 0, 5, TimeUnit.MINUTES);

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

    //region task
    public boolean addTask (SantaTask task){

        taskList.add(task);
        //TODO: check if task UUID already exist, update instead


        //update config file
        writeSantaConfig();

        return true;
    }

    public SantaTask findTaskByUUID (String taskUUID){
        SantaTask returnTask = null;
        //TODO: return task from list by UUID


        return returnTask;
    }

    public boolean addLocation (SantaLocation location){
        locationList.add(location);
        return true;
    }
    //endregion


    //region runnable

    //
    public void loadUserCredential()
    {
        //TODO: Zhenning -> loadUserCredential;

        mLoadedEmail = ""; //TODO: set value here
        mLoadedPassword = ""; //TODO: set value here
        creadentialLoaded = true;
    }

    //endregion


    //region test

    /** Test  Section */

    public void sendEmailTest(String email,String password)
    {
        //TODO:use loadedUserCredential
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


    //region on receive update

    public void onWifi (){
        SantaFunction.toggleWiFi(mContext,true);
    }

    //endregion

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
                    executeAction(action);
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
                    executeAction(action);
                }

            }

        }

    }

    public void checkTimeOnTaskAppoint(){

        for (SantaTask mTask : taskList)
        {
            if (mTask instanceof SantaTaskBattery) {
                SantaTaskAppoint task = (SantaTaskAppoint) mTask;

                if(task.isConditionMet()){

                    //issue alarm before take action
                    Intent i = new Intent(mContext,AlarmActivity.class);
                    i.putExtra(SantaLogic.EXTRA_SANTA_TASK_APPOINT,task);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Log.e(TAG,"HEHEHE running~");
                    mContext.startActivity(i);
                }

            }

        }
    }

    public void onAlarmMissed(String uuid){
        //TODO: call alarm missed when user miss the alarm

        //TODO take action


    }
    //endregion


    //region execute action
    /** Execute Action Section */
    public void executeAction(SantaAction action){

        Log.d(TAG,"executeAction()");

        switch (action.getTaskType()){
            case SantaAction.ACTION_EMAIL:
                //TODO get info and send email
            case SantaAction.ACTION_SMS:
                //TODO: get info send sms
            case SantaAction.ACTION_WIFI:
                //TODO: turn on/off wifi
                break;
            default:
        }

    }


    //endregion

    //region JSON config



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

    //endregion

    public boolean readSantaConfig(){

        JSONObject mObject = SantaUtilities.readConfigFromFile();

        try {

            //load json into object list
            JSONArray taskArray = mObject.getJSONArray(JTAG_SANTA_TASK_LIST);

            Log.d(TAG,taskArray.toString());

            taskList.clear();

            for(int n = 0; n < taskArray.length(); n++)
            {
                JSONObject object = taskArray.getJSONObject(n);

                String taskType = object.getString(JTAG_SANTA_TASK_TYPE);

                Log.d(TAG,taskType);

                if(taskType.equals(JTAG_SANTA_TASK_APPOINT)){

                    String timeString = object.getString(JTAG_SANTA_DATETIME);
                    String uuid = object.getString(JTAG_UUID);
                    String actionString = object.getString(JTAG_SANTA_ACTION);

                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);

                    SantaTaskAppoint newTask = new SantaTaskAppoint(timeString,action);
                    newTask.setUuid(uuid);
                    addTask(newTask);
                    Log.d(TAG,taskType + " Added");
                }
                else if (taskType.equals(JTAG_SANTA_TASK_BATT)){

                    int battPercent = object.getInt(JTAG_SANTA_BATT_LEVEL);
                    String uuid = object.getString(JTAG_UUID);

                    String actionString = object.getString(JTAG_SANTA_ACTION);
                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);
                    SantaTaskBattery newTask = new SantaTaskBattery(battPercent,action);
                    newTask.setUuid(uuid);
                    addTask(newTask);
                    Log.d(TAG,taskType + " Added");
                }
                else if(taskType.equals(JTAG_SANTA_TASK_LOC)){

                    float lat = object.getLong(JTAG_SANTA_LAT);
                    float longitude = object.getLong(JTAG_SANTA_LONG);
                    float range = object.getLong(JTAG_SANTA_Range);
                    String uuid = object.getString(JTAG_UUID);

                    String actionString = object.getString(JTAG_SANTA_ACTION);
                    Gson gson = new Gson();
                    SantaAction action = gson.fromJson(actionString,SantaAction.class);

                    SantaTaskLocation newTask = new SantaTaskLocation(action,lat,longitude,range);
                    newTask.setUuid(uuid);
                    addTask(newTask);
                    Log.d(TAG,taskType + " Added");
                }

            }

            return true;

        }
        catch (JSONException e) {
            e.printStackTrace();

            return false;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
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

    //endregion



}
