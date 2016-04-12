package me.chayut.SantaHelperLogic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import me.zhenning.EmailSender;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaHelperLogic {

    private static final String TAG = "SantaHelperLogic";

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

    /** on received update  Section */

    public void onLocationUpdateReceived(){

    }

    public void onBatteryPercentageReceived(){

    }

    public void onTimeUpDateReceived(){

    }








}
