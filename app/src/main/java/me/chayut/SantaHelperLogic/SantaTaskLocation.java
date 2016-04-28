package me.chayut.SantaHelperLogic;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskLocation extends SantaTask implements Parcelable  {

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
}
