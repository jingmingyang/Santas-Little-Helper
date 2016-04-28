package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaAction  implements Parcelable{


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public SantaAction() {
    }

    protected SantaAction(Parcel in) {
    }

    public static final Creator<SantaAction> CREATOR = new Creator<SantaAction>() {
        @Override
        public SantaAction createFromParcel(Parcel source) {
            return new SantaAction(source);
        }

        @Override
        public SantaAction[] newArray(int size) {
            return new SantaAction[size];
        }
    };
}

