package me.chayut.SantaHelperLogic;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import me.zhenning.EmailSender;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaHelperLogic {

    private static final String TAG = "SantaHelperLogic";

    public static final String EXTRA_SANTA_TASK = "EXTRA_SANTA_TASK";
    public static final String EXTRA_SANTA_TASK_APPOINT = "EXTRA_SANTA_TASK_APPOINT";
    public static final String EXTRA_SANTA_TASK_BATT = "EXTRA_SANTA_TASK_BATT";
    public static final String EXTRA_SANTA_TASK_LOC = "EXTRA_SANTA_TASK_LOC";
    public static final String EXTRA_SANTA_ACTION = "EXTRA_SANTA_ACTION";

    private ArrayList<EndPoint> endPoints;

    private ArrayList<SantaTask> taskList;

    Context mContext;


    public SantaHelperLogic(Context context) {

        Log.d(TAG,"Santa Logic con");

        mContext = context;

        endPoints = new ArrayList<>();
        taskList = new ArrayList<>();

        //Test interface
        SantaTaskAppoint mTask = new SantaTaskAppoint();
        taskList.add(mTask);

        SantaTask gotTask =  taskList.get(0);

        if (gotTask instanceof SantaTaskAppoint) {
            Log.d(TAG,"SantaTaskAppoint");
        }
        else if (gotTask instanceof SantaTaskLocation){
            Log.d(TAG,"SantaTaskLocation");
        }

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


    public boolean addAppointTask (SantaTaskAppoint mAT){
        Log.d(TAG, "addAppointTask");
        taskList.add(mAT);
        return true;
    }

    public boolean addLocationTask (SantaTaskLocation mLT){
        taskList.add(mLT);
        return true;
    }

    public boolean addTask (SantaTask task){
        taskList.add(task);
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

    }

    public void onBatteryPercentageReceived(int percentage){

    }

    public void onTimeUpDateReceived(){

    }








}
