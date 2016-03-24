package me.chayut.SantaHelperLogic;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chayut on 24/03/16.
 */
public class EndPoint implements Parcelable {

    private  String value = "";
    private  String Name = "";
    private int type =0;

    public static final int TYPE_EMAIL = 0;
    public static final int TYPE_PHONE = 1;

    /**
     * default constructors
     */
    public EndPoint(){
    }

    public EndPoint(int inType,String inName ,String inValue){
        value = inValue;
        Name = inName;
        type = inType;
    }



    /**
     * Methods
     */


    /**
     * parcelable
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.Name);
        dest.writeInt(this.type);
    }

    protected EndPoint(Parcel in) {
        this.value = in.readString();
        this.Name = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<EndPoint> CREATOR = new Creator<EndPoint>() {
        @Override
        public EndPoint createFromParcel(Parcel source) {
            return new EndPoint(source);
        }

        @Override
        public EndPoint[] newArray(int size) {
            return new EndPoint[size];
        }
    };
}
