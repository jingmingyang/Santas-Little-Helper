package me.chayut.SantaHelperLogic;

import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by chayut on 1/05/16.
 */
public class SantaUtilities {

    private static final String TAG = "SantaUtilities ";

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public  static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /** JSON dump Section */

    public static void saveConfigToFile(JSONObject jsonObject) {

        if (SantaUtilities.isExternalStorageWritable()){
            try {

                String filename = "santaFile";

                File file = new File(Environment.getExternalStorageDirectory(), filename);

                FileOutputStream outputStream;

                outputStream = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(outputStream);
                myOutWriter.write(jsonObject.toString());
                myOutWriter.close();
                outputStream.close();

                Log.d(TAG, "Done writing File: " + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static JSONObject readConfigFromFile() {

        JSONObject mObj = null;

        if(SantaUtilities.isExternalStorageReadable()){

            String filename = "myfile";
            File file = new File(Environment.getExternalStorageDirectory(), filename);

            try {

                FileInputStream fIn = new FileInputStream(file);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));

                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow;
                }
                myReader.close();

                Log.d(TAG, "Done reading SD");

                mObj = new JSONObject(aBuffer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mObj;
    }

}
