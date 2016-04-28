package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 8/04/16.
 */
public class SantaTaskBattery extends SantaTask implements Parcelable {

    private static final String TAG = "SantaTaskBattery";


    private int mBattPercentage;
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
}
