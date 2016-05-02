package me.chayut.SantaHelperLogic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import me.chayut.santaslittlehelper.R;

/**
 * Created by chayut on 14/04/16.
 */
public class SantaTaskAdapter extends ArrayAdapter<SantaTask>  {

    private static final String TAG = "SantaTaskAdapter";

    Context mContext;
    private List<SantaTask> mList;

    public SantaTaskAdapter (Context context, int textViewResourceId,
                           List<SantaTask> objects) {

        super(context, textViewResourceId, objects);

        this.mContext = context;
        this.mList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View rowView;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        SantaTask mTask = mList.get(position);


        if (mTask instanceof SantaTaskAppoint) {
            Log.d(TAG,"SantaTaskAppoint");
            rowView = inflater.inflate(R.layout.row_task_appoint, parent, false);
            SantaTaskAppoint task = (SantaTaskAppoint) mTask;
        }
        else if  (mTask instanceof SantaTaskLocation){
            Log.d(TAG,"SantaTaskLocation");
            rowView = inflater.inflate(R.layout.row_task_location, parent, false);
            SantaTaskLocation task = (SantaTaskLocation) mTask;
        }
        else if  (mTask instanceof SantaTaskBattery){
            Log.d(TAG,"SantaTaskBattery");
            rowView = inflater.inflate(R.layout.row_task_battery, parent, false);
            SantaTaskBattery task = (SantaTaskBattery) mTask;
        }
        else
        {
            rowView = inflater.inflate(R.layout.row_task_appoint, parent, false);
        }

        //TODO: setup User endpoint interaction

        return rowView;
    }

    public SantaTask getItem(int position){
        return mList.get(position);
    }
}
