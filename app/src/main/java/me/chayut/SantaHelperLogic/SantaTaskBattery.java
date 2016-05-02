package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chayut on 8/04/16.
 */
public class SantaTaskBattery extends SantaTask implements Parcelable {

    private static final String TAG = "SantaTaskBattery";
    public static final String jTagBatt = "batteryLevel";

    private int mBattPercentage;

    public SantaAction getAction() {
        return mAction;
    }

    public void setAction(SantaAction mAction) {
        this.mAction = mAction;
    }

    private SantaAction mAction;

    public SantaTaskBattery(int thresholdPercentage,SantaAction action){
        mAction = action;
        mBattPercentage = thresholdPercentage;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mBattPercentage);
        dest.writeParcelable(this.mAction, flags);
    }

    protected SantaTaskBattery(Parcel in) {
        this.mBattPercentage = in.readInt();
        this.mAction = in.readParcelable(SantaAction.class.getClassLoader());
    }

    public static final Parcelable.Creator<SantaTaskBattery> CREATOR = new Parcelable.Creator<SantaTaskBattery>() {
        @Override
        public SantaTaskBattery createFromParcel(Parcel source) {
            return new SantaTaskBattery(source);
        }

        @Override
        public SantaTaskBattery[] newArray(int size) {
            return new SantaTaskBattery[size];
        }
    };

    @Override
    public JSONObject toJSONObject (){

        JSONObject mObject = new JSONObject();

        try{
            mObject.put(SantaLogic.JTAG_SANTA_TASK_TYPE,SantaLogic.JTAG_SANTA_TASK_BATT);
            mObject.put(jTagBatt,mBattPercentage);

            Gson gson = new Gson();
            mObject.put("Task",new JSONObject( gson.toJson(mAction)));
            Log.d(TAG,gson.toJson(mAction) );
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return  mObject;
    }

}
