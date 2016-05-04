package me.chayut.santaslittlehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mediaPlayer = mediaPlayer.create(this,R.raw.pig);
        mediaPlayer.start();

        new AlertDialog.Builder(AlarmActivity.this).setTitle("alarm").setMessage("time up ~")
                .setPositiveButton("close alarm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        AlarmActivity.this.finish();
                        //TODO: feed back to the logic if the user press the button
                    }
                }).show();
    }


}
