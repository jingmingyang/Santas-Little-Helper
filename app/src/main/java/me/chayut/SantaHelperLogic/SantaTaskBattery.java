package me.chayut.SantaHelperLogic;

/**
 * Created by chayut on 8/04/16.
 */
public class SantaTaskBattery implements SantaTask {

    private static final String TAG = "SantaTaskBattery";


    private int mBattPercentage;
    private SantaAction mAction;

    public SantaTaskBattery(int thresholdPercentage,SantaAction action){
        mAction = action;
        mBattPercentage = thresholdPercentage;

    }



}
