package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaAction  implements Parcelable{

    public static final int ACTION_NULL = 0;
    public static final int ACTION_SMS =1;
    public static final int ACTION_EMAIL =2;
    public static final int ACTION_WIFI = 3;

    private int taskType = 0 ;
    private String email = "";
    private String phoneNumber = "";
    private Boolean wifiState = true;
    private String message = "";
    private Boolean hasAttachment = false;


    public SantaAction() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskType);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeValue(this.wifiState);
        dest.writeString(this.message);
        dest.writeValue(this.hasAttachment);
    }

    protected SantaAction(Parcel in) {
        this.taskType = in.readInt();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.wifiState = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.message = in.readString();
        this.hasAttachment = (Boolean) in.readValue(Boolean.class.getClassLoader());
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

