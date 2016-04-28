package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaActionSendSMS extends SantaAction implements Parcelable{


    public SantaActionSendSMS() {
    }

    public String getInfo (){

        return "SMS";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected SantaActionSendSMS(Parcel in) {
    }

    public static final Parcelable.Creator<SantaActionSendSMS> CREATOR = new Parcelable.Creator<SantaActionSendSMS>() {
        @Override
        public SantaActionSendSMS createFromParcel(Parcel source) {
            return new SantaActionSendSMS(source);
        }

        @Override
        public SantaActionSendSMS[] newArray(int size) {
            return new SantaActionSendSMS[size];
        }
    };
}
