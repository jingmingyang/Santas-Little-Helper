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
import java.util.UUID;

import me.zhenning.DataEncryption;

/**
 * Created by chayut on 1/05/16.
 */
public class SantaUtilities {

    private static final String TAG = "SantaUtilities ";

    private static final double EARTH_RADIUS = 6378137.0;

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

                String filename = "santaFile.conf";

                File file = new File(Environment.getExternalStorageDirectory(), filename);

                FileOutputStream outputStream;

                outputStream = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(outputStream);
                myOutWriter.write(jsonObject.toString());
                myOutWriter.close();
                outputStream.close();

                //TODO: encrypt the file after stored
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();

                filePath = filePath + filename;
                int pos = filePath.lastIndexOf('.');
                String fileNameEnc = filePath.substring(0, pos) + "-enc." + filename;

                DataEncryption mEncrypter = new DataEncryption();
                mEncrypter.setBlocksize(128);
                mEncrypter.encryptFile(fileNameEnc, filePath);

                //TODO: delete the source config file

                Log.d(TAG, "Done writing File: " + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static JSONObject readConfigFromFile() {


        //TODO: decrypt the file
        JSONObject mObj = null;

        if(SantaUtilities.isExternalStorageReadable()){

            String filename = "santaFile.conf";
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath = filePath + filename;
            int pos = filePath.lastIndexOf('.');
            String filenameEnc = filePath.substring(0, pos) + "-enc." + filename;
            String filenameDec = filename.substring(0,pos) + "-dec." + filename.substring(pos+1);

            DataEncryption mEncrypter = new DataEncryption();
            mEncrypter.setBlocksize(128);
            mEncrypter.decryptFile(filenameDec, filenameEnc);

            File file = new File(Environment.getExternalStorageDirectory(), filenameDec);

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

    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2)
    {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static String getNewUUID ()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
