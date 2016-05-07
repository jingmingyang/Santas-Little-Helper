package me.chayut.santaslittlehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmActivity extends AppCompatActivity {

    int ButtonCancel_clicked = 0;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mediaPlayer = mediaPlayer.create(this,R.raw.pig);
        mediaPlayer.start();


        Toast.makeText(getApplicationContext(), "TIME UP", Toast.LENGTH_LONG).show();

        final Handler handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if(ButtonCancel_clicked==0) {
                            mediaPlayer.stop();
                            AlarmActivity.this.finish();

                            Toast.makeText(getApplicationContext(), "Sorry, No One Heard the Alarm ", Toast.LENGTH_LONG).show();

                            //TODO: issue alert to logic that alarm missed
                            break;

                        }
                }
                super.handleMessage(msg);
            }
        };
        TimerTask task = new TimerTask(){
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        Timer timer;
        timer = new Timer(true);
        timer.schedule(task, 1000 * 5);  //  Delay 1000ms *5    5s

        new AlertDialog.Builder(AlarmActivity.this).setTitle("alarm").setMessage("time up ~")
                .setPositiveButton("close alarm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ButtonCancel_clicked = 1;
                        mediaPlayer.stop();
                        AlarmActivity.this.finish();
                    }
                }).show();
    }


}
