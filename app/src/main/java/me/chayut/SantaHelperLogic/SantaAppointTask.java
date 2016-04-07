package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaAppointTask implements Parcelable, SantaTask {

    public EndPoint getmEndpoint() {
        return mEndpoint;
    }

    public void setmEndpoint(EndPoint mEndpoint) {
        this.mEndpoint = mEndpoint;
    }

    private EndPoint mEndpoint ;

    public SantaAppointTask() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEndpoint, flags);
    }

    protected SantaAppointTask(Parcel in) {
        this.mEndpoint = in.readParcelable(EndPoint.class.getClassLoader());
    }

    public static final Parcelable.Creator<SantaAppointTask> CREATOR = new Parcelable.Creator<SantaAppointTask>() {
        @Override
        public SantaAppointTask createFromParcel(Parcel source) {
            return new SantaAppointTask(source);
        }

        @Override
        public SantaAppointTask[] newArray(int size) {
            return new SantaAppointTask[size];
        }
    };
}
