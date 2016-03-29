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
    private ArrayList<SantaAppointTask> mAppointTasks;
    private ArrayList<SantaLocationTask> mLocationTasks;

    public SantaHelperLogic() {
        endPoints = new ArrayList<>();
        mAppointTasks = new ArrayList<>();
        mLocationTasks = new ArrayList<>();
    }

    public boolean addEndPoint (EndPoint mEP){
        Log.d(TAG, "addEndPoint");
        endPoints.add(mEP);
        return true;
    }

    public ArrayList<EndPoint> getEndPoints() {
        return endPoints;
    }

    public ArrayList<SantaAppointTask> getmAppointTasks() {
        return mAppointTasks;
    }

    public ArrayList<SantaLocationTask> getmLocationTasks() {
        return mLocationTasks;
    }

    public boolean addAppointTask (SantaAppointTask mAT){
        Log.d(TAG, "addAppointTask");
        mAppointTasks.add(mAT);
        return true;
    }

    public boolean addLocationTask (SantaLocationTask mLT){
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








}
