package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaActionSendEmail extends SantaAction implements Parcelable {

    public SantaActionSendEmail() {
    }

    public String getInfo (){

        return "Email";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected SantaActionSendEmail(Parcel in) {
    }

    public static final Creator<SantaActionSendEmail> CREATOR = new Creator<SantaActionSendEmail>() {
        @Override
        public SantaActionSendEmail createFromParcel(Parcel source) {
            return new SantaActionSendEmail(source);
        }

        @Override
        public SantaActionSendEmail[] newArray(int size) {
            return new SantaActionSendEmail[size];
        }
    };
}
