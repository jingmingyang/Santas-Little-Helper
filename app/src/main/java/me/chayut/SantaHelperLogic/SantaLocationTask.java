package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaLocationTask implements Parcelable,SantaTask {

    public EndPoint getmEndPoint() {
        return mEndPoint;
    }

    public void setmEndPoint(EndPoint mEndPoint) {
        this.mEndPoint = mEndPoint;
    }

    private EndPoint mEndPoint;

    public SantaLocationTask() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEndPoint, flags);
    }

    protected SantaLocationTask(Parcel in) {
        this.mEndPoint = in.readParcelable(EndPoint.class.getClassLoader());
    }

    public static final Parcelable.Creator<SantaLocationTask> CREATOR = new Parcelable.Creator<SantaLocationTask>() {
        @Override
        public SantaLocationTask createFromParcel(Parcel source) {
            return new SantaLocationTask(source);
        }

        @Override
        public SantaLocationTask[] newArray(int size) {
            return new SantaLocationTask[size];
        }
    };
}
