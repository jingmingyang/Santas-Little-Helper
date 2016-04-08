package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskLocation implements Parcelable,SantaTask {

    public EndPoint getmEndPoint() {
        return mEndPoint;
    }

    public void setmEndPoint(EndPoint mEndPoint) {
        this.mEndPoint = mEndPoint;
    }

    private EndPoint mEndPoint;

    public SantaTaskLocation() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEndPoint, flags);
    }

    protected SantaTaskLocation(Parcel in) {
        this.mEndPoint = in.readParcelable(EndPoint.class.getClassLoader());
    }

    public static final Parcelable.Creator<SantaTaskLocation> CREATOR = new Parcelable.Creator<SantaTaskLocation>() {
        @Override
        public SantaTaskLocation createFromParcel(Parcel source) {
            return new SantaTaskLocation(source);
        }

        @Override
        public SantaTaskLocation[] newArray(int size) {
            return new SantaTaskLocation[size];
        }
    };
}
