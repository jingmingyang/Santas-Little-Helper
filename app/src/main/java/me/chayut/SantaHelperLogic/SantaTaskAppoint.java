package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskAppoint extends SantaTask implements Parcelable {

    private static final String TAG = "SantaTaskAppoint";

    private SantaAction mAction;
    private String uuid;

    private String timeString  = "" ;


    public SantaTaskAppoint(SantaAction mAction, String uuid, String timeString) {
        this.mAction = mAction;
        this.uuid = uuid;
        this.timeString = timeString;
    }

    public SantaTaskAppoint() {
        mAction = new SantaAction();
        uuid = SantaUtilities.getNewUUID();

    }


    public SantaTaskAppoint(SantaAction action) {
        mAction = action;
        uuid = SantaUtilities.getNewUUID();
    }




    @Override
    public JSONObject toJSONObject (){

        JSONObject mObject = new JSONObject();

        try{
            mObject.put(SantaLogic.JTAG_SANTA_TASK_TYPE,SantaLogic.JTAG_SANTA_TASK_APPOINT);
            mObject.put(SantaLogic.JTAG_SANTA_DATETIME,timeString);
            mObject.put(SantaLogic.JTAG_UUID,uuid);

            Gson gson = new Gson();
            mObject.put(SantaLogic.JTAG_SANTA_ACTION,new JSONObject( gson.toJson(mAction)));
            Log.d(TAG,gson.toJson(mAction) );
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return  mObject;
    }


    /** getter setter */

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public SantaAction getAction() {
        return mAction;
    }

    public void setAction(SantaAction mAction) {
        this.mAction = mAction;
    }

    public boolean isConditionMet(String time){
        return false;
    }


    public SantaTaskAppoint(String timeString, SantaAction mAction) {
        this.timeString = timeString;
        this.mAction = mAction;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mAction, flags);
        dest.writeString(this.uuid);
        dest.writeString(this.timeString);
    }

    protected SantaTaskAppoint(Parcel in) {
        this.mAction = in.readParcelable(SantaAction.class.getClassLoader());
        this.uuid = in.readString();
        this.timeString = in.readString();
    }

    public static final Creator<SantaTaskAppoint> CREATOR = new Creator<SantaTaskAppoint>() {
        @Override
        public SantaTaskAppoint createFromParcel(Parcel source) {
            return new SantaTaskAppoint(source);
        }

        @Override
        public SantaTaskAppoint[] newArray(int size) {
            return new SantaTaskAppoint[size];
        }
    };
}
