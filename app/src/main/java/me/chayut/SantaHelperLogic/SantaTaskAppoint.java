package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskAppoint implements Parcelable, SantaTask {

    public EndPoint getmEndpoint() {
        return mEndpoint;
    }

    public void setmEndpoint(EndPoint mEndpoint) {
        this.mEndpoint = mEndpoint;
    }

    private EndPoint mEndpoint ;

    public SantaTaskAppoint() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEndpoint, flags);
    }

    protected SantaTaskAppoint(Parcel in) {
        this.mEndpoint = in.readParcelable(EndPoint.class.getClassLoader());
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
