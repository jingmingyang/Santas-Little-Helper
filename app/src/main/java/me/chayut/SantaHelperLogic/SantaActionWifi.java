package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaActionWifi  extends SantaAction implements Parcelable {

    public SantaActionWifi() {
    }

    public String getInfo (){

        return "Wifi";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected SantaActionWifi(Parcel in) {
    }

    public static final Parcelable.Creator<SantaActionWifi> CREATOR = new Parcelable.Creator<SantaActionWifi>() {
        @Override
        public SantaActionWifi createFromParcel(Parcel source) {
            return new SantaActionWifi(source);
        }

        @Override
        public SantaActionWifi[] newArray(int size) {
            return new SantaActionWifi[size];
        }
    };
}
