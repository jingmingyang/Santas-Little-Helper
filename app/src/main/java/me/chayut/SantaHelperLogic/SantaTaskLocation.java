package me.chayut.SantaHelperLogic;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskLocation extends SantaTask implements Parcelable  {

    private static final String TAG = "SantaTaskLocation";



    private SantaAction mAction;
    private Location mLocation;
    private int mRange;

    public SantaTaskLocation(SantaAction action) {

        mAction =action;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mAction, flags);
        dest.writeParcelable(this.mLocation, flags);
        dest.writeInt(this.mRange);
    }

    protected SantaTaskLocation(Parcel in) {
        this.mAction = in.readParcelable(SantaAction.class.getClassLoader());
        this.mLocation = in.readParcelable(Location.class.getClassLoader());
        this.mRange = in.readInt();
    }

    public static final Creator<SantaTaskLocation> CREATOR = new Creator<SantaTaskLocation>() {
        @Override
        public SantaTaskLocation createFromParcel(Parcel source) {
            return new SantaTaskLocation(source);
        }

        @Override
        public SantaTaskLocation[] newArray(int size) {
            return new SantaTaskLocation[size];
        }
    };


    @Override
    public JSONObject toJSONObject (){

        JSONObject mObject = new JSONObject();

        try{
            mObject.put(SantaLogic.JTAG_SANTA_TASK_TYPE,SantaLogic.JTAG_SANTA_TASK_LOC);

            Gson gson = new Gson();
            mObject.put("Task",new JSONObject(gson.toJson(mAction)));
            Log.d(TAG,gson.toJson(mAction) );
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return  mObject;
    }

    /** Setter and Getter */
    public SantaAction getAction() {
        return mAction;
    }

    public void setAction(SantaAction mAction) {
        this.mAction = mAction;
    }

}
