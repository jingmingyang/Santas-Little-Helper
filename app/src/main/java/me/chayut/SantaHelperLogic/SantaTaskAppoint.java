package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskAppoint extends SantaTask implements Parcelable {


    private SantaAction mAction;


    public SantaTaskAppoint() {
    }


    public SantaTaskAppoint(SantaAction action) {
        mAction = action;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected SantaTaskAppoint(Parcel in) {

    }

    public static final Parcelable.Creator<SantaTaskAppoint> CREATOR = new Parcelable.Creator<SantaTaskAppoint>() {
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
