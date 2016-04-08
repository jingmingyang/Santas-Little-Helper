package me.chayut.SantaHelperLogic;

import android.util.Log;

import java.util.ArrayList;

import me.zhenning.EmailSender;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaHelperLogic {

    private static final String TAG = "SantaHelperLogic";

    private ArrayList<EndPoint> endPoints;
    private ArrayList<SantaTaskAppoint> mAppointTasks;
    private ArrayList<SantaTaskLocation> mLocationTasks;

    private ArrayList<SantaTask> testList;


    public SantaHelperLogic() {
        Log.d(TAG,"Santa Logic con");
        endPoints = new ArrayList<>();
        mAppointTasks = new ArrayList<>();
        mLocationTasks = new ArrayList<>();

        //Test interface

        testList = new ArrayList<>();
        SantaTaskAppoint mTask = new SantaTaskAppoint();
        testList.add(mTask);

        SantaTask gotTask =  testList.get(0);

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

    public ArrayList<SantaTaskAppoint> getmAppointTasks() {
        return mAppointTasks;
    }

    public ArrayList<SantaTaskLocation> getmLocationTasks() {
        return mLocationTasks;
    }

    public boolean addAppointTask (SantaTaskAppoint mAT){
        Log.d(TAG, "addAppointTask");
        mAppointTasks.add(mAT);
        return true;
    }

    public boolean addLocationTask (SantaTaskLocation mLT){
        mLocationTasks.add(mLT);
        return true;
    }

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

    public void onLocationUpdateReceived(){

    }

    public void onBatteryPercentageReceived(){

    }

    public void onTimeUpDateReceived(){

    }








}
